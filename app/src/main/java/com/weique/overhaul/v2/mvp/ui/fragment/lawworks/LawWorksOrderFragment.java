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
import com.weique.overhaul.v2.di.component.DaggerLawWorksOrderComponent;
import com.weique.overhaul.v2.mvp.contract.LawWorksOrderContract;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderListBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.LawWorksOrderPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.LawWorksOrderAdapter;
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
 * ================================================
 *
 * @author GreatKing
 */
public class LawWorksOrderFragment extends BaseLazyLoadFragment<LawWorksOrderPresenter> implements LawWorksOrderContract.View {
    /**
     * 订单类型
     */
    private static final String TYPE = "TYPE";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swr)
    SwipeRefreshLayout swr;

    @Inject
    LawWorksOrderAdapter mAdapter;
    @Inject
    LinearLayoutManager manager;
    private int type;

    public static LawWorksOrderFragment newInstance(int type) {
        LawWorksOrderFragment fragment = new LawWorksOrderFragment();
        try {
            Bundle bundle = new Bundle();
            bundle.putInt(TYPE, type);
            fragment.setArguments(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerLawWorksOrderComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_law_works_order, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            type = getArguments().getInt(TYPE);
            swr.setOnRefreshListener(() -> mPresenter.getOrderList(true, false, type));
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, manager);
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
            mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            mAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    LawWorksOrderListBean bean = (LawWorksOrderListBean) adapter.getItem(position);
                    ARouter.getInstance().build(RouterHub.APP_LAWWORKSORDERDETAILACTIVITY)
                            .withString(ARouerConstant.ID, bean.getId())
                            .withInt(ARouerConstant.STATUS, type)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_LAWWORKSORDERFRAGMENT)
                            .navigation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> {
                mPresenter.getOrderList(false, true, type);
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
        mPresenter.getOrderList(true, false, type);
    }

    @Override
    public void setNewOrderData(List<LawWorksOrderListBean> lawWorksOrderListBeans, boolean isLoadMore) {
        try {
            if (isLoadMore) {
                mAdapter.addData(lawWorksOrderListBeans);
            } else {
                mAdapter.setNewData(lawWorksOrderListBeans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE ，order_status), source);
     * 订单详情 接单 反馈 时  回调  更新列表
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_LAWWORKSORDERFRAGMENT)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.COMMON_UPDATE:
                    if (type == (int) eventBusBean.getData()) {
                        mPresenter.getOrderList(true, false, type);
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
