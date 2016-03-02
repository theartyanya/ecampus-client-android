package com.kpi.campus.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Providing the adapter to populate pages inside of a ViewPager in BulletinBoardActivity.
 *
 * Created by Admin on 02.02.2016.
 */
public class BulletinTabPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence mTitles[];

    private List<Fragment> mFragments;// = new ArrayList<Fragment>(Arrays.asList(new Bb1TabFragment(), new Bb1TabFragment()));

    public static int BULLETIN_TAB_0 = 0;
    public static int BULLETIN_TAB_1 = 1;
    public static int BULLETIN_TAB_2 = 2;
    public static int BULLETIN_TAB_3 = 3;
    public static int BULLETIN_TAB_4 = 4;

    public BulletinTabPagerAdapter(FragmentManager fm, CharSequence titles[], List<Fragment> fragments) {
        super(fm);
        mTitles = titles;
        mFragments = fragments;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    // Return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    // Return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return mTitles.length;
    }
}