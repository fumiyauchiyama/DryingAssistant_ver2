package android.lifeistech.com.ioh;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fumiyauchiyama on 2017/06/10.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

   // @Override
    //public Fragment getItem(int position) {
        //return new OutsideFragment();
    //}

    public Fragment getItem(int position) {
        if(position == 0){
            return  new OutsideFragment();

        }else{
            return  new Outside2Fragment();
        }
    }


    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "天日干し";
        } else {
            return "カレンダー";
        }
    }

}
