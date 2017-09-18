package android.lifeistech.com.ioh;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

public class NotificationService extends Service {

    public static final String REQUEST_TYPE = "notification";
    public static final int REQUEST_PROCESS = 1;

    @Override
    public IBinder onBind(Intent intent) {

        return null;

    }

    @Override
    public void onCreate() {

        super.onCreate();
        Log.d("service", "created");

    }

//    @Override
//    public void onStartCommand(){
//        super.onStartCommand(Intent intent, int flag, int startId);
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        pref = getApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = pref.edit();

        Log.d("aboolean=",String.valueOf(aBoolean));


        refMug.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals("number")) {
                    ch0 = dataSnapshot.getValue(Integer.class);
                }

                if (dataSnapshot.getKey().equals("number1")) {
                    ch1 = dataSnapshot.getValue(Integer.class);

                }


                wd = (ch0 + ch1) / 2;

                getBooleans();


                if (wd <= 5 && aBoolean && nBoolean) {

                    android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(NotificationService.this);
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle("洗濯物を取り込みましょう");
                    builder.setContentText("取り込む時にスイッチを切ってください");
                    //builder.setContentInfo("情報欄");
                    builder.setTicker("乾燥しました！");
                    builder.setDefaults(Notification.DEFAULT_ALL);
                    builder.setWhen(System.currentTimeMillis());
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(0, builder.build());
                    nBoolean = false;
                    commit(aBoolean,nBoolean);
                    return;


                } else {

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot.getKey().equals("number")) {
                    ch0 = dataSnapshot.getValue(Integer.class);
                }

                if (dataSnapshot.getKey().equals("number1")) {
                    ch1 = dataSnapshot.getValue(Integer.class);

                }


                wd = (ch0 + ch1) / 2;

                getBooleans();


                if (wd <= 5 && aBoolean && nBoolean) {

                    android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(NotificationService.this);
                    builder.setSmallIcon(R.mipmap.ic_launcher);
                    builder.setContentTitle("洗濯物を取り込みましょう");
                    builder.setContentText("取り込む時にスイッチを切ってください");
                    //builder.setContentInfo("情報欄");
                    builder.setTicker("乾燥しました！");
                    builder.setDefaults(Notification.DEFAULT_ALL);
                    builder.setWhen(System.currentTimeMillis());
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(0, builder.build());
                    nBoolean = false;
                    commit(aBoolean,nBoolean);
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

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("service", "destroy");
        super.onDestroy();

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refMug = database.getReference();
    int wd;
    int ch0;
    int ch1;
    boolean aBoolean;
    boolean nBoolean;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private void commit(boolean aBoolean, boolean nBoolean){
        editor.putBoolean("aboolean",aBoolean);
        editor.putBoolean("nboolean",nBoolean);
        editor.commit();
    }

    private boolean getBooleans(){

        aBoolean = pref.getBoolean("aboolean",false);
        nBoolean = pref.getBoolean("nboolean",false);

        return aBoolean & nBoolean;
    }

//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        pref = getApplicationContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
//        editor = pref.edit();
//
//        Log.d("aboolean=",String.valueOf(aBoolean));
//
//
//        refMug.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                if (dataSnapshot.getKey().equals("number")) {
//                    ch0 = dataSnapshot.getValue(Integer.class);
//                }
//
//                if (dataSnapshot.getKey().equals("number1")) {
//                    ch1 = dataSnapshot.getValue(Integer.class);
//
//                }
//
//
//                wd = (ch0 + ch1) / 2;
//
//                getBooleans();
//
//
//                if (wd <= 5 && aBoolean && nBoolean) {
//
//                    android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(NotificationService.this);
//                    builder.setSmallIcon(R.mipmap.ic_launcher);
//                    builder.setContentTitle("洗濯物を取り込みましょう");
//                    builder.setContentText("取り込む時にスイッチを切ってください");
//                    //builder.setContentInfo("情報欄");
//                    builder.setTicker("乾燥しました！");
//                    builder.setDefaults(Notification.DEFAULT_ALL);
//                    builder.setWhen(System.currentTimeMillis());
//                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    manager.notify(0, builder.build());
//                    nBoolean = false;
//                    commit(aBoolean,nBoolean);
//                    return;
//
//
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                if (dataSnapshot.getKey().equals("number")) {
//                    ch0 = dataSnapshot.getValue(Integer.class);
//                }
//
//                if (dataSnapshot.getKey().equals("number1")) {
//                    ch1 = dataSnapshot.getValue(Integer.class);
//
//                }
//
//
//                wd = (ch0 + ch1) / 2;
//
//                getBooleans();
//
//
//                if (wd <= 5 && aBoolean && nBoolean) {
//
//                    android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(NotificationService.this);
//                    builder.setSmallIcon(R.mipmap.ic_launcher);
//                    builder.setContentTitle("洗濯物を取り込みましょう");
//                    builder.setContentText("取り込む時にスイッチを切ってください");
//                    //builder.setContentInfo("情報欄");
//                    builder.setTicker("乾燥しました！");
//                    builder.setDefaults(Notification.DEFAULT_ALL);
//                    builder.setWhen(System.currentTimeMillis());
//                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    manager.notify(0, builder.build());
//                    nBoolean = false;
//                    commit(aBoolean,nBoolean);
//                    return;
//
//
//                } else {
//
//                }
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }


}
