package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Intent;
import android.os.Bundle;
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
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerBusinessQuestionComponent;
import com.weique.overhaul.v2.mvp.contract.BusinessQuestionContract;
import com.weique.overhaul.v2.mvp.model.entity.BusinessQuestion;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.BusinessQuestionPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.BusinessQuestionAdapter;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 领导人 企业问题
 * <p>
 * ================================================
 *
 * @author GreatKing
 */

@Route(path = RouterHub.APP_BUSINESSQUESTIONACTIVITY)
public class BusinessQuestionActivity extends BaseActivity<BusinessQuestionPresenter> implements BusinessQuestionContract.View {

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

    private BusinessQuestionAdapter businessQuestionAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessQuestionComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_question;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            assert mPresenter != null;
            setTitle("企业问题");
            initAdapter();
            mPresenter.getBusinessQuestionData(true, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getBusinessQuestionData(true, false);

            });
            businessQuestionAdapter = new BusinessQuestionAdapter();
            businessQuestionAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView,new LinearLayoutManager(this));
            recyclerView.setAdapter(businessQuestionAdapter);
            recyclerView.setClipToPadding(false);
            businessQuestionAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            businessQuestionAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    BusinessQuestion.ListBean listBean = (BusinessQuestion.ListBean) adapter.getItem(position);
                    if (listBean.get类型() == 1) {
                        ARouter.getInstance().build(RouterHub.APP_BUSINESSQUESTIONDETAILACTIVITY)
                                .withString(ARouerConstant.ID, (listBean.getId()))
                                .navigation();
                    } else {
                        ARouter.getInstance().build(RouterHub.APP_ENTERPRISEISSUESEEACTIVITY)
                                .withString(ARouerConstant.ID, (listBean.getId()))
                                .navigation();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            businessQuestionAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getBusinessQuestionData(false, true);
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
            businessQuestionAdapter.loadMoreEnd(true);
        } else {
            businessQuestionAdapter.loadMoreComplete();
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

    /**
     * 更新
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_BUSINESSQUESTIONACTIVITY)
    private void onEventbusCallBack(EventBusBean eventBusBean) {
        if (eventBusBean.getCode() == EventBusConstant.COMMON_UPDATE) {
            mPresenter.getBusinessQuestionData(true, false);
        }
    }

    @Override
    public void setBusinessQuestionData(BusinessQuestion itemBean, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                businessQuestionAdapter.addData(itemBean.getList());
            } else {
                businessQuestionAdapter.setNewData(itemBean.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
