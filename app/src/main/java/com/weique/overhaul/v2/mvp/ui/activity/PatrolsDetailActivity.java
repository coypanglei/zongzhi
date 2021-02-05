package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerPatrolsDetailComponent;
import com.weique.overhaul.v2.mvp.contract.PatrolsDetailContract;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.presenter.PatrolsDetailPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionAddActivity;
import com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionMapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.PatrolsDetailListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.LAT;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.LNG;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.REMIND;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity.TYPE;


/**
 * @author GreatKing
 */
@Route(path = RouterHub.APP_PATROLSDETAILACTIVITY)
public class PatrolsDetailActivity extends BaseActivity<PatrolsDetailPresenter> implements PatrolsDetailContract.View {
    @Inject
    Gson gson;

    @Autowired(name = "id")
    String id;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;

    private PatrolsDetailListAdapter patrolsDetailListAdapter;
    GridInformationBean Bean = new GridInformationBean();


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPatrolsDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_patrols_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            setTitle("巡查任务详情");
            initAdapter();
            assert mPresenter != null;
            mPresenter.GetDepartment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getPatrolsDetailListData(true, false, id);
            });
            patrolsDetailListAdapter = new PatrolsDetailListAdapter();
            patrolsDetailListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(patrolsDetailListAdapter);
            recyclerView.setClipToPadding(false);
            patrolsDetailListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        if (((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)).getEnumElementProcType() == 1) {
                            String pointInJson = ((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)).getPointInJson();
                            String picList = ((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)).getIVImageUrlsInJson();
                            List<String> jsonList = new ArrayList<>();
                            jsonList = gson.fromJson(picList, new TypeToken<List<String>>() {
                            }.getType());
                            StandardAddressBean.LonAndLat lonAndLat = null;
                            try {
                                lonAndLat = gson.fromJson(pointInJson, StandardAddressBean.LonAndLat.class);
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                            if (lonAndLat == null) {
                                ARouter.getInstance().build(RouterHub.APP_TOURVISITACTIVITY)
                                        .withString(TYPE, "TASK")
                                        .withBoolean("status", (((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)).isStatus()))
                                        .withString(REMIND, ((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)).getMemo())
                                        .withSerializable("TASK_MODEL", ((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)))
                                        .withString(ARouerConstant.TITLE, "新增走访记录")
                                        .navigation();
                            } else {
                                ARouter.getInstance().build(RouterHub.APP_TOURVISITACTIVITY)
                                        .withString(TYPE, "TASK")
                                        .withBoolean("status", (((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)).isStatus()))
                                        .withString(REMIND, ((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)).getMemo())
                                        .withStringArrayList("list", (ArrayList<String>) jsonList)
                                        .withString(ARouerConstant.TITLE, "新增走访记录")
                                        .withDouble(LNG, lonAndLat.getLng())
                                        .withDouble(LAT, lonAndLat.getLat())
                                        .withSerializable("TASK_MODEL", ((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)))
                                        .navigation();
                            }


                        } else {
                            ARouter.getInstance().build(RouterHub.APP_INSPECTIONMAPACTIVITY)
                                    .withString(InspectionMapActivity.POINTS_IN_JSON, Bean.getPointsInJSON())
                                    .withString(InspectionMapActivity.IS_NEW_POINT, InspectionAddActivity.NEW_TASK)
                                    .withSerializable("TASK_MODEL", ((PatrolsDetailItemBean.ElementsBean) adapter.getData().get(position)))
                                    .withString(ARouerConstant.TITLE, "巡检记录详情")
                                    .navigation();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });

            patrolsDetailListAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getPatrolsDetailListData(false, true, id);
            }, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        vSwipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        vSwipeRefresh.setRefreshing(false);
    }

    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            patrolsDetailListAdapter.loadMoreEnd(true);
        } else {
            patrolsDetailListAdapter.loadMoreComplete();
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
    protected void onResume() {
        super.onResume();
        assert mPresenter != null;
        mPresenter.getPatrolsDetailListData(true, false, id);
    }

    @Override
    public void setPatrolsDetailListData(PatrolsDetailItemBean itemBean, boolean isLoadMore) {
        if (isLoadMore) {
            patrolsDetailListAdapter.addData(itemBean.getElements());
        } else {
            patrolsDetailListAdapter.setNewData(itemBean.getElements());
        }
    }

    @Override
    public void gotoMapTv(GridInformationBean gridInformationBean) {
        Bean = gridInformationBean;
    }
}
