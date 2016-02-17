package com.kpi.campus.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kpi.campus.ui.fragment.Bb1TabFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Providing the adapter to populate pages inside of a ViewPager in BulletinBoardActivity.
 *
 * Created by Admin on 02.02.2016.
 */
public class BulletinTabPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence mTitles[];

    private List<Fragment> mFragment = new ArrayList<Fragment>(Arrays.asList(new Bb1TabFragment(), new Bb1TabFragment()));

    public static int BULLETIN_TAB_0 = 0;
    public static int BULLETIN_TAB_1 = 1;

    public BulletinTabPagerAdapter(FragmentManager fm, CharSequence titles[]) {
        super(fm);
        mTitles = titles;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    // Return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    // Return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return mTitles.length;
    }
}