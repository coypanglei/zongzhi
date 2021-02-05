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
import com.weique.overhaul.v2.di.component.DaggerAnnouncementComponent;
import com.weique.overhaul.v2.mvp.contract.AnnouncementContract;
import com.weique.overhaul.v2.mvp.model.entity.AnnouncementListBean;
import com.weique.overhaul.v2.mvp.presenter.AnnouncementPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.AnnouncementListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 公告
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
public class AnnouncementFragment extends BaseLazyLoadFragment<AnnouncementPresenter> implements AnnouncementContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Inject
    AnnouncementListAdapter adapter;
    @Inject
    LinearLayoutManager manager;
    @Inject
    HorizontalDividerItemDecoration decoration;

    public static AnnouncementFragment newInstance() {
        AnnouncementFragment fragment = new AnnouncementFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAnnouncementComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_announcement, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        swipeRefresh.setOnRefreshListener(() -> {
            mPresenter.getNotice(true, false);
        });
        initAdapter();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initAdapter() {
        try {
            ArmsUtils.configRecyclerView(recyclerView, manager);
            recyclerView.addItemDecoration(decoration);
            recyclerView.setAdapter(adapter);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            adapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            adapter.setOnLoadMoreListener(() -> {
                mPresenter.getNotice(false, true);
            }, recyclerView);
            adapter.setOnItemClickListener((adapter, view, position) -> {
                if (AppUtils.isFastClick()) {
                    return;
                }
                Object item = adapter.getItem(position);
                if (item instanceof AnnouncementListBean.ListBean) {
                    AnnouncementListBean.ListBean listBean = (AnnouncementListBean.ListBean) item;
                    mPresenter.getNoticeInfo(listBean.getId());
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
            adapter.loadMoreEnd(true);
        } else {
            adapter.loadMoreComplete();
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
        mPresenter.getNotice(true, false);
    }

    /**
     * 设置心数据
     *
     * @param list       list
     * @param isLoadMore isLoadMore
     */
    @Override
    public void setNewData(List<AnnouncementListBean.ListBean> list, boolean isLoadMore) {
        if (isLoadMore) {
            adapter.addData(list);
        } else {
            adapter.setNewData(list);
        }
    }

    @Override
    public void setWebData(AnnouncementListBean.ListBean listBean) {
        ARouter.getInstance().build(RouterHub.APP_COMMONWEBVIEWACTIVITY)
                .withString(ARouerConstant.TITLE, getString(R.string.announcement_detail))
                .withString(ARouerConstant.WEB_CONTENT, listBean.getContent())
                .navigation();
    }
}
