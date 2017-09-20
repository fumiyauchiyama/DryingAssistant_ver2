package android.lifeistech.com.ioh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by fumiyauchiyama on 2017/06/24.
 */

public class TutorialFragment1 extends Fragment {

    SharedPreferences plef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_tutorial, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plef = getContext().getSharedPreferences("plef_start",MODE_PRIVATE);

        SharedPreferences.Editor editor = plef.edit();
        editor.putBoolean("key_tutorial",true);
        editor.commit();

    }
}
