package com.weique.overhaul.v2.mvp.ui.activity.party;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.weique.overhaul.v2.di.component.DaggerPartyCenterCollectsComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterCollectsContract;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterCollectsPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.PartyCollectsItemAdapter;

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
 * @author GK
 */
@Route(path = RouterHub.APP_PARTYCENTERCOLLECTSACTIVITY)
public class PartyCenterCollectsActivity extends BaseActivity<PartyCenterCollectsPresenter> implements PartyCenterCollectsContract.View {

    @BindView(R.id.recycler_collects)
    RecyclerView recyclerCollects;
    @BindView(R.id.swipeRefreshLayout)
    VerticalSwipeRefreshLayout swipeRefreshLayout;

    @Inject
    PartyCollectsItemAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterCollectsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_party_center_collects;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(R.string.collect);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            assert mPresenter != null;
            mPresenter.getCollectList(true, false);
        });
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setOnLoadMoreListener(() ->
        {
            assert mPresenter != null;
            mPresenter.getCollectList(false, true);
        }, recyclerCollects);
        ArmsUtils.configRecyclerView(recyclerCollects,new LinearLayoutManager(this));
        recyclerCollects.setClipToPadding(false);
        recyclerCollects.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.null_content_layout, recyclerCollects);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PartyContentItemBean.ListBean favoritesBean = (PartyContentItemBean.ListBean) adapter.getItem(position);
            try {
                if (AppUtils.isFastClick()) {
                    return;
                }
                switch (view.getId()) {
                    case R.id.front_layout:
                        mAdapter.notifiItemClose(position);
                        //只能收藏新闻  所以这里类型 固定是新闻
                        ARouter.getInstance()
                                .build(RouterHub.APP_PARTYCONTENTARTICLEDETAILACTIVITY)
                                .withString("id", favoritesBean.getId())
                                .withInt("type", PartyCenterActivity.NEWS_CENTER)
                                .withInt("position", position)
                                .navigation(PartyCenterCollectsActivity.this);
                        break;
                    case R.id.delete_layout:
                        mPresenter.removeCollectStatus(favoritesBean.getId(), position);
                        break;
                    default:
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        assert mPresenter != null;
        mPresenter.getCollectList(false, false);
    }

    /**
     * @param pos 文章中 取消收藏 这里直接移除item
     */
    @Subscriber(tag = RouterHub.APP_PARTYCENTERCOLLECTSACTIVITY)
    public void onEventCallBack(int pos) {
        removeItem(pos);
    }

    @Override
    public void removeItem(int pos) {
        if (mAdapter.getItem(pos) != null) {
            mAdapter.deleteItem(pos);
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

    /**
     * 下拉加载更多
     *
     * @param listBeans partyContentItemBeans
     */
    @Override
    public void setMoreData(List<PartyContentItemBean.ListBean> listBeans) {
        try {
            mAdapter.addData(listBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据
     *
     * @param listBeans partyContentItemBeans
     */
    @Override
    public void setNewData(List<PartyContentItemBean.ListBean> listBeans) {
        try {
            mAdapter.setNewData(listBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
