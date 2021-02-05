package com.weique.overhaul.v2.mvp.ui.activity.visit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerInspectionListComponent;
import com.weique.overhaul.v2.mvp.contract.InspectionListContract;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InspectionListItemBean;
import com.weique.overhaul.v2.mvp.presenter.InspectionListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.InspectionListAdapter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 巡检首页列表
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_INSPECTIONLISTACTIVITY)
public class InspectionListActivity extends BaseActivity<InspectionListPresenter> implements InspectionListContract.View {
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.newly_ipqc_record)
    Button newlyBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.tag_one)
    TextView tagOne;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;

    GridInformationBean Bean = new GridInformationBean();
    private InspectionListAdapter inspectionListAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        assert mPresenter != null;
        mPresenter.GetDepartment();
        mPresenter.getInspectionListData(true, false);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInspectionListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_inspection_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("巡检记录");
        AccessControlUtil.controlByLevelCommunity(newlyBtn);
        initAdapter();
        assert mPresenter != null;
    }


    private void initAdapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getInspectionListData(true, false);
            });
            inspectionListAdapter = new InspectionListAdapter();
            inspectionListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(inspectionListAdapter);
            recyclerView.setClipToPadding(false);
            inspectionListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        ARouter.getInstance().build(RouterHub.APP_INSPECTIONDETAILLISTACTIVITY)
                                .withString("NAME", ((InspectionListItemBean.InspectionRecordBean) adapter.getData().get(position)).getResourceName())
                                .withString("ADDRESS", ((InspectionListItemBean.InspectionRecordBean) adapter.getData().get(position)).getDepartmentFullPath())
                                .withString("TYPE", ((InspectionListItemBean.InspectionRecordBean) adapter.getData().get(position)).getName())
                                .withString("ResourceId", ((InspectionListItemBean.InspectionRecordBean) adapter.getData().get(position)).getResourceId())
                                .withString("ElementTypeId", ((InspectionListItemBean.InspectionRecordBean) adapter.getData().get(position)).getElementTypeId())
                                .navigation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            inspectionListAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getInspectionListData(false, true);
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

    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            inspectionListAdapter.loadMoreEnd(true);
        } else {
            inspectionListAdapter.loadMoreComplete();
        }
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick(R.id.newly_ipqc_record)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            ARouter.getInstance().build(RouterHub.APP_INSPECTIONMAPACTIVITY)
                    .withString(InspectionMapActivity.POINTS_IN_JSON, Bean.getPointsInJSON())
                    .withString(InspectionMapActivity.IS_NEW_POINT, InspectionAddActivity.NEW)
                    .withString(ARouerConstant.TITLE, "新增巡检记录")
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gotoMapTv(GridInformationBean gridInformationBean) {
        Bean = gridInformationBean;
    }

    @Override
    public void setInspectionListData(InspectionListItemBean data, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                inspectionListAdapter.addData(data.getInspectionRecord());
            } else {
                inspectionListAdapter.setNewData(data.getInspectionRecord());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
