package com.weique.overhaul.v2.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.TimeUtil;
import com.weique.overhaul.v2.di.component.DaggerPatrolsComponent;
import com.weique.overhaul.v2.mvp.contract.PatrolsContract;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.PatrolsPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.PatrolsListAdapter;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 巡检任务
 *
 * @author GreatKing
 */
public class PatrolsFragment extends BaseLazyLoadFragment<PatrolsPresenter> implements PatrolsContract.View {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.sw)
    SwipeRefreshLayout sw;

    private PatrolsListAdapter patrolsListAdapter;

    public static PatrolsFragment newInstance() {
        PatrolsFragment fragment = new PatrolsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPatrolsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patrols, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        initApapter();
        sw.setOnRefreshListener(() -> mPresenter.getPatrolsListData(true, false, "0"));
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    private void initApapter() {
        patrolsListAdapter = new PatrolsListAdapter();
        patrolsListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(patrolsListAdapter);
        recyclerView.setClipToPadding(false);
        patrolsListAdapter.setEmptyView(R.layout.null_content_home_layout, recyclerView);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

//                ContentResolver mResolver = Objects.requireNonNull(getActivity()).getContentResolver();
//                String timeFormat = android.provider.Settings.System.getString(mResolver, android.provider.Settings.System.TIME_12_24);
//                if (timeFormat.equals("24")) {
//                    //24小时制
//                } else {
//                    //12小时制
//                    Calendar mCalendar = Calendar.getInstance();
//                    if (mCalendar.get(Calendar.AM_PM) == 0) {
//                        //白天
//                    }else {
//                        //晚上
//                    }
//                }
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    SimpleDateFormat format = new SimpleDateFormat(Constant.YMDHMS1, Locale.CHINA);
                    try {
                        //                    if (TimeUtil.compareDate(format.format(new Date()), ((PatrolsBean.ListBean) adapter.getData().get(position)).getExecutTime())) {
                        //                        ArmsUtils.makeText("任务超期，不可办理！");
                        //                    } else if (TimeUtil.compareDate(format.format(new Date()), ((PatrolsBean.ListBean) adapter.getData().get(position)).getDeadlineTime())) {
                        //                        ArmsUtils.makeText("当前未到任务开始时间！");
                        //                    } else {
                        //                        ARouter.getInstance().build(RouterHub.APP_PATROLSDETAILACTIVITY)
                        //                                .withString("id", ((PatrolsBean.ListBean) adapter.getData().get(position)).getId())
                        //                                .navigation();
                        //                    }

                        String start = ((PatrolsBean.ListBean) adapter.getData().get(position)).getExecutTime();
                        String end = ((PatrolsBean.ListBean) adapter.getData().get(position)).getDeadlineTime();

                        if (TimeUtil.compareDate(format.format(new Date()), start, end) == 200) {
                            ARouter.getInstance().build(RouterHub.APP_PATROLSDETAILACTIVITY)
                                    .withString("id", ((PatrolsBean.ListBean) adapter.getData().get(position)).getId())
                                    .navigation();
                        } else if (TimeUtil.compareDate(format.format(new Date()), start, end) == 100) {
                            ArmsUtils.makeText("当前未到任务开始时间！");
                        } else if (TimeUtil.compareDate(format.format(new Date()), start, end) == 101) {
                            ArmsUtils.makeText("任务超期，不可办理！");
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        patrolsListAdapter.setOnLoadMoreListener(() -> {
            assert mPresenter != null;
            mPresenter.getPatrolsListData(false, true, "0");
        }, recyclerView);
    }

    @Override
    public void showLoading() {
        sw.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        sw.setRefreshing(false);
    }


    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            patrolsListAdapter.loadMoreEnd(true);
        } else {
            patrolsListAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public void setPatrolsListData(PatrolsBean itemBean, boolean isLoadMore) {
        if (isLoadMore) {
            patrolsListAdapter.addData(itemBean.getList());
        } else {
            patrolsListAdapter.setNewData(itemBean.getList());
        }
    }


    @Subscriber(tag = RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT_PATROLSFRAGMENT)
    private void eventBusCallback(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.IS_REFRESH:
                    assert mPresenter != null;
                    mPresenter.getPatrolsListData(true, false, "0");
                    break;

                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.getPatrolsListData(true, false, "0");
    }
}
