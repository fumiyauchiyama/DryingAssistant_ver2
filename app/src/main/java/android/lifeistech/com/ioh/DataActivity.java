package android.lifeistech.com.ioh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.Sampler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;
import static java.security.AccessController.getContext;

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

        dateTextView = (TextView)findViewById(R.id.datetextViews);
        timeTextView = (TextView)findViewById(R.id.timetextViews);
        weatherTextView = (TextView)findViewById(R.id.weathertextViews);
        temTextView = (TextView)findViewById(R.id.temTextView);
        editText = (EditText)findViewById(R.id.memoText);
        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton3);
        gson = new Gson();
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = pref.edit();

        Intent intent = getIntent();
        String json = pref.getString("SAVE_KEY", "[]");
        mdatas = gson.fromJson(json, new TypeToken<ArrayList<Data>>() {
        }.getType());
        //mdatas.add(new Data(0, 0, 0, 0, 0, "初回記念スタンプ", "ご使用いただきありがとうございます！",2017));
        position = intent.getIntExtra("position",0);
        Log.d("int i =", valueOf(position));
        data = mdatas.get(position);
        dateTextView.setText(data.month + "月" + data.day + "日");
        timeTextView.setText(data.time + "分");
        weatherTextView.setText(data.weather);
        temTextView.setText(data.tem + "℃" + data.hun + "%");
        editText.setText(data.memo);

        if(data.weather.startsWith("Thunderstorm")){
            fab.setImageResource(R.drawable.thunders);
        }else if(data.weather.startsWith("Drizzle")){
            fab.setImageResource(R.drawable.rains);
        }else if(data.weather == "Rain") {
            fab.setImageResource(R.drawable.rains);
        }else if (data.weather == "Snow"){
            fab.setImageResource(R.drawable.snows);
        }else if (data.weather == "Atmosphere"){
            fab.setImageResource(R.drawable.suns);
        }else if (data.weather == "Clear"){
            fab.setImageResource(R.drawable.suns);
        }else if(data.weather.startsWith("Clouds")) {
            fab.setImageResource(R.drawable.clouds);
        }else{
            fab.setImageResource(R.drawable.crowns);
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
