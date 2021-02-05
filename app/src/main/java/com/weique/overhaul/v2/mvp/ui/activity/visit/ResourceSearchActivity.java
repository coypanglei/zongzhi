package com.weique.overhaul.v2.mvp.ui.activity.visit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.weique.overhaul.v2.di.component.DaggerResourceSearchComponent;
import com.weique.overhaul.v2.mvp.contract.ResourceSearchContract;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchItemBean;
import com.weique.overhaul.v2.mvp.presenter.ResourceSearchPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.ResourceSearchAdapter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 走访记录
 *
 * @author GK
 */
@Route(path = RouterHub.APP_RESOURCESEARCHACTIVITY)
public class ResourceSearchActivity extends BaseActivity<ResourceSearchPresenter> implements ResourceSearchContract.View {
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout vSwipeRefresh;

    private ResourceSearchAdapter resourceSearchAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerResourceSearchComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_resource_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("走访记录");
        //控制到  社区级别显示
        rightBtnText.setText(ArmsUtils.getString(this, R.string.add_new));
        rightBtnText.setTextSize(17);
        rightBtnText.setTextColor(ArmsUtils.getColor(this, R.color.blue_ff3982f6));
        AccessControlUtil.controlByLevelCommunity(rightBtnText);
        initApapter();
        assert mPresenter != null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        assert mPresenter != null;
        mPresenter.getResourceSearchListData(true, false);
    }

    /**
     * 隐藏或结束加载更多
     * true 结束  false 隐藏
     */
    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            resourceSearchAdapter.loadMoreEnd(true);
        } else {
            resourceSearchAdapter.loadMoreComplete();
        }
    }

    private void initApapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getResourceSearchListData(true, false);
            });
            resourceSearchAdapter = new ResourceSearchAdapter();
            resourceSearchAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView,new LinearLayoutManager(this));
            recyclerView.setAdapter(resourceSearchAdapter);
            recyclerView.setClipToPadding(false);
            resourceSearchAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        ARouter.getInstance().build(RouterHub.APP_RESOURCESEARCHDETAILACTIVITY)
                                .withString("NAME", ((ResourceSearchItemBean.InterViewRecordBean) adapter.getData().get(position)).getResourceName())
                                .withString("ADDRESS", ((ResourceSearchItemBean.InterViewRecordBean) adapter.getData().get(position)).getDepartmentFullPath())
                                .withString("TYPE", ((ResourceSearchItemBean.InterViewRecordBean) adapter.getData().get(position)).getName())
                                .withString("ResourceId", ((ResourceSearchItemBean.InterViewRecordBean) adapter.getData().get(position)).getResourceId())
                                .withString("ElementTypeId", ((ResourceSearchItemBean.InterViewRecordBean) adapter.getData().get(position)).getElementTypeId())
                                .navigation();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            resourceSearchAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getResourceSearchListData(false, true);
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

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick(R.id.right_btn)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            ARouter.getInstance().build(RouterHub.APP_TOURVISITACTIVITY)
                    .withString(TourVisitActivity.TYPE, "")
                    .withString(ARouerConstant.TITLE, "新增走访记录")
                    .navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setAnswerListData(ResourceSearchItemBean data, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                resourceSearchAdapter.addData(data.getInterViewRecord());
            } else {
                resourceSearchAdapter.setNewData(data.getInterViewRecord());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
