package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * @author GK
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<? extends Fragment> fragments;
    private String[] mTitles;


    public MyPagerAdapter(FragmentManager fm, ArrayList<? extends Fragment> mFragments, String[] titles) {
        super(fm);
        fragments = mFragments;
        mTitles = titles;
    }

    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> mFragments) {
        super(fm);
        fragments = mFragments;
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        try {
            if (mTitles != null || mTitles.length >= position + 1) {
                title = mTitles[position];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
