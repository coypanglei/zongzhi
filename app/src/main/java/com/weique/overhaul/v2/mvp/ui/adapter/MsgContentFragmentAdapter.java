package com.weique.overhaul.v2.mvp.ui.adapter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;import androidx.fragment.app.FragmentPagerAdapter;


import com.weique.overhaul.v2.mvp.ui.fragment.party.PartContentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息内容子页面适配器
 * @author  GK
 */
public class MsgContentFragmentAdapter extends FragmentPagerAdapter {
    private List<String> names;
    private String  type;

    public MsgContentFragmentAdapter(FragmentManager fm,String  type) {
        super(fm);
        this.names = new ArrayList<>();
        this.type =type;
    }

    /**
     * 数据列表
     *
     * @param datas
     */
    public void setList(List<String> datas) {
        this.names.clear();
        this.names.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        PartContentFragment fragment = new PartContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", names.get(position));
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String plateName = names.get(position);
        if (plateName == null) {
            plateName = "";
        } else if (plateName.length() > 15) {
            plateName = plateName.substring(0, 15) + "...";
        }
        return plateName;
    }
}
