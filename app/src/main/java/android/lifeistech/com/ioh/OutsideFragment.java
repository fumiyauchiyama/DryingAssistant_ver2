package android.lifeistech.com.ioh;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.lifeistech.com.ioh.R.layout.dialog;


public class OutsideFragment extends Fragment {
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refMug = database.getReference();

    TextView textwaterdata;
    TextView TemperatureTextView;
    TextView textweather;
    TextView pretextView;       //pre = Prediction


    int waterdata;
    int ch0;
    int ch1;

    int temperature;
    int hun;
    int year;
    int month;
    int day;

    int Time;
    int preTime;
    int preTimed;
    Timer mTimer;
    Timer preTimer;
    private Handler mHandler;

    Button button;
    ProgressBar progressBar;

    boolean DryingBoolean;
    boolean notificationBoolean;
    boolean isAlreadyNotified;

    List<Data> mdatas;

    NotificationService notificationService;
    NotificationBroadcastReciever notificationBroadcastReciever;
    IntentFilter intentFilter;

    int inteWeather = 0; // -1 の時は無効、0 は晴れ、１は雨

    Weather weather = Weather.Null;

    enum Weather {
        Sun, Rain, Null
    }

    public void getNowDate() {
        Date date = new Date();
        SimpleDateFormat mdf = new SimpleDateFormat("MM");
        month = Integer.parseInt(mdf.format(date));
        SimpleDateFormat ddf = new SimpleDateFormat("dd");
        day = Integer.parseInt(ddf.format(date));
        SimpleDateFormat ydf = new SimpleDateFormat("yyyy");
        year = Integer.parseInt(ydf.format(date));
    }

