package in.nisb.nisbapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by mridul on 19/3/17.
 */

class TabPagerAdapter extends FragmentPagerAdapter {

    private String fragments[] = {"Home","Events","Blog","Notifications"};

    public TabPagerAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new EventFragment();
            case 2:
                return new BlogFragment();
            case 3:
                return new NotificationFragment();
            default:
                return null;
        }
    }

    public String getPageTitle(int position){
        return "";//fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }


}
