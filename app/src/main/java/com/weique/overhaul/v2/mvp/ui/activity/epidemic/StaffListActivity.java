package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.PhoneInfoUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerStaffListComponent;
import com.weique.overhaul.v2.mvp.contract.StaffListContract;
import com.weique.overhaul.v2.mvp.model.entity.StaffListBean;
import com.weique.overhaul.v2.mvp.presenter.StaffListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.StaffListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 职工信息列表界面
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_STAFFLISTACTIVITY)
public class StaffListActivity extends BaseActivity<StaffListPresenter> implements StaffListContract.View {

    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    StaffListAdapter mAdapter;
    @Inject
    HorizontalDividerItemDecoration decoration;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.sr)
    SwipeRefreshLayout sr;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStaffListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_staff_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("员工列表");
        rightBtnText.setText("历史温度");
        initAdapter();
        mPresenter.getStaffList(true, false);
    }

    private void initAdapter() {
        try {
            sr.setOnRefreshListener(() -> {
                mPresenter.getStaffList(true, false);
            });
            ArmsUtils.configRecyclerView(rv, linearLayoutManager);
            rv.setAdapter(mAdapter);
            rv.addItemDecoration(decoration);
            mAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getStaffList(false, true);
            }, rv);
            mAdapter.setEmptyView(R.layout.null_content_layout, rv);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    StaffListBean.ListBean item = (StaffListBean.ListBean) adapter.getItem(position);
                    switch (view.getId()) {
                        case R.id.all_item:
        //                    if (false) {
        //                        ArmsUtils.makeText("体温信息,已上报");
        //                        return;
        //                    }
        //                    new HeatDialog(this, (dialog, contents) -> {
        //                        if (StringUtil.isNullString(contents)) {
        //                            ArmsUtils.makeText("请输入体温");
        //                            return;
        //                        }
        //                        mPresenter.sendStaffHeat(contents);
        //                        dialog.dismiss();
        //                    }).show();
                            break;
                        case R.id.phone:
                            if (StringUtil.isNullString(item.getTel()) || item.getTel().length() != 11) {
                                ArmsUtils.makeText("电话号码不正确");
                                return;
                            }
                            PhoneInfoUtil.callPhone(item.getTel(), StaffListActivity.this);
                            break;
                        default:

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        sr.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        sr.setRefreshing(false);
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

    @Override
    public void setData(List<StaffListBean.ListBean> list, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
    }


    @OnClick({R.id.right_btn, R.id.add})
    public void OnViewClick(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.right_btn:
                    ARouter.getInstance().build(RouterHub.APP_STAFFTEMPERATUREACTIVITY)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_STAFFLISTACTIVITY).navigation();
                    break;
                case R.id.add:
                    ARouter.getInstance().build(RouterHub.APP_STAFFTEMPERATUREACTIVITY).navigation();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
