package com.weique.overhaul.v2.mvp.ui.activity.party;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerAnswerComponent;
import com.weique.overhaul.v2.mvp.contract.AnswerContract;
import com.weique.overhaul.v2.mvp.model.entity.PartyAnswerItemBean;
import com.weique.overhaul.v2.mvp.presenter.AnswerPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.PartyAnswerAdapter;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 党建--答题系统列表
 *
 * @author GK
 */
@Route(path = RouterHub.APP_ANSWERACTIVITY)
public class AnswerActivity extends BaseActivity<AnswerPresenter> implements AnswerContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.v_swipe_refresh)
    VerticalSwipeRefreshLayout vSwipeRefresh;

    private PartyAnswerAdapter partyAnswerAdapter;

    private int gotoAnswer = -1;

    public static final String COMPLETE = "已完成";
    public static final String NOT_COMPLETE = "未完成";

    @Subscriber(tag = RouterHub.APP_ANSWERRESULTACTIVITY)
    private void onEventCallBack(String s) {
        assert mPresenter != null;
        if (gotoAnswer >= 0 && partyAnswerAdapter.getItem(gotoAnswer) != null) {
            PartyAnswerItemBean.SubjectBean partyAnswerItemBean = partyAnswerAdapter.getData().get(gotoAnswer);
            if (partyAnswerItemBean != null && !COMPLETE.equals(partyAnswerItemBean.getType())) {
                partyAnswerAdapter.getData().get(gotoAnswer).setType(COMPLETE);
                partyAnswerAdapter.notifyItemChanged(gotoAnswer);
            }
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAnswerComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_answer;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        assert mPresenter != null;

        initApapter();
        mPresenter.getPartyAnswerData(false, false);
    }

    private void initApapter() {
        try {
            vSwipeRefresh.setOnRefreshListener(() -> {
                mPresenter.getPartyAnswerData(true, false);
            });
            partyAnswerAdapter = new PartyAnswerAdapter();
            partyAnswerAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView,new LinearLayoutManager(this));
            recyclerView.setAdapter(partyAnswerAdapter);
            recyclerView.setClipToPadding(false);
            partyAnswerAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            partyAnswerAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    PartyAnswerItemBean.SubjectBean item = (PartyAnswerItemBean.SubjectBean) adapter.getItem(position);
                    switch (view.getId()) {
                        case R.id.btn_start_answer:
                            if (COMPLETE.equals(item.getType())) {
                                ArmsUtils.makeText(ArmsUtils.getString(AnswerActivity.this, R.string.is_answer));
                            } else {
                                gotoAnswer = position;
                                ARouter.getInstance()
                                        .build(RouterHub.APP_PARTYCENTERSTUDYACTIVITY)
                                        .withString("questionId", (item.getId()))
                                        .navigation(AnswerActivity.this);
                            }
                            break;
                        default:
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            partyAnswerAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getPartyAnswerData(false, true);
            }, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAnswerListData(PartyAnswerItemBean data, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                partyAnswerAdapter.addData(data.getSubject());
            } else {
                partyAnswerAdapter.setNewData(data.getSubject());
            }
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
            partyAnswerAdapter.loadMoreEnd(true);
        } else {
            partyAnswerAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
    }
}
