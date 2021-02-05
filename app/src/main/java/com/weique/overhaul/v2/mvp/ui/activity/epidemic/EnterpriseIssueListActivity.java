package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Context;
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
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerEnterpriseIssueComponent;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueContract;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueListBean;
import com.weique.overhaul.v2.mvp.model.entity.LeaderBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EnterpriseIssuePresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.EnterpriseIssueAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:  企业人端入口
 * <p>企业上报问题列表
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_ENTERPRISEISSUELISTACTIVITY)
public class EnterpriseIssueListActivity extends BaseActivity<EnterpriseIssuePresenter> implements EnterpriseIssueContract.View {
    @Inject
    LinearLayoutManager linearLayoutManager;
    @Inject
    EnterpriseIssueAdapter mAdapter;
    @Inject
    HorizontalDividerItemDecoration decoration;
    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.name_department)
    TextView nameDepartment;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.t_layout)
    RelativeLayout tLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.up_issue)
    LinearLayout upIssue;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEnterpriseIssueComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_enterprise_issue;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            swipeRefresh.setOnRefreshListener(() -> {
                mPresenter.getEnterpriseIssueList(true, false);
            });
            ArmsUtils.configRecyclerView(recyclerView, linearLayoutManager);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    EnterpriseIssueListBean.ListBean listBean = (EnterpriseIssueListBean.ListBean) adapter.getItem(position);
                    ARouter.getInstance().build(RouterHub.APP_ENTERPRISEISSUESEEACTIVITY)
                            .withString(ARouerConstant.ID, (listBean.getId())).navigation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getEnterpriseIssueList(false, true);
            }, recyclerView);
            mPresenter.getEnterpriseIssueList(true, false);
            mPresenter.getMyEntLeaderInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            if (b) {
                mAdapter.loadMoreEnd(true);
            } else {
                mAdapter.loadMoreComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.submit})
    public void onClickLisenter(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            ARouter.getInstance().build(RouterHub.APP_ENTERPRISEISSUEEDITACTIVITY).navigation();
        } catch (Exception e) {
            e.printStackTrace();
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
    public Context getContext() {
        return this;
    }

    @Override
    public void setData(List<EnterpriseIssueListBean.ListBean> o, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(o);
        } else {
            mAdapter.setNewData(o);
        }
    }

    /**
     * 更新
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_ENTERPRISEISSUELISTACTIVITY)
    private void onEventbusCallBack(EventBusBean eventBusBean) {
        if (eventBusBean.getCode() == EventBusConstant.COMMON_UPDATE) {
            mPresenter.getEnterpriseIssueList(true, false);
        }
    }

    /**
     * 设置领导人信息
     *
     * @param leaderData 领导人信息
     */
    @Override
    public void setLeaderData(LeaderBean leaderData) {
        try {
            nameDepartment.setText("姓名：" + (leaderData.get姓名()));
            phone.setText("电话：" + (leaderData.get联系电话()));
            email.setText("邮箱：" + (leaderData.getEmail()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
