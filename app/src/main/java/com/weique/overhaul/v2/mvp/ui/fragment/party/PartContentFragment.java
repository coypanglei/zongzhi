package com.weique.overhaul.v2.mvp.ui.fragment.party;

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
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerPartContentComponent;
import com.weique.overhaul.v2.mvp.contract.PartContentContract;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.PartContentPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.PartyContentFragmentAdapter;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GK
 */
public class PartContentFragment extends BaseLazyLoadFragment<PartContentPresenter> implements PartContentContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PartyContentFragmentAdapter partyContentFragmentAdapter;
    /**
     * 请求列表 时 table 参数
     */
    private static final String InfoTypeId = "infoTypeId";

    /**
     * 区分 调用接口
     */
    private static final String TYPE = "type";

    public static PartContentFragment newInstance(String infoTypeId, int type) {
        PartContentFragment fragment = new PartContentFragment();
        Bundle args = new Bundle();
        args.putString(InfoTypeId, infoTypeId);
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPartContentComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    /**
     * 接收 刷新界面  事件
     */
    @Subscriber(tag = PartyCenterActivity.APP_PARTYCENTERACTIVITY_FRAGMENTS_TWO_REFRESH)
    private void onEventCallBack(EventBusBean eventBusBean) {
        if (isVisibleToUser()) {
            switch (eventBusBean.getCode()) {
                case PartyCenterActivity.PARTY_SHOW_REFRESH:
//                    setDataLoaded(false);

                    if (isVisible()) {
                        lazyLoadData();
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_part_content, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            partyContentFragmentAdapter = new PartyContentFragmentAdapter();
            partyContentFragmentAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(partyContentFragmentAdapter);
            recyclerView.setClipToPadding(false);
            partyContentFragmentAdapter.setOnLoadMoreListener(() -> {
                assert mPresenter != null;
                mPresenter.getPartyDataByType(false, true, getArguments().getString(InfoTypeId), getArguments().getInt(TYPE));
            }, recyclerView);
            partyContentFragmentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    PartyContentItemBean.ListBean baseBean = (PartyContentItemBean.ListBean) adapter.getItem(position);
                    switch (view.getId()) {
                        case R.id.front_layout:
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PARTYCONTENTARTICLEDETAILACTIVITY)
                                    .withString("id", (baseBean.getId()))
                                    .withInt("type", getArguments().getInt(TYPE))
                                    .navigation(getContext());
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            partyContentFragmentAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
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
        mPresenter.getPartyDataByType(false, false, getArguments().getString(InfoTypeId), getArguments().getInt(TYPE));
    }

    @Override
    public void setData(@Nullable Object data) {
        try {
            List<PartyContentItemBean.ListBean> itemBeans = (List<PartyContentItemBean.ListBean>) data;
            partyContentFragmentAdapter.setNewData(itemBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载更多数据
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void addMoreData(@Nullable Object data) {
        try {
            List<PartyContentItemBean.ListBean> itemBeans = (List<PartyContentItemBean.ListBean>) data;
            assert itemBeans != null;
            partyContentFragmentAdapter.addData(itemBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        EventBus.getDefault().post(new EventBusBean(PartyCenterActivity.PARTY_SHOW_REFRESH), RouterHub.APP_PARTYCENTERACTIVITY);
    }

    @Override
    public void hideLoading() {
        EventBus.getDefault().post(new EventBusBean(PartyCenterActivity.PARTY_HIDE_REFRESH), RouterHub.APP_PARTYCENTERACTIVITY);
    }

    @Override
    public void LoadingMore(boolean b) {
        if (b) {
            partyContentFragmentAdapter.loadMoreEnd(true);
        } else {
            partyContentFragmentAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
    }


    @Override
    public BaseFragment getFragment() {
        return this;
    }
}
