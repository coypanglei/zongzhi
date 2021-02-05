package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerIntegralRuleComponent;
import com.weique.overhaul.v2.mvp.contract.IntegralRuleContract;
import com.weique.overhaul.v2.mvp.model.entity.IntergralDetailsBean;
import com.weique.overhaul.v2.mvp.presenter.IntegralRulePresenter;

import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.ui.adapter.IntegralAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.IntegralRuleAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/19/2020 16:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Route(path = RouterHub.APP_INTEGRAL_RULE_ACTIVITY, name = "积分规则界面")
public class IntegralRuleActivity extends BaseActivity<IntegralRulePresenter> implements IntegralRuleContract.View {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    HorizontalDividerItemDecoration itemDecoration;
    @Inject
    IntegralRuleAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIntegralRuleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        return R.layout.activity_integral_rule;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("考核积分规则");
        mPresenter.getIntegralDetails(true, false);
        initRecycler();
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
    public Context getContext() {
        return this;
    }

    @Override
    public void getIntegralDetails(IntergralDetailsBean integral, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(integral.getList());
        } else {
            mAdapter.setNewData(integral.getList());
        }
    }


    /**
     * 初始化 recycler
     */
    private void initRecycler() {
        try {

            swipeRefreshLayout.setOnRefreshListener(() -> {
                try {
                    mPresenter.getIntegralDetails(true, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    try {
                        swipeRefreshLayout.setEnabled(recyclerView.getChildCount() == 0 || recyclerView.getChildAt(0).getTop() >= 0);
                        super.onScrollStateChanged(recyclerView, newState);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(recyclerView, layoutManager);
            recyclerView.setClipToPadding(false);
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(itemDecoration);
            mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> {
                try {
                    mPresenter.getIntegralDetails(false, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, recyclerView);
        } catch (
                Exception e) {
            e.printStackTrace();
        }

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
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

}
