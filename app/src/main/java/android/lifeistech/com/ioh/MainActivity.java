package android.lifeistech.com.ioh;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.value;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    boolean aBoolean;
    SharedPreferences plef;
    SharedPreferences.Editor editor;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference refMug = database.getReference();

    TextView texthan;

    public static final String ARG_TYPE = "type";
    public static final String ARG_ID = "id";



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String id = getIntent().getStringExtra(ARG_ID);
        String type = getIntent().getStringExtra(ARG_TYPE);


        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);


        //ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar) ;

        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);

        plef = getSharedPreferences("pref",MODE_PRIVATE);

        aBoolean = plef.getBoolean("key_tutorial",false);

        if (!aBoolean) {

            Intent intent = new Intent(this, TutorialActivity.class);
            startActivity(intent);

        }else {
            SharedPreferences.Editor editor = plef.edit();
            editor.putBoolean("key_tutorial",false);

        }

//        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
//            public void onPageScrollStateChanged(int state) {
//                if (ViewPager.SCROLL_STATE_IDLE == state) {
//                    viewPager.removeView(Outside2Fragment);
//                }
//            }
//        });
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Help:
                // ボタンをタップした際の処理を記述
                Intent intent = new Intent(this, TutorialActivity.class);
                startActivity(intent);
            case R.id.Setting:
                Intent Settingintent = new Intent(this, SettingActivity.class);
                startActivity(Settingintent);
        }
        return true;
    }



}
