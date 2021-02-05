package com.weique.overhaul.v2.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.ARouterUtils;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerMyParticipateEventComponent;
import com.weique.overhaul.v2.mvp.contract.MyParticipateEventContract;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.presenter.MyParticipateEventPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.MyParticipateEventAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GK
 */
public class MyParticipateEventFragment extends BaseLazyLoadFragment<MyParticipateEventPresenter> implements MyParticipateEventContract.View {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    VerticalSwipeRefreshLayout swipeRefreshLayout;

    @Inject
    BaseQuickAdapter baseQuickAdapter;
    @Inject
    HorizontalDividerItemDecoration decoration;
    @Inject
    LinearLayoutManager layoutManager;


    /**
     * 进行中
     */
    public static final int UNDERWAY = 0;
    /**
     * 已完成
     */
    public static final int COMPLETED = 1;


    private MyParticipateEventAdapter adapter;


    public static MyParticipateEventFragment newInstance(int status) {
        MyParticipateEventFragment fragment = new MyParticipateEventFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt(ARouerConstant.STATUS, status);
        fragment.setArguments(bundle1);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyParticipateEventComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_participate_event, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecycler();
    }


    private void initRecycler() {
        try {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                mPresenter.getTaskList(getArguments().getInt(ARouerConstant.STATUS), true, false);
            });
            ArmsUtils.configRecyclerView(recyclerView, layoutManager);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(baseQuickAdapter);
            baseQuickAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            baseQuickAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    Object item = adapter.getItem(position);
                    if (item instanceof EventsReportedBean.ListBean) {
                        EventsReportedBean.ListBean listBean = (EventsReportedBean.ListBean) item;
                        ARouter.getInstance()
                                .build(RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                                .withParcelable(EventsReportedLookActivity.LIST_BEAN, listBean)
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT)
                                .navigation();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            baseQuickAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getTaskList(getArguments().getInt(ARouerConstant.STATUS), false, true);
            }, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void launchActivityByRouter(@NonNull String path) {
        ARouterUtils.navigation(getContext(), path);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            baseQuickAdapter.loadMoreEnd(true);
        } else {
            baseQuickAdapter.loadMoreComplete();
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


    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.getTaskList(getArguments().getInt(ARouerConstant.STATUS), true, false);
    }

    /**
     * 设置信息数据
     *
     * @param list       list
     * @param isLoadMore isLoadMore
     */
    @Override
    public void setNewData(List<EventsReportedBean.ListBean> list, boolean isLoadMore) {
        if (isLoadMore) {
            baseQuickAdapter.addData(list);
        } else {
            baseQuickAdapter.setNewData(list);
        }
    }
}
