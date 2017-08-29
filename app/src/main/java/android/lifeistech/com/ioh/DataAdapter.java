package android.lifeistech.com.ioh;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fumiyauchiyama on 2017/08/08.
 */

public class DataAdapter extends ArrayAdapter<Data> {

    private List<Data> mData;


    public DataAdapter(Context context, int layoutResourseId, List<Data> objects) {

        super(context, layoutResourseId, objects);
        mData = objects;

    }

    @Override
    public Data getItem(int position) {

        return mData.get(position);

    }
    

    @Override
    public int getCount() {

        return mData.size();

    }

    private class ViewHoler {
        ImageView imageView;
        TextView datetextview;
        TextView timetextview;
        LinearLayout card;

        public ViewHoler(View view) {
            imageView = (ImageView) view.findViewById(R.id.weaimageViewincard);
            datetextview = (TextView) view.findViewById(R.id.datetextviewincard);
            timetextview = (TextView) view.findViewById(R.id.timetextviewincard);

            card = (LinearLayout) view.findViewById(R.id.card);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHoler viewHoler;
        Log.d("adapter", "test");

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card, null);
            viewHoler = new ViewHoler(convertView);
            convertView.setTag(viewHoler);

        } else {
            viewHoler = (ViewHoler) convertView.getTag();
        }

        final Data item = getItem(position);
        viewHoler.timetextview.setText("test");
        if (item != null) {
            setIconImage(viewHoler.imageView, item);
            viewHoler.datetextview.setText(String.valueOf(item.month) + "月" + String.valueOf(item.day) + "日");
            viewHoler.timetextview.setText(String.valueOf(item.time));

            Log.d("Adapter", "item is not null: " + mData.size());
            /*viewHoler.Card.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //Intent intent = new Intent(this,DataActivity.class);
                    //startActivity(intent);

                    Intent intent = new Intent(MainActivity.this, DataActivity.class);
                    startActivity(intent);
                }
            });
            */

        }

        return convertView;
    }

    private void setIconImage(ImageView imageView, Data data) {
        if (data.weather.equals("Thunderstorm")) {
            imageView.setImageResource(R.drawable.thunderstanp);
        } else if (data.weather.equals("Drizzle")) {
            imageView.setImageResource(R.drawable.rainstamp);
        } else if (data.weather.equals("Rain")) {
            imageView.setImageResource(R.drawable.rainstamp);
        } else if (data.weather.equals("Snow")) {
            imageView.setImageResource(R.drawable.snowstamp);
        } else if (data.weather.equals("Atmosphere")) {
            imageView.setImageResource(R.drawable.sunstamp);
        } else if (data.weather.equals("Clear")) {
            imageView.setImageResource(R.drawable.sunstamp);
        } else if (data.weather.equals("Clouds")) {
            imageView.setImageResource(R.drawable.cloudstamp);
        } else {
            imageView.setImageResource(R.drawable.crownstamp);
        }
    }


}
