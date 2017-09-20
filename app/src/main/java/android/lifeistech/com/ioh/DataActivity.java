package android.lifeistech.com.ioh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class DataActivity extends AppCompatActivity {

    TextView dateTextView;
    TextView timeTextView;
    TextView weatherTextView;
    TextView temTextView;
    EditText editText;
    FloatingActionButton fab;

    List<Data> mdatas;
    Gson gson;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int position;

    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        dateTextView = (TextView) findViewById(R.id.datetextViews);
        timeTextView = (TextView) findViewById(R.id.timetextViews);
        weatherTextView = (TextView) findViewById(R.id.weathertextViews);
        temTextView = (TextView) findViewById(R.id.temTextView);
        editText = (EditText) findViewById(R.id.memoText);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        gson = new Gson();
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = pref.edit();

        Intent intent = getIntent();
        String json = pref.getString("SAVE_KEY", "[]");
        mdatas = gson.fromJson(json, new TypeToken<ArrayList<Data>>() {
        }.getType());

        position = intent.getIntExtra("position", 0);
        Log.d("int i =", valueOf(position));
        data = mdatas.get(position);
        dateTextView.setText(data.month + "月" + data.day + "日");
        timeTextView.setText(data.time + "分");
        weatherTextView.setText(data.weather);
        temTextView.setText(data.tem + "℃" + data.hun + "%");
        editText.setText(data.memo);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            setImage(data.weather);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setImage(data.weather);
            setBackground(data.weather);

        }

    }

    private void setImage(String weather){
        if(weather.startsWith("Thunderstorm")){
            fab.setImageResource(R.drawable.for_fab_thunders);

        }else if(weather.startsWith("Drizzle")){
            fab.setImageResource(R.drawable.for_fab_rains);

        }else if(weather.startsWith("Rain")) {
            fab.setImageResource(R.drawable.for_fab_rains);

        }else if(weather.startsWith("Snow")){
            fab.setImageResource(R.drawable.for_fab_snows);

        }else if(weather.startsWith("Atmosphere")){
            fab.setImageResource(R.drawable.for_fab_suns);

        }else if(weather.startsWith("Clear")){
            fab.setImageResource(R.drawable.for_fab_suns);

        }else if(weather.startsWith("Clouds")) {
            fab.setImageResource(R.drawable.for_fab_clouds);

        }else{
            fab.setImageResource(R.drawable.for_fab_crowns);

        }

    }

    private void setBackground(String weather){
        if(weather.startsWith("Thunderstorm")){
            fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.thunderstorm)));

        }else if(weather.startsWith("Drizzle")){
            fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.rain)));

        }else if(weather.startsWith("Rain")) {
            fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.rain)));

        }else if(weather.startsWith("Snow")){
            fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.snow)));

        }else if(weather.startsWith("Atmosphere")){
            fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.clear)));

        }else if(weather.startsWith("Clear")){
            fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.clear)));

        }else if(weather.startsWith("Clouds")) {
            fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.clouds)));

        }else{
            fab.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.crown)));

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            data.memo = editText.getText().toString();
            mdatas.set(position,data);
            editor.putString("SAVE_KEY", gson.toJson(mdatas));
            editor.commit();
            finish();
        }

        return true;

    }
}
