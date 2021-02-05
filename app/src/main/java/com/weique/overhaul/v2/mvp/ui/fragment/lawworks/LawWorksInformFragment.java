package com.weique.overhaul.v2.mvp.ui.fragment.lawworks;

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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerLawWorksInformComponent;
import com.weique.overhaul.v2.mvp.contract.LawWorksInformContract;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksInformBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.LawWorksInformPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.LawWorksInformAdapter;

import org.simple.eventbus.Subscriber;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
public class LawWorksInformFragment extends BaseLazyLoadFragment<LawWorksInformPresenter> implements LawWorksInformContract.View {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swr)
    SwipeRefreshLayout swr;

    @Inject
    LawWorksInformAdapter mAdapter;
    @Inject
    LinearLayoutManager manager;

    private int mPosition = -1;

    public static LawWorksInformFragment newInstance() {
        LawWorksInformFragment fragment = new LawWorksInformFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLawWorksInformComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_law_works_inform, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            swr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPresenter.getInformList(true, false);
                }
            });
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, manager);
            recyclerView.setAdapter(mAdapter);
            mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    LawWorksInformBean informBean = (LawWorksInformBean) adapter.getItem(position);
                    if (!informBean.getRead()) {
                        mPosition = position;
                    } else {
                        mPosition = -1;
                    }
                    mPresenter.getInformDetail(informBean.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getInformList(false, true);
            }, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        swr.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swr.setRefreshing(false);
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
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    /**
     * 第一次可见时触发调用,此处实现具体的数据请求逻辑
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.getInformList(true, false);
    }

    @Override
    public void setNewData(List<LawWorksInformBean> informBeans, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(informBeans);
        } else {
            mAdapter.setNewData(informBeans);
        }
    }

    /**
     * 打开通知详情后  文件设置为已读 回调  更新列表
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_LAWWORKSINFORMFRAGMENT)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            if (EventBusConstant.COMMON_UPDATE == eventBusBean.getCode()) {
                if (mPosition >= 0) {
                    mAdapter.getItem(mPosition).setRead(true);
                    mAdapter.notifyItemChanged(mPosition);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
