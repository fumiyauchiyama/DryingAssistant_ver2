package android.lifeistech.com.ioh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HistoryFragment extends Fragment {

    List<Data> mData;
    DataAdapter mDataAdapter;
    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private List<Data> loadData() {
        List<Data> list = new ArrayList<>();

        Gson gson = new Gson();
        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String json = pref.getString("SAVE_KEY", "[]");
        list = gson.fromJson(json, new TypeToken<ArrayList<Data>>() {}.getType());

        return list;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = (ListView) getActivity().findViewById(R.id.outside2_listview);

        SwipeRefreshLayout swipeLayout;
        swipeLayout = (SwipeRefreshLayout)getActivity().findViewById(R.id.swiperefreshlayout);

        swipeLayout.setOnRefreshListener(() -> {
            mData = loadData();
            mDataAdapter.setmData(mData);
            if(swipeLayout.isRefreshing()) {
                swipeLayout.setRefreshing(false);
            }
        });

        mData = loadData();

        if (mData == null) {
            mData = new ArrayList<Data>();
        }

        mDataAdapter = new DataAdapter(getActivity(), R.layout.card, mData);
        mListView.setAdapter(mDataAdapter);

        mListView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getActivity(), DataActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });

    }

}
