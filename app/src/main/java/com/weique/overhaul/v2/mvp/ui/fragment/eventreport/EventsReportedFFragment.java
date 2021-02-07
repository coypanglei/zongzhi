package com.weique.overhaul.v2.mvp.ui.fragment.eventreport;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.service.localtion.LocSdkClient;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.UserGridUtil;
import com.weique.overhaul.v2.di.component.DaggerEventsReportedFComponent;
import com.weique.overhaul.v2.mvp.contract.EventsReportedFContract;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedLookBean;
import com.weique.overhaul.v2.mvp.presenter.EventsReportedFPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedCrudActivity;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedLookActivity;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2021 14:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class EventsReportedFFragment extends BaseLazyLoadFragment<EventsReportedFPresenter> implements EventsReportedFContract.View {

    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout refreshLayout;
    /**
     * 我的
     */
    public static final int MY = 0;
    /**
     * 处置
     */
    public static final int HANDLE = 1;
    /**
     * 受理
     */
    public static final int ACCEPT = 2;
    /**
     * 协同
     */
    public static final int SYNERGY = 3;
    /**
     * 核查
     */
    public static final int INSPECT = 4;


    EventsReportedAdapter mAdapter;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.add)
    FloatingActionButton add;
    private String mOrderSortType;
    /**
     * 是 新增还是  修改
     */
    private int action = -1;
    /**
     * 修改信息的  位置
     */
    private int alertPosition = -1;

    private int mOrderStatus = -2;

    private String keyword;
    private EventsReportedBean.ListBean listBean;
    private String gridJson;
    private static final String TYPE_ = "type";
    private int type;

    public static EventsReportedFFragment newInstance(int type) {
        EventsReportedFFragment fragment = new EventsReportedFFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerEventsReportedFComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events_reported, container, false);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    public void setKeyword(String keyword) {
        try {
            this.keyword = keyword;
            setDataLoaded(false);
            if (isVisibleToUser()) {
                tryLoadData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOrderStatus(int mOrderStatus) {
        this.mOrderStatus = mOrderStatus;
        //只有我的上报进来  再去 根据状态刷新
        if (MY == type) {
            setDataLoaded(false);
            if (isVisibleToUser()) {
                tryLoadData();
            }
        }
    }

    public void setOrderSortType(String mOrderSortType) {
        this.mOrderSortType = mOrderSortType;
        setDataLoaded(false);
        if (isVisibleToUser()) {
            tryLoadData();
        }
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            type = getArguments().getInt(TYPE_);
            refreshLayout.setOnRefreshListener(() -> {
                getOrderList(true, false, keyword);
            });
            ArmsUtils.configRecyclerView(recycler, new LinearLayoutManager(getContext()));
            recycler.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                    .colorResId(R.color.theme_background)
                    .sizeResId(R.dimen.dp_5)
                    .build());
            mAdapter = new EventsReportedAdapter();
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            recycler.setAdapter(mAdapter);
            if (MY != type) {
                add.setVisibility(View.GONE);
            } else {
                AccessControlUtil.controlByLevelCommunity(add);
            }
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    switch (view.getId()) {
                        case R.id.item_view:
                            Object item = adapter.getItem(position);
                            if (item instanceof EventsReportedBean.ListBean) {
                                listBean = (EventsReportedBean.ListBean) item;
                                if (listBean.getEnumOrderStatus() ==
                                        EventsReportedBean.ListBean.EventsReportedEnumNewBean.TS) {
                                    alertPosition = position;
                                    action = EventBusConstant.ALERT;
                                } else {
                                    action = EventBusConstant.SELECT;
                                }
                                mPresenter.gridOperatorInformation();
                            }
                            break;
                        default:
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> getOrderList(false, true, keyword), recycler);
            mAdapter.setEmptyView(R.layout.null_content_layout, recycler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.add})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.add:
                    action = EventBusConstant.ADD;
                    mPresenter.gridOperatorInformation();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取订单列表
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     * @param keyword       keyword
     */
    private void getOrderList(boolean pullToRefresh, boolean isLoadMore, String keyword) {
        if (mOrderStatus >= EventsReportedBean.ListBean.EventsReportedEnumNewBean.TS) {
            mPresenter.getEvents(pullToRefresh, isLoadMore, keyword, type, mOrderStatus, mOrderSortType);
        } else {
            mPresenter.getEvents(pullToRefresh, isLoadMore, keyword, type, mOrderSortType);
        }
    }

    @Override
    public void getGridInfoSuccess(String pointsInJSON) {
        gridJson = pointsInJSON;
        mPresenter.getIsInGrid();
    }

    /**
     * 设置界面列表数据
     */
    @Override
    public void setNewData(List<EventsReportedBean.ListBean> newData, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(newData);
        } else {
            mAdapter.setNewData(newData);
        }
    }

    /**
     *
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
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
    protected void lazyLoadData() {
        getOrderList(false, false, keyword);
    }

    @Override
    public void setAddressCanChanged(Boolean o) {
        try {
            List<List<LatLng>> latLngLists = new ArrayList<>();
            List<PolygonOptions> ooPolygons = new ArrayList<>();
            UserGridUtil.gridJsonToListLatLng(gridJson, latLngLists, ooPolygons);
            // 若用百度定位sdk,需要在此初始化定位SDK
            LocSdkClient.getInstance(getActivity()).getLocationStart();
            BDLocation location =
                    LocSdkClient.getInstance(getActivity()).getLocationStart()
                            .getLastKnownLocation();
            if (location == null || latLngLists.size() <= 0) {
                ArmsUtils.makeText("获取您的定位失败");
                return;
            }
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            //不允许修改坐标  并 用户不在网格内
            boolean select = true;
            if (action == EventBusConstant.ALERT || action == EventBusConstant.ADD) {
                select = false;
            }
            if (!o && !UserGridUtil.pointInGrid(latLng, latLngLists) && !select) {
                ArmsUtils.makeText("您不在负责区域内，无法上报事件");
            } else {
                EventsReportedLookBean bean = new EventsReportedLookBean();
                bean.setEnumOrderStatus(EventsReportedBean.ListBean.EventsReportedEnumNewBean.TS);
                if (action == EventBusConstant.ALERT) {
                    ARouter.getInstance()
                            .build(RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                            .withString(ARouerConstant.ID, listBean.getEventRecordId())
                            .withString(EventsReportedCrudActivity.CUST_ID, listBean.getCustId())
                            .withInt(ARouerConstant.STATUS, EventBusConstant.ALERT)
                            .withString(MapActivity.POINTS_IN_JSON, gridJson)
                            .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, o)
                            .navigation();
                } else if (action == EventBusConstant.SELECT) {
                    ARouter.getInstance()
                            .build(RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                            .withParcelable(EventsReportedLookActivity.LIST_BEAN, listBean)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDACTIVITY)
                            .withString(MapActivity.POINTS_IN_JSON, gridJson)
                            .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, o)
                            .withInt(ARouerConstant.TYPE, type)
                            .navigation();
                } else if (action == EventBusConstant.ADD) {
                    ARouter.getInstance().build(RouterHub.APP_EVENTSREPORTEDCRUDACTIVITY)
                            .withParcelable(EventsReportedCrudActivity.EVENTSREPORTEDLOOKBEAN, bean)
                            .withInt(ARouerConstant.STATUS, EventBusConstant.ADD)
                            .withString(MapActivity.POINTS_IN_JSON, gridJson)
                            .withBoolean(MapActivity.ADDRESS_CAN_CHANGED, o)
                            .navigation();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePos() {
        try {
            mAdapter.remove(alertPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