    private void setProgressBar() {
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", preTime);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    private void Notification(String title, String content, String ticker, int Iconid,int Notificationid) {
        android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(getContext());
        builder.setSmallIcon(Iconid);
        builder.setLargeIcon(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.dryingassistant));
        builder.setContentTitle(title);
        builder.setContentText(content);
        //builder.setContentInfo("情報欄");
        builder.setTicker(ticker);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(Notificationid, builder.build());
    }

    private void commit(Boolean DryingBoolean, Boolean notificationBoolean) {
        editor.putBoolean("aboolean", DryingBoolean);
        editor.putBoolean("notificationBoolean", notificationBoolean);
        editor.commit();
    }

    private boolean getBooleans() {
        DryingBoolean = pref.getBoolean("aboolean", false);
        notificationBoolean = pref.getBoolean("notificationBoolean", false);
        return DryingBoolean;
    }

    private void setdata() {

        waterdata = (ch0 + ch1) / 2;

        if (temperature == 0 || hun == 0) {
            return;
        } else {
            //preTime = 85680 / (temperature * (100 - hun));
            preTime = 714 * waterdata / (temperature * (100 - hun));
        }


        textwaterdata.setText(String.valueOf(waterdata));
        TemperatureTextView.setText(temperature + "℃、" + hun + "%");
        pretextView.setText(preTime / 60 + "時間" + preTime % 60 + "分");
        setProgressBar();
    }

    private void getWeather() {
        // リクエストオブジェクトを作って
        Request request = new Request.Builder()
                // URLを生成
                .url("http://api.openweathermap.org/data/2.5/find?lat=35.283611&lon=139.667221&cnt=1&APPID=b6de807d926981bf3ac26ca77b1a2ae7")
                .get()
                .build();
        // クライアントオブジェクトを作成する
        OkHttpClient client = new OkHttpClient();
        // 新しいリクエストを行う
        client.newCall(request).enqueue(new Callback() {
            // 通信が失敗した時
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            // 通信が成功した時
            @Override
            public void onResponse(Response response) throws IOException {
                // 通信結果をログに出力する
                Log.d("onResponse", response.toString());
                final String json = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        parseJson(json);
                    }
                });
            }
        });

    }

    private void parseJson(String json) {
        Log.d("Json", json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            // {forecasts[] -> 0 -> {dataLabel, telop, temperature}}
            JSONArray listArray = jsonObject.getJSONArray("list");

            Log.d("json", listArray.toString());
            // 0番目のものが今日の天気なので取得する
            JSONObject todayWeatherJson = listArray.getJSONObject(0);

            Log.d("json", todayWeatherJson.toString());
            JSONArray Array = todayWeatherJson.getJSONArray("weather");

            JSONObject WeatherJson = Array.getJSONObject(0);
            Log.d("json", WeatherJson.toString());

            String telop = WeatherJson.getString("main");
            textweather.setText(telop);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outside, container, false);


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = pref.edit();

        textwaterdata = (TextView) view.findViewById(R.id.texthan);
        textweather = (TextView) view.findViewById(R.id.textwea);
        TemperatureTextView = (TextView) view.findViewById(R.id.texttem);
        pretextView = (TextView) view.findViewById(R.id.PretextView);
        button = (Button) view.findViewById(R.id.click);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        progressBar.setProgress(0);

        mdatas = new ArrayList<>();

        getBooleans();
        getWeather();

        if(mHandler == null){

            mHandler = new Handler();

        }

        if (DryingBoolean) {
            button.setText("洗濯終了ボタン");
            Log.d("aboolean=", String.valueOf(DryingBoolean));


        } else {
            button.setText("洗濯開始ボタン");
            Log.d("aboolean=", String.valueOf(DryingBoolean));


        }


        refMug.addChildEventListener(new ChildEventListener() {
            int i = 0;

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getKey().equals("number")) {
                    ch0 = dataSnapshot.getValue(Integer.class);
                }
                if (dataSnapshot.getKey().equals("number1")) {
                    ch1 = dataSnapshot.getValue(Integer.class);
                }
                if (dataSnapshot.getKey().equals("temperature")) {
                    temperature = dataSnapshot.getValue(Integer.class);
                }
                if (dataSnapshot.getKey().equals("humidity")) {
                    hun = dataSnapshot.getValue(Integer.class);
                }

                setdata();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getKey().equals("number")) {
                    ch0 = dataSnapshot.getValue(Integer.class);
                }

                if (dataSnapshot.getKey().equals("number1")) {
                    ch1 = dataSnapshot.getValue(Integer.class);
                }
                if (dataSnapshot.getKey().equals("temperature")) {
                    temperature = dataSnapshot.getValue(Integer.class);
                }
                if (dataSnapshot.getKey().equals("humidity")) {
                    hun = dataSnapshot.getValue(Integer.class);
                }

                setdata();

                if (i == 0) {
                    getWeather();
                    i++;
                } else if (i == 9) {
                    i = 0;
                } else {
                    i++;
                }

                getBooleans();

                if (textweather.getText().toString().equals("Rain") || textweather.getText().toString().equals("Drizzle") && isAlreadyNotified) {
                    Notification("雨が降っています！", "洗濯物が濡れている可能性があります", "雨が降ってます！", R.drawable.rains, 1);
                    isAlreadyNotified = false;
                    editor.putBoolean("rainBoolean", isAlreadyNotified);
                    editor.commit();
                }


                if (waterdata <= 5 && DryingBoolean && notificationBoolean) {

                    Notification("洗濯物を取り込みましょう", "取り込む時にスイッチを切ってください", "乾燥しました！", R.drawable.notification, 0);
                    notificationBoolean = false;
                    commit(DryingBoolean, notificationBoolean);

                    if(mTimer != null){

                        mTimer.cancel();

                    }
                    return;

                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        button.setOnClickListener(v -> {

            if (DryingBoolean) {
                getActivity().stopService(new Intent(getActivity(), NotificationService.class));
                DryingBoolean = false;
                button.setText("洗濯開始ボタン");
                Toast.makeText(getContext(), "洗濯を終了しました", Toast.LENGTH_SHORT).show();
                Log.d("aboolean=", String.valueOf(DryingBoolean));

                Gson gson = new Gson();
                getNowDate();
                Data mdata = new Data(month, day, Time, temperature, hun, textweather.getText().toString(), null, year);

                mdatas = gson.fromJson(pref.getString("SAVE_KEY", "[]"), List.class);
                Log.d("pref", pref.getString(pref.getString("SAVE_KEY", "not String"), "List.class"));
                if (mdatas == null) {
                    mdatas = new ArrayList<Data>();
                }
                mdatas.add(0, mdata);

                editor.putString("SAVE_KEY", gson.toJson(mdatas));
                commit(DryingBoolean, notificationBoolean);

                if(mTimer != null) {
                    mTimer.cancel();
                }
                if (preTimer != null) {
                    preTimer.cancel();
                }
                progressBar.setProgress(0);
                pretextView.setText("00:00");
                final View view1 = getActivity().getLayoutInflater().inflate(dialog, null);
                new AlertDialog.Builder(getActivity()).setView(view1).show();

                ImageView imageView = (ImageView) view1.findViewById(R.id.dialogimage);
                if (mdata.weather.equals("Thunderstorm")) {
                    imageView.setImageResource(R.drawable.thunderstanp);
                } else if (mdata.weather.equals("Drizzle")) {
                    imageView.setImageResource(R.drawable.rainstamp);
                } else if (mdata.weather.equals("Rain")) {
                    imageView.setImageResource(R.drawable.rainstamp);
                } else if (mdata.weather.equals("Snow")) {
                    imageView.setImageResource(R.drawable.snowstamp);
                } else if (mdata.weather.equals("Atmosphere")) {
                    imageView.setImageResource(R.drawable.sunstamp);
                } else if (mdata.weather.equals("Clear")) {
                    imageView.setImageResource(R.drawable.sunstamp);
                } else if (mdata.weather.equals("Clouds")) {
                    imageView.setImageResource(R.drawable.cloudstamp);
                } else {
                    imageView.setImageResource(R.drawable.crownstamp);
                }

                Button okButton = (Button) view1.findViewById(R.id.dialogok);
                Button memoButton = (Button) view1.findViewById(R.id.dialogmemo);
                okButton.setOnClickListener(v12 -> getActivity().finish());
                memoButton.setOnClickListener(v1 -> {
                    Intent memoIntent = new Intent(getActivity(), DataActivity.class);
                    startActivity(memoIntent);
                });

            } else {
                button.setText("洗濯終了ボタン");
                DryingBoolean = true;
                notificationBoolean = true;
                commit(DryingBoolean, notificationBoolean);

                Time = 0;

                mTimer = new Timer(false);
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mHandler.post(() -> Time++);
                    }
                }, 0, 60000);

                if (temperature == 0 || hun == 0) {
                    return;
                } else {
                    preTime = 714 * waterdata / (temperature * (100 - hun));
                    preTimed = preTime;
                    progressBar.setMax(preTimed);
                    setProgressBar();
                    preTimer = new Timer(false);
                    preTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mHandler.post(() -> {
                                preTime--;
                                if (preTime == 0) {
                                    progressBar.setProgress(0);
                                    pretextView.setText("00:00");
                                    preTimer.cancel();
                                }
                                pretextView.setText(preTime / 60 + "時間" + preTime % 60 + "分");
                                progressBar.setMax(preTimed);
                                setProgressBar();
                            });
                        }
                    }, 0, 60000);

                    isAlreadyNotified = true;
                    editor.putBoolean("rainBoolean", isAlreadyNotified);
                    editor.commit();

                }

                Notification("乾燥中です", "取り込む時にスイッチを切ってください", "乾燥中です", R.drawable.notification,0);
                getActivity().startService(new Intent(getActivity(), NotificationService.class));

                notificationBroadcastReciever = new NotificationBroadcastReciever();
                intentFilter = new IntentFilter();
                intentFilter.addAction("MY_ACTION");
                getActivity().registerReceiver(notificationBroadcastReciever, intentFilter);
            }
        });


    }


}
