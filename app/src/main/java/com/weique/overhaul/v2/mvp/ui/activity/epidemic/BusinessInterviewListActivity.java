package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerBusinessInterviewComponent;
import com.weique.overhaul.v2.mvp.contract.BusinessInterviewContract;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInterViewListBean;
import com.weique.overhaul.v2.mvp.presenter.BusinessInterviewPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.BusinessInterviewAdapter;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 领导人 查看 企业 寻访 列表
 * <p>
 * ================================================
 */
@Route(path = RouterHub.APP_BUSINESSINTERVIEWLISTACTIVITY)
public class BusinessInterviewListActivity extends BaseActivity<BusinessInterviewPresenter> implements BusinessInterviewContract.View {

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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout vSwipeRefresh;
    @BindView(R.id.fill_line)
    View fillLine;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.up_issue)
    LinearLayout upIssue;

    private BusinessInterviewAdapter businessInterviewAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessInterviewComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_interview;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            assert mPresenter != null;
            mPresenter.getBusinessInterviewListData(true, false);
            setTitle("企业走访");
            initAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getBusinessInterviewListData(true, false);

            });
            businessInterviewAdapter = new BusinessInterviewAdapter();
            businessInterviewAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView,new LinearLayoutManager(this));
            recyclerView.setAdapter(businessInterviewAdapter);
            recyclerView.setClipToPadding(false);
            businessInterviewAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            businessInterviewAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    ARouter.getInstance().build(RouterHub.APP_BUSINESSINTERVIEWDETAILACTIVITY)
                            .withString(ARouerConstant.ID, ((BusinessInterViewListBean.ListBean) adapter.getData().get(position)).getId())
                            .navigation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            businessInterviewAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getBusinessInterviewListData(false, true);
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
            businessInterviewAdapter.loadMoreEnd(true);
        } else {
            businessInterviewAdapter.loadMoreComplete();
        }
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
        finish();
    }


    @Override
    public void setBusinessInterviewListData(BusinessInterViewListBean itemBean, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                businessInterviewAdapter.addData(itemBean.getList());
            } else {
                businessInterviewAdapter.setNewData(itemBean.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
