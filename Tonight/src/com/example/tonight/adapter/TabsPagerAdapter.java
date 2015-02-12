package com.example.tonight.adapter;

import com.example.tonight.fragment.AllFragment;
import com.example.tonight.fragment.DowntownFragment;
import com.example.tonight.fragment.SouthFragment;
import com.example.tonight.fragment.WestFragment;
import com.example.tonight.fragment.WhyteFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by junker4ce on 15-02-11.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new AllFragment();
            case 1:
                // Games fragment activity
                return new DowntownFragment();
            case 2:
                // Movies fragment activity
                return new SouthFragment();
            case 3:
                // Movies fragment activity
                return new WestFragment();
            case 4:
                // Movies fragment activity
                return new WhyteFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }
}
