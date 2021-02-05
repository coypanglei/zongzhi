package com.weique.overhaul.v2.mvp.ui.activity.matter;

import android.annotation.SuppressLint;
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
import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ViewAnimationUtil;
import com.weique.overhaul.v2.di.component.DaggerMatterListComponent;
import com.weique.overhaul.v2.mvp.contract.MatterListContract;
import com.weique.overhaul.v2.mvp.model.entity.CommonEnumBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterListBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.MatterListPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.MyPagerAdapter;
import com.weique.overhaul.v2.mvp.ui.fragment.matter.MatterListFragment;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonScreenPopup;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.BasePopupWindow;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: MatterList
 * <p>
 * ================================================
 *
 * @author Administrator
 */
@Route(path = RouterHub.APP_MATTERLISTACTIVITY)
public class MatterListActivity extends BaseActivity<MatterListPresenter>
        implements MatterListContract.View {

    @BindView(R.id.sliding_tab_layout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.vp)
    ViewPager vp;

    @BindView(R.id.sort_text)
    TextView sortText;
    @BindView(R.id.status)
    LinearLayout status;
    @BindView(R.id.status_text)
    TextView statusText;
    @BindView(R.id.status_arrow)
    ImageView statusArrow;
    @BindView(R.id.screen)
    LinearLayout screen;

    @BindView(R.id.search_view)
    MaterialSearchView materialSearchView;
    /**
     * 分类
     */
    public static final String TYPE_SORT = "EnumDataSource";
    /**
     * 订单状态
     */
    public static final String TYPE_ORDER_STATE = "EnumOrderStatus";

    private MyPagerAdapter mAdapter;
    private ArrayList<MatterListFragment> mFragments;

    private List<CommonEnumBean> orderStateList;
    private List<CommonEnumBean> sortList;
    private int checkedOrderStatePos = 0;
    private int checkedSortPos = 0;
    private CommonScreenPopup orderSortPopup;
    private String clickType;
    private int defaultState = -2;
    private int defaultSort = -2;
    private String keyWord;
    private CountDownTimer timer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMatterListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_matter_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            setTitle("事项列表");
            initSearch();
            mFragments = new ArrayList<>();
            mFragments.add(MatterListFragment.newInstance(MatterListFragment.SPONSOR));
            mFragments.add(MatterListFragment.newInstance(MatterListFragment.SYNERGY));
            mFragments.add(MatterListFragment.newInstance(MatterListFragment.MY));
            String[] array = new String[]{
                    MatterListFragment.SPONSOR, MatterListFragment.SYNERGY, MatterListFragment.MY};
            mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments, array);
            vp.setAdapter(mAdapter);
            slidingTabLayout.setViewPager(vp, array);
            vp.setOffscreenPageLimit(array.length);
            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0 || position == 1) {
                        status.setVisibility(View.INVISIBLE);
                    } else {
                        status.setVisibility(View.VISIBLE);
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
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.clearFocus();
            materialSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 初始化  搜索相关问题
     */
    private void initSearch() {
        try {
            materialSearchView.setVoiceSearch(false);
            materialSearchView.setEllipsize(true);
            materialSearchView.setHint(getString(R.string.input_search_content));
            materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (!newText.equals(keyWord)) {
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
                                keyWord = newText;
                                for (MatterListFragment mFragment : mFragments) {
                                    mFragment.setKeyword(keyWord);
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
                }

                @Override
                public void onSearchViewClosed() {
                    //Do some magic
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        hideProgressDialog();
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
        finish();
    }

    @Override
    public void setList(List<MatterListBean.ListBean> list, boolean isLoadMore) {
    }

    @OnClick({R.id.sort, R.id.status, R.id.right_btn})
    public void onClick(View v) {
        try {
            assert mPresenter != null;
            switch (v.getId()) {
                case R.id.sort:
                    clickType = TYPE_SORT;
                    if (sortList != null) {
                        showPopup(sortList, checkedSortPos);
                    } else {
                        mPresenter.getEnum(TYPE_SORT);
                    }
                    break;
                case R.id.status:
                    clickType = TYPE_ORDER_STATE;
                    if (orderStateList != null) {
                        showPopup(orderStateList, checkedOrderStatePos);
                    } else {
                        mPresenter.getEnum(TYPE_ORDER_STATE);
                    }
                    break;
                case R.id.right_btn:
                    if (!materialSearchView.isSearchOpen()) {
                        materialSearchView.showSearch();
                    }
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEnum(List<CommonEnumBean> o) {
        o.add(0, new CommonEnumBean("全部", -2));
        switch (clickType) {
            case TYPE_SORT:
                sortList = o;
                break;
            case TYPE_ORDER_STATE:
                orderStateList = o;
                break;
            default:
                break;
        }
        showPopup(o, 0);
    }

    /**
     * @param o   o
     * @param pos pos
     */
    private void showPopup(List<CommonEnumBean> o, int pos) {
        try {
            if (orderSortPopup == null) {
                orderSortPopup = new CommonScreenPopup(this);
                orderSortPopup.setListItemClickListener((orderStatus, name, pos1) -> {
                    try {
                        switch (clickType) {
                            case TYPE_SORT:
                                checkedSortPos = pos1;
                                defaultSort = orderStatus;
                                sortText.setText(getString(R.string.sort) + "(" + name + ")");
                                break;
                            case TYPE_ORDER_STATE:
                                checkedOrderStatePos = pos1;
                                defaultState = orderStatus;
                                statusText.setText(getString(R.string.status) + "(" + name + ")");
                                break;
                            default:
                                break;
                        }
                        popupRefreshList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                orderSortPopup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ViewAnimationUtil.rotateAnimation(statusArrow, false);
                    }
                });
            }
            orderSortPopup.setBeans(o, pos);
            orderSortPopup.showPopupWindow(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * popup 修改 刷新 列表
     */
    private void popupRefreshList() {
        for (MatterListFragment mFragment : mFragments) {
            mFragment.setStateAndSort(defaultState, defaultSort);
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (timer != null) {
                timer.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * 打开通知详情后  文件设置为已读 回调  更新列表
     *
     * @param eventBusBean eventBusBean
     */
    @Subscriber(tag = RouterHub.APP_MATTERLISTACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        try {
            if (EventBusConstant.COMMON_UPDATE == eventBusBean.getCode()) {
                //更新数据
                for (MatterListFragment mFragment : mFragments) {
                    mFragment.setStateAndSort(defaultState, defaultSort);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
