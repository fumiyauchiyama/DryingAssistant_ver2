package android.lifeistech.com.ioh;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fumiyauchiyama on 2017/09/10.
 */

public class SettingcardAdapter extends ArrayAdapter<SettingCard> {

    private List<SettingCard> mSettingcard;

    public SettingcardAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        //mSettingcard = objects;
    }



    @Override
    public SettingCard getItem(int position) {

        return mSettingcard.get(position);

    }


    @Override
    public int getCount() {

        return mSettingcard.size();

    }

    private class ViewHoler {
        Switch aSwitch;
        TextView settingtext;
        TextView settingsub;
        LinearLayout card;

        public ViewHoler(View view) {
            aSwitch = (Switch) view.findViewById(R.id.switchcard);
            settingtext = (TextView) view.findViewById(R.id.SettingtextView);
            settingsub = (TextView) view.findViewById(R.id.SettingtextViewsub);

            card = (LinearLayout) view.findViewById(R.id.Setting);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final SettingcardAdapter.ViewHoler viewHoler;
        Log.d("adapter", "test");

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.settingcard, null);
            viewHoler = new SettingcardAdapter.ViewHoler(convertView);
            convertView.setTag(viewHoler);

        } else {
            viewHoler = (SettingcardAdapter.ViewHoler) convertView.getTag();
        }

        final SettingCard item = getItem(position);
        viewHoler.settingsub.setText("test");
        if (item != null) {
            viewHoler.settingtext.setText(item.Settingcontent);
            viewHoler.settingsub.setText(item.SettingSub);


        }

        return convertView;
    }
}
