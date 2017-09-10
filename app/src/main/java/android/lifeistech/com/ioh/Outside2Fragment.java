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
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gson = new Gson();
        pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);


        mListView = (ListView)getActivity().findViewById(R.id.outside2_listview);

        mData = new ArrayList<Data>();
        if(mData.size() == 0) {
            String json = pref.getString("SAVE_KEY", "[]");
            mData = gson.fromJson(json, new TypeToken<ArrayList<Data>>() {
            }.getType());
        }
        if(mData == null){
            mData = new ArrayList<Data>();
        }
        //mData.add(new Data(0, 0, 0, 0, 0, "fuu", "hoge",2017));



        //mDataAdapter = new DataAdapter(getActivity(),R.layout.card,mData);

        mDataAdapter = new DataAdapter(getActivity(),R.layout.card,mData);
        mListView.setAdapter(mDataAdapter);



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DataActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
}
