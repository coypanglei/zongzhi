package com.weique.overhaul.v2.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerTaskAgentsComponent;
import com.weique.overhaul.v2.mvp.contract.TaskAgentsContract;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.TaskAgentsPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedLookActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.TaskAgentsAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/13/2020 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 *
 * @author GreatKing
 */
public class TaskAgentsFragment extends BaseLazyLoadFragment<TaskAgentsPresenter> implements TaskAgentsContract.View {

    @Inject
    HorizontalDividerItemDecoration decoration;
    @BindView(R.id.recycler)
    RecyclerView recycler;


    private TaskAgentsAdapter taskAgentsAdapter;


    public static TaskAgentsFragment newInstance() {
        TaskAgentsFragment fragment = new TaskAgentsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTaskAgentsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_agents, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
    }


    @Override
    public void setData(@Nullable Object data) {
    }

    private void initAdapter() {
        try {
            taskAgentsAdapter = new TaskAgentsAdapter();
            taskAgentsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recycler,new LinearLayoutManager(getContext()));
            recycler.setAdapter(taskAgentsAdapter);
            recycler.addItemDecoration(decoration);
            recycler.setClipToPadding(false);
            taskAgentsAdapter.setEmptyView(R.layout.null_content_home_layout, recycler);
            taskAgentsAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    Object item = adapter.getItem(position);
                    if (item instanceof EventsReportedBean.ListBean) {
                        EventsReportedBean.ListBean listBean = (EventsReportedBean.ListBean) item;
                        ARouter.getInstance()
                                .build(RouterHub.APP_EVENTSREPORTEDLOOKACTIVITY)
                                .withParcelable(EventsReportedLookActivity.LIST_BEAN, listBean)
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT)
                                .navigation();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            taskAgentsAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getTaskList("", EventsReportedBean.TRANSACTOR, false, true);
            }, recycler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            taskAgentsAdapter.loadMoreEnd(true);
        } else {
            taskAgentsAdapter.loadMoreComplete();
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

    }


    @Override
    public void setDataTask(List<EventsReportedBean.ListBean> list, boolean isLoadMore) {
        if (isLoadMore) {
            taskAgentsAdapter.addData(list);
        } else {
            taskAgentsAdapter.setNewData(list);
        }
    }


    @Subscriber(tag = RouterHub.APP_MAINACTIVITY_HOMEFRAGMENT)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.SELECT:
                    mPresenter.getTaskList("", EventsReportedBean.TRANSACTOR, true, false);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    @Override
    protected void lazyLoadData() {
        assert mPresenter != null;
        mPresenter.getTaskList("", EventsReportedBean.TRANSACTOR, true, false);
    }
}
