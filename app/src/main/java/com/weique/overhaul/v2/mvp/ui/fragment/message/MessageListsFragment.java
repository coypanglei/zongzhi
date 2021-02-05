package com.weique.overhaul.v2.mvp.ui.fragment.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerMessageListsComponent;
import com.weique.overhaul.v2.mvp.contract.MessageListsContract;
import com.weique.overhaul.v2.mvp.model.entity.MessageListBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.MessageListsPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.message.MessageDetailActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.MessageListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * ================================================
 */
public class MessageListsFragment extends BaseLazyLoadFragment<MessageListsPresenter> implements MessageListsContract.View {

    private int pos = -1;

    @BindView(R.id.recycler_view)
    RecyclerView messageList;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Inject
    MessageListAdapter messageListAdapter;
    @Inject
    LinearLayoutManager manager;
    @Inject
    HorizontalDividerItemDecoration decoration;


    public static MessageListsFragment newInstance() {
        MessageListsFragment fragment = new MessageListsFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMessageListsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_lists, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        swipeRefresh.setOnRefreshListener(() -> mPresenter.getMessage(true, false));
        initAdapter();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initAdapter() {
        try {
            ArmsUtils.configRecyclerView(messageList, manager);
            messageList.addItemDecoration(decoration);
            messageList.setAdapter(messageListAdapter);
            messageListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            messageListAdapter.setEmptyView(R.layout.null_content_layout, messageList);
            messageListAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getMessage(false, true);
            }, messageList);
            messageListAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    Object item = adapter.getItem(position);
                    if (item instanceof MessageListBean.ListBean) {
                        MessageListsFragment.this.pos = position;
                        MessageListBean.ListBean listBean = (MessageListBean.ListBean) item;
                        ARouter.getInstance().build(RouterHub.APP_MESSAGEDETAILACTIVITY)
                                .withParcelable(MessageDetailActivity.LISTBEAN, listBean)
                                .withString(ARouerConstant.SOURCE, RouterHub.APP_MESSAGELISTACTIVITY)
                                .navigation();
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
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            messageListAdapter.loadMoreEnd(true);
        } else {
            messageListAdapter.loadMoreComplete();
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


    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.getMessage(true, false);
    }

    /**
     * 更新个人信息
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_MESSAGELISTACTIVITY)
    private void onEventbusCallBack(EventBusBean eventBusBean) {
        if (pos > -1) {
            messageListAdapter.getData().get(pos).setIsRead(true);
            messageListAdapter.notifyItemChanged(pos);
        }
    }

    @Override
    public void setNewData(List<MessageListBean.ListBean> listBeans, boolean isLoadMore) {
        if (isLoadMore) {
            messageListAdapter.addData(listBeans);
        } else {
            messageListAdapter.setNewData(listBeans);
        }
    }
}
