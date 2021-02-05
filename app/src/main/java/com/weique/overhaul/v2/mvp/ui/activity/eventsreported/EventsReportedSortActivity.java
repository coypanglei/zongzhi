package com.weique.overhaul.v2.mvp.ui.activity.eventsreported;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerEventsReportedSortComponent;
import com.weique.overhaul.v2.mvp.contract.EventsReportedSortContract;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedSortBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EventsReportedSortPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedSortAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 事件分类  列表
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_EVENTSREPORTEDSORTACTIVITY)
public class EventsReportedSortActivity extends BaseActivity<EventsReportedSortPresenter> implements EventsReportedSortContract.View {

    @BindView(R.id.events)
    RecyclerView eventsList;
    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout swipeRefresh;

    @Inject
    AppManager appManager;

    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    EventsReportedSortAdapter mAdapter;
    @Inject
    HorizontalDividerItemDecoration horizontalDividerItemDecoration;

    /**
     * 上个界面  来源
     */
    @Autowired(name = ARouerConstant.SOURCE)
    String source;
    public static final String TYPE_ID = "TYPE_ID";
    /**
     * 类型来源
     */
    @Autowired(name = TYPE_ID)
    String typeId;
    public static final String IS_FIRST = "IS_FIRST";
    /**
     * 是第一个界面 --  第一个界面添加 全部按钮
     */
    @Autowired(name = IS_FIRST)
    boolean isFirst = true;
    private int mPosition = -1;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEventsReportedSortComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_events_reported_sort;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(getString(R.string.event_type));
        ARouter.getInstance().inject(this);
        initRecyclerView();
        mPresenter.getEvents(typeId, false, source);
    }

    /**
     * 初始化 recyclerview
     */
    private void initRecyclerView() {
        swipeRefresh.setOnRefreshListener(() -> mPresenter.getEvents(typeId, false, source));
        ArmsUtils.configRecyclerView(eventsList, linearLayoutManager);
        eventsList.addItemDecoration(horizontalDividerItemDecoration);
        eventsList.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.null_content_layout, eventsList);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            try {
                if (AppUtils.isFastClick()) {
                    return;
                }
                Object item = adapter.getItem(position);
                EventsReportedSortBean.ListBean bean = null;
                if (item instanceof EventsReportedSortBean.ListBean) {
                    bean = (EventsReportedSortBean.ListBean) item;
                }
                if (bean == null) {
                    ArmsUtils.makeText("信息有误");
                    return;
                }
                switch (view.getId()) {
                    case R.id.sort_item_layout:
                        if (bean.isIsLeaf()) {
                            EventBus.getDefault().post(new EventBusBean(EventBusConstant.UPDATE_UP_EVENT_SORT, bean), source);
                            appManager.killActivity(EventsReportedSortActivity.class);
                        } else {
                            if (bean.isExpanded()) {
                                adapter.collapse(position);
                            } else {
                                if (!bean.isIsLeaf()) {
                                    mPosition = position;
                                    if (bean.getList() == null || bean.getList().size() <= 0) {
                                        mPresenter.getEvents(bean.getId(), true, source);
                                    } else {
                                        adapter.expand(position);
                                    }
                                }
                            }
                        }
                        break;

                    default:
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
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
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * 设置心数据
     *
     * @param eventFormType eventFormType
     */
    @Override
    public void setData(List<EventsReportedSortBean.ListBean> eventFormType,
                        boolean needAdd) {
        if (needAdd) {
            EventsReportedSortBean.ListBean item = mAdapter.getItem(mPosition);
            item.setList(eventFormType);
            for (EventsReportedSortBean.ListBean bean : eventFormType) {
                bean.setLevel(bean.getLevel() + 1);
            }
            mAdapter.expand(mPosition);
            mAdapter.notifyItemChanged(mPosition);
        } else {
            mAdapter.setNewData(eventFormType);
        }
    }
}
