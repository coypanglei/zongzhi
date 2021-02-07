package com.weique.overhaul.v2.mvp.ui.activity.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.mvp.model.entity.HomeMenuItemBean;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.PatrolsFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.TaskListHomeFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 任务 和 工作清单
 *
 * @author Administrator
 */
@Route(path = RouterHub.APP_TASKANDWORKLISTACTIVITY)
public class TaskAndWorkListActivity extends BaseActivity {
    private ArrayList<HomeMenuItemBean> mMenuData;
    private MyPagerAdapter mAdapter;

    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.vp)
    ViewPager vp;


    private ArrayList<BaseLazyLoadFragment> mFragments;

    private final String[] strings1 = {"工作清单", "待办巡查"};
    private TaskListHomeFragment taskListHomeFragment;
    private PatrolsFragment patrolsFragment;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_task_and_work_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        try {
            mFragments = new ArrayList<>();
            taskListHomeFragment = TaskListHomeFragment.newInstance();
            mFragments.add(taskListHomeFragment);
            patrolsFragment = PatrolsFragment.newInstance();
            mFragments.add(patrolsFragment);
            mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, strings1);
            vp.setAdapter(mAdapter);
            tl2.setViewPager(vp, strings1);
            vp.setOffscreenPageLimit(strings1.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}