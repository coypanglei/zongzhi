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

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerInspectionDetailListComponent;
import com.weique.overhaul.v2.mvp.contract.InspectionDetailListContract;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;
import com.weique.overhaul.v2.mvp.presenter.InspectionDetailListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.InspectionDetailListAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionMapActivity.RESOURCE;
import static com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionMapActivity.WHICH_POINT;


/**
 * 巡检资源详情列表
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_INSPECTIONDETAILLISTACTIVITY)
public class InspectionDetailListActivity extends BaseActivity<InspectionDetailListPresenter> implements InspectionDetailListContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.newly_btn)
    Button newlyBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.head_img)
    ImageView headImg;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.address)
    TextView address;
    @BindView(R.id.top_layout)
    LinearLayout topLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;

    private InspectionDetailListAdapter resourceSearchDetailAdapter;
    private GridInformationBean Bean = new GridInformationBean();


    @Autowired(name = "NAME")
    String pName;
    @Autowired(name = "ADDRESS")
    String pAddress;
    @Autowired(name = "TYPE")
    String pType;
    @Autowired(name = "ResourceId")
    String ResourceId;
    @Autowired(name = "ElementTypeId")
    String ElementTypeId;

    @Inject
    Gson gson;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerInspectionDetailListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_inspection_detail_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("查看巡检记录");

        AccessControlUtil.controlByLevelGrid(newlyBtn);
        name.setText(pName);
        address.setText(pAddress);
        type.setText(pType);
        initApapter();

        assert mPresenter != null;
        mPresenter.GetDepartment(UserInfoUtil.getUserInfo().getDepartmentId());
    }

    private void initApapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getInspectionDetailListData(true, false, ResourceId, ElementTypeId);
            });

            resourceSearchDetailAdapter = new InspectionDetailListAdapter();
            resourceSearchDetailAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(resourceSearchDetailAdapter);
            resourceSearchDetailAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            resourceSearchDetailAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        if (R.id.edit_btn == view.getId()) {
                            ARouter.getInstance().build(RouterHub.APP_INSPECTIONMAPACTIVITY)
                                    .withString(InspectionMapActivity.POINTS_IN_JSON, Bean.getPointsInJSON())
                                    .withSerializable("model", ((ResourceSearchDetailItemBean.ListBean) adapter.getData().get(position)))
                                    .withString(RESOURCE, pName)
                                    .withInt(WHICH_POINT, position)
                                    .withString(InspectionMapActivity.IS_NEW_POINT, InspectionAddActivity.RESET)
                                    .withString(ARouerConstant.TITLE, "编辑巡检记录详情")
                                    .navigation();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            resourceSearchDetailAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getInspectionDetailListData(false, true, ResourceId, ElementTypeId);
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
    protected void onResume() {
        super.onResume();
        assert mPresenter != null;
        mPresenter.getInspectionDetailListData(true, false, ResourceId, ElementTypeId);
    }

    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            resourceSearchDetailAdapter.loadMoreEnd(true);
        } else {
            resourceSearchDetailAdapter.loadMoreComplete();
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


    @OnClick(R.id.newly_btn)
    public void onViewClicked(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.newly_btn:
                    ResourceSearchDetailItemBean.ListBean listBean = null;
                    if (resourceSearchDetailAdapter != null && resourceSearchDetailAdapter.getData().size() > 0) {
                        listBean = resourceSearchDetailAdapter.getData().get(0);
                        ARouter.getInstance().build(RouterHub.APP_INSPECTIONMAPACTIVITY)
                                .withString(InspectionMapActivity.POINTS_IN_JSON, Bean.getPointsInJSON())
                                .withString(InspectionMapActivity.IS_NEW_POINT, InspectionAddActivity.TARGET_NEW_POINT)
                                .withSerializable("model", listBean)
                                .withString(ARouerConstant.TITLE, "新增巡检记录详情")
                                .withString(RESOURCE, pName)
                                .navigation();
                    } else {
                        ArmsUtils.makeText("数据错误,请回上一界面新增");
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void gotoMapTv(GridInformationBean gridInformationBean) {
        Bean = gridInformationBean;
    }

    @Override
    public void setInspectionDetailListData(ResourceSearchDetailItemBean data, boolean isLoadMore) {
        if (isLoadMore) {
            resourceSearchDetailAdapter.addData(data.getList());
        } else {
            resourceSearchDetailAdapter.setNewData(data.getList());
        }
    }
}
