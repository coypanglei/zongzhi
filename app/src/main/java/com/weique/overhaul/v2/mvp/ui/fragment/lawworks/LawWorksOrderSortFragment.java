package com.weique.overhaul.v2.mvp.ui.fragment.lawworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderStatusBean;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 法务订单 分类 fragment
 *
 * @author GreatKing
 */
public class LawWorksOrderSortFragment extends BaseFragment {

    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    private ArrayList<Fragment> mFragments;
    private MyPagerAdapter mAdapter;
    private List<String> strings;


    public static LawWorksOrderSortFragment newInstance() {
        LawWorksOrderSortFragment fragment = new LawWorksOrderSortFragment();
        return fragment;
    }

    /**
     * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
     *
     * @param appComponent
     */
    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    /**
     * 初始化 View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_law_works_order_sort_fragment, container, false);
    }

    /**
     * 初始化数据
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            strings = new ArrayList<>();
            mFragments = new ArrayList<>();
            //移动端  订单状态 从 待接单开始
            for (int ss : LawWorksOrderStatusBean.APP_ORDER_SCREEN) {
                mFragments.add(LawWorksOrderFragment.newInstance(ss));
                strings.add(LawWorksOrderStatusBean.getStatusStrByStatus(ss));
            }
            String[] array = this.strings.toArray(new String[this.strings.size()]);
            mAdapter = new MyPagerAdapter(getChildFragmentManager(), mFragments, array);
            viewPage.setAdapter(mAdapter);
            tl2.setViewPager(viewPage, array);
            viewPage.setOffscreenPageLimit(array.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @Override
    public void setData(@Nullable Object data) {

    }
}
