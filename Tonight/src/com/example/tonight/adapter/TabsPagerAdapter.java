package com.example.tonight.adapter;

import com.example.tonight.MainActivity;
import com.example.tonight.fragment.AllFragment;
import com.example.tonight.fragment.DowntownFragment;
import com.example.tonight.fragment.SouthFragment;
import com.example.tonight.fragment.WestFragment;
import com.example.tonight.fragment.WhyteFragment;
import com.example.tonight.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;

import static android.app.PendingIntent.getActivity;

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
                //MainActivity.updateDrawer();
                return new AllFragment();
            case 1:
                // Games fragment activity
                //MainActivity.updateDrawer("Downtown");
                return new DowntownFragment();
            case 2:
                // Movies fragment activity
                //MainActivity.updateDrawer("South");
                return new SouthFragment();
            case 3:
                // Movies fragment activity
                //MainActivity.updateDrawer("West");
                return new WestFragment();
            case 4:
                // Movies fragment activity
                //MainActivity.updateDrawer("Whyte");
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
