package com.weique.overhaul.v2.mvp.ui.fragment.party;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerPartyCenterNewsComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterNewsContract;
import com.weique.overhaul.v2.mvp.model.entity.PartyCenterRecommendedBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterNewsPresenter;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.BbcChoicePopup;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
public class PartyCenterNewsFragment extends BaseLazyLoadFragment<PartyCenterNewsPresenter> implements PartyCenterNewsContract.View {

    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.stl_layout)
    LinearLayout stlLayout;
    @BindView(R.id.more_table)
    ImageView moreTable;
    @BindView(R.id.vp)
    ViewPager vp;
    private ArrayList<Fragment> mFragments;
    private MyPagerAdapter mAdapter;
    private PartyCenterRecommendedBean recommendedBean;
    private BbcChoicePopup bbcChoicePopup;

    public static PartyCenterNewsFragment newInstance() {
        PartyCenterNewsFragment fragment = new PartyCenterNewsFragment();
        return fragment;
    }


    public static String lable;

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterNewsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_party_center_news, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 设置显示全部 fragment
     */
    public void setVpPositionToAll() {
        tl2.setCurrentTab(1);
    }

    @Override
    public void setData(@Nullable Object data) {
        try {
            mFragments = new ArrayList<>();
            recommendedBean = (PartyCenterRecommendedBean) data;
            List<PartyCenterRecommendedBean.TypesBean> typesBeans = recommendedBean.getTypes();
            typesBeans.add(0, new PartyCenterRecommendedBean.TypesBean("0", ArmsUtils.getString(getContext(), R.string.recommend)));
            typesBeans.add(1, new PartyCenterRecommendedBean.TypesBean("", ArmsUtils.getString(getContext(), R.string.all)));
            List<String> strList = new ArrayList<>();
            for (PartyCenterRecommendedBean.TypesBean typesBean : typesBeans) {
                strList.add(typesBean.getName());
            }
            String[] strings1 = strList.toArray(new String[strList.size()]);
            for (PartyCenterRecommendedBean.TypesBean typesBean : typesBeans) {
                if ("0".equals(typesBean.getId())) {
                    mFragments.add(PartyCenterRecommendedFragment.newInstance(recommendedBean));
                } else {
                    mFragments.add(PartContentFragment.newInstance(typesBean.getId(), PartyCenterActivity.NEWS_CENTER));
                }
            }
            mAdapter = new MyPagerAdapter(getChildFragmentManager(), mFragments, strings1);
            vp.setAdapter(mAdapter);
            tl2.setViewPager(vp, strings1);
            vp.setOffscreenPageLimit(strings1.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.more_table)
    public void onClick() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (recommendedBean.getTypes() != null && recommendedBean.getTypes().size() > 0) {
                if (bbcChoicePopup == null) {
                    bbcChoicePopup = new BbcChoicePopup(getActivity(), recommendedBean.getTypes());
                }
                bbcChoicePopup.showPopupWindow(stlLayout);
                bbcChoicePopup.setListItemClickListener(pos -> {
                    tl2.setCurrentTab(pos);
                    bbcChoicePopup.dismiss();
                });
            }
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

    /**
     * 接收 刷新界面  事件
     */
    @Subscriber(tag = PartyCenterActivity.APP_PARTYCENTERACTIVITY_FRAGMENTS_ONE_REFRESH)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            if (isVisibleToUser()) {
                switch (eventBusBean.getCode()) {
                    case PartyCenterActivity.PARTY_SHOW_REFRESH:
                        //看下是否已经拿到类型
                        if (isVisible()) {
                            if (recommendedBean != null && recommendedBean.getTypes() != null && recommendedBean.getTypes().size() > 0) {
                                EventBus.getDefault().post(eventBusBean, PartyCenterActivity.APP_PARTYCENTERACTIVITY_FRAGMENTS_TWO_REFRESH);
                                return;
                            }
                            //没拿到 就去拿
                            //                    setDataLoaded(false);

                            lazyLoadData();
                        }
                        break;
                    default:
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    protected void lazyLoadData() {
        mPresenter.getPartyMainData();
    }


    @Override
    public BaseFragment getFragment() {
        return this;
    }
}
