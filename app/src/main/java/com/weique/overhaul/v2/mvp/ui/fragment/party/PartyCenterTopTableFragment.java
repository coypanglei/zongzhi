package com.weique.overhaul.v2.mvp.ui.fragment.party;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.weique.overhaul.v2.di.component.DaggerPartyCenterTopTableComponent;
import com.weique.overhaul.v2.mvp.contract.PartyCenterTopTableContract;
import com.weique.overhaul.v2.mvp.model.entity.PartyCenterRecommendedBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.PartyCenterTopTablePresenter;
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
 * @author GK
 */
public class PartyCenterTopTableFragment extends BaseLazyLoadFragment<PartyCenterTopTablePresenter> implements PartyCenterTopTableContract.View {


    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.stl_layout)
    LinearLayout stlLayout;
    private ArrayList<Fragment> mFragments;
    private MyPagerAdapter mAdapter;
    private List<PartyCenterRecommendedBean.TypesBean> types;
    private BbcChoicePopup bbcChoicePopup;

    private static final String TYPE = "type";

    public static PartyCenterTopTableFragment newInstance(int type) {
        PartyCenterTopTableFragment fragment = new PartyCenterTopTableFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPartyCenterTopTableComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_party_center_top_table, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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

                        if (isVisible()) {
                            if (types != null && types.size() > 0) {
                                EventBus.getDefault().post(eventBusBean, PartyCenterActivity.APP_PARTYCENTERACTIVITY_FRAGMENTS_TWO_REFRESH);
                                return;
                            }
                            //                    setDataLoaded(false);

                            lazyLoadData();
                        }
                    default:
                }
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

    @Override
    public void setData(@Nullable Object data) {
        try {
            if (data instanceof PartyCenterRecommendedBean) {
                mFragments = new ArrayList<>();
                PartyCenterRecommendedBean bean = (PartyCenterRecommendedBean) data;
                types = bean.getTypes();
                types.add(0, new PartyCenterRecommendedBean.TypesBean("", ArmsUtils.getString(getContext(), R.string.all)));
                //name
                List<String> titleNameList = new ArrayList<>();
                //flag
                List<String> infoTypeIdList = new ArrayList<>();
                for (PartyCenterRecommendedBean.TypesBean typesBean : types) {
                    titleNameList.add(typesBean.getName());
                    infoTypeIdList.add(typesBean.getId());
                }
                String[] strings1 = titleNameList.toArray(new String[titleNameList.size()]);
                for (String infoTypeId : infoTypeIdList) {
                    mFragments.add(PartContentFragment.newInstance(infoTypeId, getArguments().getInt(TYPE)));
                }
                mAdapter = new MyPagerAdapter(getChildFragmentManager(), mFragments, strings1);
                vp.setAdapter(mAdapter);
                tl2.setViewPager(vp, strings1);
                vp.setOffscreenPageLimit(strings1.length);
            }
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
            if (types != null && types.size() > 0) {
                if (bbcChoicePopup == null) {
                    bbcChoicePopup = new BbcChoicePopup(getActivity(), types);
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
        mPresenter.getPartyMainData(false, false, getArguments().getInt(TYPE));
    }

    @Override
    public BaseFragment getFragment() {
        return this;
    }
}
