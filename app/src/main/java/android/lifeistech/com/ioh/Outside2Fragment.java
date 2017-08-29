package android.lifeistech.com.ioh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class Outside2Fragment extends Fragment {

    //CalendarView calendarView = new CalendarView(this.getContext());
    CalendarView calendarView;
    List<Data> mData;
    DataAdapter mDataAdapter;
    ListView mListView;
    Gson gson;
    SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outside2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        gson = new Gson();
//        pref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
//
//
//        mListView = (ListView)getActivity().findViewById(R.id.outside2_listview);
//        mData = new ArrayList<>();
//
//        if(mData == null) {
//            String json = pref.getString("SAVE_KEY", "");
//            mData = gson.fromJson(json, new TypeToken<ArrayList<Data>>() {
//            }.getType());
//        }

//        mData.add(new Data(8, 18, 160, 0, 0, "fuu", "hoge"));
//        mData.add(new Data(8, 20, 174, 0, 0, "Clouds", "hoge"));
//        mData.add(new Data(8, 25, 153, 0, 0, "Clear", "hoge"));
//        mData.add(new Data(8, 27, 94,0, 0,"Rain", null));

//        mDataAdapter = new DataAdapter(getActivity(),R.layout.card,mData);
//
//        mListView.setAdapter(mDataAdapter);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gson = new Gson();
        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);


        mListView = (ListView)getActivity().findViewById(R.id.outside2_listview);
        mData = new ArrayList<Data>();
        if(mData == null) {
            String json = pref.getString("SAVE_KEY", "");
            mData = gson.fromJson(json, new TypeToken<ArrayList<Data>>() {
            }.getType());
        }

        mData.add(new Data(0, 0, 0, 0, 0, "fuu", "hoge"));

        mDataAdapter = new DataAdapter(getActivity(),R.layout.card,mData);

        if(mDataAdapter == null){
            mDataAdapter = new DataAdapter(getActivity(),R.layout.card,mData);
            mListView.setAdapter(mDataAdapter);
            Log.d("mDa","null");
        }else{
            mDataAdapter = new DataAdapter(getActivity(),R.layout.card,mData);
            Log.d("mDa","is not null");
            mListView.setAdapter(mDataAdapter);
        }




//        if(mListView == null){
//            Log.d("list","null");
//        }else {
//            Log.d("list")
//        }


        /*Bundle bundle = new Bundle();  //保存用のバンドル
        Map<String, ?> prefKV = getActivity().getSharedPreferences("shared_preference", Context.MODE_PRIVATE).getAll();
        Set<String> keys = prefKV.keySet();
        for(String key : keys){
            Object value = prefKV.get(key);
            if(value instanceof String){
                bundle.putString(key, (String) value);
            }else if(value instanceof Integer){
                // …略
            }
        }

        String stringList = bundle.getString("list"); //key名が"list"のものを取り出す
        if(stringList != null) {

            try {
                JSONArray array = new JSONArray(stringList);
                for (int i = 0, length = array.length(); i < length; i++) {
                    JSONObject jsonobject = array.getJSONObject(i);
                    int month = jsonobject.getInt("month");
                    int day = jsonobject.getInt("day");
                    int time = jsonobject.getInt("time");
                    int tem = jsonobject.getInt("tem");
                    int hun = jsonobject.getInt("hun");
                    String weather = jsonobject.getString("weather");
                    String memo = jsonobject.getString("memo");
                    data data = new data(month, day, time, tem, hun, weather, memo);
                    mdatas.add(data);
                    mlistView.setAdapter(mdataAdapter);
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }*/

    }


    //@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getContext(), DataActivity.class);
        // インテントにセット
        intent.putExtra("position", position);
        // Activity をスイッチする
        startActivity(intent);
    }




}
