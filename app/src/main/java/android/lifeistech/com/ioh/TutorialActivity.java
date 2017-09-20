package android.lifeistech.com.ioh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static java.security.AccessController.getContext;

public class TutorialActivity extends AppCompatActivity {

    SharedPreferences plef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ViewPager viewPager = (ViewPager) findViewById(R.id.tutorial_view_pager);
        viewPager.setAdapter(new TutorialViewPagerAdapter(getSupportFragmentManager()));

        plef = getSharedPreferences("pref",MODE_PRIVATE);

        SharedPreferences.Editor editor = plef.edit();
        editor.putBoolean("key_tutorial",true);
        editor.commit();

    }

    public void ok(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }


    public void intent(View v){
        Uri uri = Uri.parse("http://openweathermap.org/");
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);

    }

}
