package com.weique.overhaul.v2.dynamic.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.chad.newlibrary.adapter.base.ProviderBinderAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.dynamic.di.component.DaggerCommonRefreshComponent;
import com.weique.overhaul.v2.dynamic.mvp.contract.CommonRefreshContract;
import com.weique.overhaul.v2.dynamic.mvp.presenter.CommonRefreshPresenter;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/04/2021 14:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

public class CommonRefreshActivity extends BaseActivity<CommonRefreshPresenter> implements CommonRefreshContract.View {

    /**
     * 上个界面跳转的参数
     */
    @Autowired(name = Constant.COMMON_COLLECTION)
    CommonCollectBean commonCollectBean;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.swipe_refresh)
    VerticalSwipeRefreshLayout swipeRefresh;


    private ProviderBinderAdapter baseBinderAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommonRefreshComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_common_refresh;
    }

    /**
     * 初始化 recyclerview
     */
    private void initRv() {
        try {
            baseBinderAdapter = new ProviderBinderAdapter();
            ArmsUtils.configRecyclerView(rv, new LinearLayoutManager(this));
            rv.setAdapter(baseBinderAdapter);
            /*
             *  获取数据赋值
             */
            assert mPresenter != null;
            /*
             *  初始化数据
             */
            mPresenter.addItemBinders(commonCollectBean);
            mPresenter.getAllData(commonCollectBean, true);
            /*
             *  刷新
             */
            swipeRefresh.setOnRefreshListener(() -> mPresenter.getAllData(commonCollectBean, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            baseBinderAdapter.getLoadMoreModule().loadMoreEnd(true);
        } else {
            baseBinderAdapter.getLoadMoreModule().loadMoreComplete();
        }
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRv();
    }

    @Override
    public void showLoading() {
        showProgressDialog();
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
    public ProviderBinderAdapter getBinderAdapter() {
        return baseBinderAdapter;
    }

}
