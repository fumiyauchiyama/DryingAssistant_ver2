package android.lifeistech.com.ioh;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SettingActivity extends AppCompatActivity {
    ListView listView;
    SettingcardAdapter sadapter;
    List<SettingCard> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mlist = new ArrayList<SettingCard>();
        mlist.add(new SettingCard("Test","This massage is a test",false));
        sadapter = new SettingcardAdapter(this,R.layout.settingcard);
        listView = (ListView)findViewById(R.id.settinglistview);
        listView.setAdapter(sadapter);


    }
}
