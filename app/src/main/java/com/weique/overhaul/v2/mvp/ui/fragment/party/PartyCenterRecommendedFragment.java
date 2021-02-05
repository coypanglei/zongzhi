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
import com.weique.overhaul.v2.app.utils.GlideImageLoader;
import com.weique.overhaul.v2.di.component.DaggerPartyCenterRecommendedComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterRecommendedContract;
import com.weique.overhaul.v2.mvp.model.entity.PartyCenterRecommendedBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterRecommendedPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.PartyContentRecommendedFragmentAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 党建新闻中心 首页 推荐
 *
 * @author GK
 */
public class PartyCenterRecommendedFragment extends BaseLazyLoadFragment<PartyCenterRecommendedPresenter> implements PartyCenterRecommendedContract.View {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private PartyContentRecommendedFragmentAdapter partyContentRecommendedFragmentAdapter;
    private PartyCenterRecommendedBean mRcommendedBean;
    private View headView;
    private List<PartyCenterRecommendedBean.PartyBaseBean> partyBaseBeans;

    public static PartyCenterRecommendedFragment newInstance(PartyCenterRecommendedBean recommendedBean) {
        PartyCenterRecommendedFragment fragment = new PartyCenterRecommendedFragment();
        fragment.mRcommendedBean = recommendedBean;
        return fragment;
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
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterRecommendedComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_party_center_recommended, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            headView = View.inflate(getContext(), R.layout.party_head_banner, null);
            partyContentRecommendedFragmentAdapter = new PartyContentRecommendedFragmentAdapter(null);
            partyContentRecommendedFragmentAdapter.addHeaderView(headView);
            partyContentRecommendedFragmentAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(partyContentRecommendedFragmentAdapter);
            recyclerView.setClipToPadding(false);
            partyContentRecommendedFragmentAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            partyContentRecommendedFragmentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    PartyCenterRecommendedBean.PartyBaseBean baseBean = (PartyCenterRecommendedBean.PartyBaseBean) adapter.getItem(position);
                    switch (view.getId()) {
                        case R.id.more_layout:
                            assert baseBean != null;
                            EventBus.getDefault().post(new EventBusBean(baseBean.getJumpTargets()), RouterHub.APP_PARTYCENTERACTIVITY);
                            break;
                        case R.id.front_layout:
                            ARouter.getInstance()
                                    .build(RouterHub.APP_PARTYCONTENTARTICLEDETAILACTIVITY)
                                    .withString("id", baseBean.getId())
                                    .withInt("type", baseBean.getJumpTargets())
                                    .navigation(getActivity());
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装 recycler需要数据
     */
    private void initRecyclerData() {
        try {
            partyBaseBeans = new ArrayList<>();
            //添加新闻中心数据
            if (mRcommendedBean.getNews() != null && mRcommendedBean.getNews().size() > 0) {
                partyBaseBeans.add(new PartyCenterRecommendedBean.PartyBaseBean
                        (PartyContentRecommendedFragmentAdapter.PARTY_MAIN_ITEM_ONE, ArmsUtils.getString(getContext(), R.string.news_center), PartyCenterActivity.NEWS_CENTER));
                for (PartyCenterRecommendedBean.NewsBean newsBean : mRcommendedBean.getNews()) {
                    newsBean.setItemType(PartyContentRecommendedFragmentAdapter.PARTY_MAIN_ITEM_SECOND);
                    newsBean.setJumpTargets(PartyCenterActivity.NEWS_CENTER);
                    partyBaseBeans.add(newsBean);
                }
            }
            //添加主题教育
            if (mRcommendedBean.getSubs() != null && mRcommendedBean.getSubs().size() > 0) {
                partyBaseBeans.add(new PartyCenterRecommendedBean.PartyBaseBean
                        (PartyContentRecommendedFragmentAdapter.PARTY_MAIN_ITEM_ONE, ArmsUtils.getString(getContext(), R.string.education), PartyCenterActivity.SUBJECT_EDUCATION));
                for (PartyCenterRecommendedBean.SubsBean subsBean : mRcommendedBean.getSubs()) {
                    subsBean.setItemType(PartyContentRecommendedFragmentAdapter.PARTY_MAIN_ITEM_SECOND);
                    subsBean.setJumpTargets(PartyCenterActivity.SUBJECT_EDUCATION);
                    partyBaseBeans.add(subsBean);
                }
            }
            //添加信息通知
            if (mRcommendedBean.getNotifications() != null && mRcommendedBean.getNotifications().size() > 0) {
                partyBaseBeans.add(new PartyCenterRecommendedBean.PartyBaseBean
                        (PartyContentRecommendedFragmentAdapter.PARTY_MAIN_ITEM_ONE, ArmsUtils.getString(getContext(), R.string.notice), PartyCenterActivity.NOTICE));
                for (PartyCenterRecommendedBean.NotificationsBean notificationsBean : mRcommendedBean.getNotifications()) {
                    notificationsBean.setItemType(PartyContentRecommendedFragmentAdapter.PARTY_MAIN_ITEM_SECOND);
                    notificationsBean.setJumpTargets(PartyCenterActivity.NOTICE);
                    partyBaseBeans.add(notificationsBean);
                }
            }
            //添加党建活动
            if (mRcommendedBean.getPartyMeetings() != null && mRcommendedBean.getPartyMeetings().size() > 0) {
                partyBaseBeans.add(new PartyCenterRecommendedBean.PartyBaseBean
                        (PartyContentRecommendedFragmentAdapter.PARTY_MAIN_ITEM_ONE, ArmsUtils.getString(getContext(), R.string.party_activity), PartyCenterActivity.PARTY_ACTIVITY));
                for (PartyCenterRecommendedBean.PartyMeetingsBean partyMeetingsBean : mRcommendedBean.getPartyMeetings()) {
                    partyMeetingsBean.setItemType(PartyContentRecommendedFragmentAdapter.PARTY_MAIN_ITEM_SECOND);
                    partyMeetingsBean.setJumpTargets(PartyCenterActivity.PARTY_ACTIVITY);
                    partyBaseBeans.add(partyMeetingsBean);
                }
            }
            partyContentRecommendedFragmentAdapter.setNewData(partyBaseBeans);
            hideLoading();
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

    /**
     * 初始化banner
     */
    private void initBanner() {
        try {
            ViewHolder holder = new ViewHolder(headView);
            List images = new ArrayList();
            images.add(ArmsUtils.getStringFromDrawableRes(getContext(), R.drawable.party_banner));
            images.add(ArmsUtils.getStringFromDrawableRes(getContext(), R.drawable.party_banner2));
            holder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            holder.banner.setImageLoader(new GlideImageLoader());
            holder.banner.setImages(images);
            holder.banner.setBannerAnimation(Transformer.DepthPage);
            holder.banner.isAutoPlay(true);
            holder.banner.setDelayTime(3000);
            holder.banner.setIndicatorGravity(BannerConfig.CENTER);
            holder.banner.start();
            holder.banner.setOnBannerListener(position -> {
                //            ArmsUtils.makeText(getActivity(), "position" + position)
            });
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
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(message);
    }


    @Override
    protected void lazyLoadData() {
        initBanner();
        initRecyclerData();
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }

    static class ViewHolder {
        @BindView(R.id.banner)
        Banner banner;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
