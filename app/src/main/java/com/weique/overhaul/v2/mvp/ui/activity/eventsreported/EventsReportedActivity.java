package com.weique.overhaul.v2.mvp.ui.activity.eventsreported;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.ViewAnimationUtil;
import com.weique.overhaul.v2.di.component.DaggerEventsReportedComponent;
import com.weique.overhaul.v2.mvp.contract.EventsReportedContract;
import com.weique.overhaul.v2.mvp.model.entity.CommonEnumBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedSortBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EventsReportedPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.eventreport.EventsReportedFFragment;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonScreenPopup;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 事件上报 已上报列表
 * <p>
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Route(path = RouterHub.APP_EVENTSREPORTEDACTIVITY, name = "事件列表")
public class EventsReportedActivity extends BaseActivity<EventsReportedPresenter> implements EventsReportedContract.View {


    @BindView(R.id.status)
    LinearLayout status;
    @BindView(R.id.screen)
    LinearLayout screen;
    @BindView(R.id.right_btn)
    LinearLayout rightBtn;
    @BindView(R.id.sort_text)
    TextView sortText;
    @BindView(R.id.status_text)
    TextView statusText;

    @BindView(R.id.status_arrow)
    ImageView statusArrow;


    @BindView(R.id.search_view)
    MaterialSearchView materialSearchView;


    @BindView(R.id.tl_2)
    SlidingTabLayout tl2;
    @BindView(R.id.vp)
    ViewPager vp;
    private MyPagerAdapter mAdapter;
    private ArrayList<EventsReportedFFragment> mFragments;

    private CommonScreenPopup popup;
    private CountDownTimer timer;


    private String keyword;
    private int orderStatusCheckPos = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEventsReportedComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_events_reported;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            initSearch();
            initViewPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViewPage() {
        try {
            mFragments = new ArrayList<>();
            mFragments.add(EventsReportedFFragment.newInstance(EventsReportedFFragment.MY));
            mFragments.add(EventsReportedFFragment.newInstance(EventsReportedFFragment.ACCEPT));
            mFragments.add(EventsReportedFFragment.newInstance(EventsReportedFFragment.HANDLE));
            mFragments.add(EventsReportedFFragment.newInstance(EventsReportedFFragment.INSPECT));
            mFragments.add(EventsReportedFFragment.newInstance(EventsReportedFFragment.SYNERGY));
            String[] array = new String[]{
                    "我的上报", "待受理", "待处置", "待核查", "待协同"};
            mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, array);
            vp.setAdapter(mAdapter);
            tl2.setViewPager(vp, array);
            vp.setOffscreenPageLimit(array.length);
            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        status.setVisibility(View.VISIBLE);
                    } else {
                        status.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
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


    /**
     * 初始化  搜索相关问题
     */
    private void initSearch() {
        try {
            materialSearchView.setVoiceSearch(false);
            materialSearchView.setEllipsize(true);
            materialSearchView.setHint(getString(R.string.input_search_content));
            materialSearchView.setSubmitOnClick(false);
            materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    for (EventsReportedFFragment mFragment : mFragments) {
                        mFragment.setKeyword(query);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.equals(keyword)) {
                        if (timer != null) {
                            timer.cancel();
                        }
                        timer = new CountDownTimer(1000, 1000) {
                            @SuppressLint("StringFormatMatches")
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                keyword = newText;
                                for (EventsReportedFFragment mFragment : mFragments) {
                                    mFragment.setKeyword(keyword);
                                }
                            }
                        }.start();
                    }
                    return false;
                }
            });

            materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
                @Override
                public void onSearchViewShown() {
                    //Do some magic
                    Timber.i("222");
                }

                @Override
                public void onSearchViewClosed() {
                    materialSearchView.dismissSuggestions();
                    //Do some magic
                }
            });
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
        finish();
    }

    @OnClick({R.id.sort, R.id.status, R.id.right_btn})
    public void onClick(View v) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (v.getId()) {
                case R.id.sort:
                    ARouter.getInstance()
                            .build(RouterHub.APP_EVENTSREPORTEDSORTACTIVITY)
                            .withString(ARouerConstant.SOURCE, RouterHub.APP_EVENTSREPORTEDACTIVITY)
                            .navigation();
                    break;
                case R.id.status:
                    ViewAnimationUtil.rotateAnimation(statusArrow, true);
                    if (popup == null) {
                        popup = new CommonScreenPopup(this);
                        popup.showPopupWindow(screen);
                        popup.setListItemClickListener(new CommonScreenPopup.ListItemClickListener() {
                            @Override
                            public void onItemClick(int orderStatus, String name, int pos) {
                                try {
                                    orderStatusCheckPos = pos;
                                    statusText.setText(getString(R.string.status) + "(" + name + ")");
                                    for (EventsReportedFFragment mFragment : mFragments) {
                                        mFragment.setOrderStatus(orderStatus);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        popup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                ViewAnimationUtil.rotateAnimation(statusArrow, false);
                            }
                        });
                    } else {
                        popup.showPopupWindow(screen);
                    }
                    List<CommonEnumBean> beans = new ArrayList<>();
                    beans.add(new CommonEnumBean(ArmsUtils.getString(this, R.string.all), -2));
                    for (int i = 0; i < EventsReportedBean.ListBean.EVENTS_REPORTED_ENUM_TEXT.length; i++) {
                        beans.add(new CommonEnumBean(EventsReportedBean.ListBean.EVENTS_REPORTED_ENUM_TEXT[i], i - 1));
                    }
                    popup.setBeans(beans, orderStatusCheckPos);
                    popup.showPopupWindow(screen);
                    break;
                case R.id.right_btn:
                    if (!materialSearchView.isSearchOpen()) {
                        materialSearchView.showSearch();
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscriber(tag = RouterHub.APP_EVENTSREPORTEDACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        //事件分类  回调 更新 recycler view
        try {
            switch (eventBusBean.getCode()) {
                case EventBusConstant.UPDATE_UP_EVENT_SORT:
                    if (eventBusBean.getData() instanceof EventsReportedSortBean.ListBean) {
                        EventsReportedSortBean.ListBean bean =
                                (EventsReportedSortBean.ListBean) eventBusBean.getData();
                        if (bean == null) {
                            ArmsUtils.makeText("获取事件分类信息失败");
                            return;
                        }
                        sortText.setText(getString(R.string.sort) + "(" + bean.getName() + ")");
                        for (EventsReportedFFragment mFragment : mFragments) {
                            mFragment.setOrderSortType(bean.getId());
                        }
                    }
                    break;
                case EventBusConstant.ADD:
                case EventBusConstant.ALERT:
                case EventBusConstant.SELECT:
                    for (EventsReportedFFragment mFragment : mFragments) {
                        mFragment.setKeyword(keyword);
                    }
                    break;
                case EventBusConstant.DELETE:
                    mFragments.get(0).deletePos();
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.clearFocus();
            materialSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
