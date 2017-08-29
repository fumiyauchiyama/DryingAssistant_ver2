package android.lifeistech.com.ioh;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class DataActivity extends AppCompatActivity {

    TextView dateTextView;
    TextView timeTextView;
    TextView weatherTextView;
    TextView temTextView;
    EditText editText;
    FloatingActionButton fab;

    List<Data> mdatas;

    int position;

    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        dateTextView = (TextView)findViewById(R.id.datetextViews);
        timeTextView = (TextView)findViewById(R.id.timetextViews);
        weatherTextView = (TextView)findViewById(R.id.weathertextViews);
        editText = (EditText)findViewById(R.id.memoText);
        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton3);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        data = mdatas.get(position);
        dateTextView.setText(data.month + "月" + data.day + "日");
        timeTextView.setText(data.time);
        weatherTextView.setText(data.weather);
        temTextView.setText(data.tem + "℃" + data.hun + "%");
        editText.setText(data.memo);

        if(data.weather == "Thunderstorm"){
            fab.setImageResource(R.drawable.thunders);
        }else if(data.weather ==  "Drizzle"){
            fab.setImageResource(R.drawable.rains);
        }else if(data.weather == "Rain") {
            fab.setImageResource(R.drawable.rains);
        }else if (data.weather == "Snow"){
            fab.setImageResource(R.drawable.snows);
        }else if (data.weather == "Atmosphere"){
            fab.setImageResource(R.drawable.suns);
        }else if (data.weather == "Clear"){
            fab.setImageResource(R.drawable.suns);
        }else if(data.weather == "Clouds") {
            fab.setImageResource(R.drawable.clouds);
        }else{
            fab.setImageResource(R.drawable.crowns);
        }
    }

    protected void onPause(Bundle savedInstanceState) {
        data.memo = editText.getText().toString();
        mdatas.set(position,data);
    }
}
