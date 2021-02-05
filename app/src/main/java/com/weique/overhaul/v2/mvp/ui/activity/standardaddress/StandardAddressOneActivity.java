package com.weique.overhaul.v2.mvp.ui.activity.standardaddress;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AccessControlUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerStandardAddressOneComponent;
import com.weique.overhaul.v2.mvp.contract.StandardAddressOneContract;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.StandardAddressOnePresenter;
import com.weique.overhaul.v2.mvp.ui.activity.map.MapActivity;
import com.weique.overhaul.v2.mvp.ui.adapter.StandardAddressOneAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * todo 此界面   已经废弃  新界面StandardAddressOneNewActivity(测试通过后  删除相关界面)
 * 添加 街道 巷 楼宇  建筑物 门牌  标准地址
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Deprecated
//@Route(path = RouterHub.APP_STANDARDADDRESSONEACTIVITY)
public class StandardAddressOneActivity extends BaseActivity<StandardAddressOnePresenter> implements StandardAddressOneContract.View {


    @Inject
    AppManager mAppManger;
    /**
     * 上级id
     */
    public static final String DEPARTMENT_BEAN = "DEPARTMENT_BEAN";
    public static final String DEPARTMENT_UP_BEAN = "DEPARTMENT_UP_BEAN";
    public static final String GRID_ID = "GridId";
    /**
     * 网格上 用户点击 进入的网格 is
     */
    @Autowired(name = GRID_ID)
    String gridId;
    /**
     * 上个界面点击的item的 信息
     */
    @Autowired(name = DEPARTMENT_BEAN)
    StandardAddressStairBean.StandardAddressStairBaseBean departmentBean;
    /**
     * 上级名称
     */
    @Autowired(name = DEPARTMENT_UP_BEAN)
    StandardAddressStairBean.StandardAddressStairBaseBean departmentUpBean;

    @BindView(R.id.rank_name)
    TextView rankName;
    @BindView(R.id.area_name)
    TextView areaName;
    @BindView(R.id.rank_address_code)
    TextView rankAddressCode;
    @BindView(R.id.area_code)
    TextView areaCode;
    @BindView(R.id.code_myself)
    TextView codeMyself;
    @BindView(R.id.tier_code)
    TextView tierCode;
    @BindView(R.id.tier_name)
    TextView tierName;
    @BindView(R.id.sub_info)
    TextView subInfo;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.add)
    FloatingActionButton add;
    @BindView(R.id.search_view)
    MaterialSearchView materialSearchView;
    private StandardAddressOneAdapter adapter;

    private int deletePosition = -1;

    List<String> strings = new ArrayList<>();

    private String keyWord = "";
    private CountDownTimer timer;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStandardAddressOneComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_standard_address_one;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            setTitle(departmentBean.getName());
            initSearch();
            appbar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    try {
                        if (verticalOffset >= 0) {
                            swipeRefreshLayout.setEnabled(true); //当滑动到顶部的时候开启
                        } else {
                            swipeRefreshLayout.setEnabled(false); //否则关闭
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            initRecycler();
            AccessControlUtil.controlByLevelCommunity(add);
            assert mPresenter != null;
            mPresenter.getGriddingFourRace(true, false, departmentBean.getId(), gridId, keyWord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化  搜索相关问题
     */
    private void initSearch() {
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
                assert mPresenter != null;
                if (!newText.equals(keyWord)) {
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
                                mPresenter.getGriddingFourRace(true, false, departmentBean.getId(), gridId, newText);
                            }
                        }.start();
                    }
                }
                return false;
            }
        });
    }

    private void initRecycler() {
        try {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                assert mPresenter != null;
                mPresenter.getGriddingFourRace(true, false, departmentBean.getId(), gridId, keyWord);
            });
            adapter = new StandardAddressOneAdapter(R.layout.standard_address_one_item, null);
            adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                    .colorResId(R.color.theme_background)
                    .sizeResId(R.dimen.dp_10)
                    .build());
            recyclerView.setAdapter(adapter);
            recyclerView.setClipToPadding(false);
            adapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    if (adapter.getItem(position) instanceof StandardAddressStairBean.StandardAddressStairBaseBean) {
                        StandardAddressStairBean.StandardAddressStairBaseBean bean =
                                (StandardAddressStairBean.StandardAddressStairBaseBean) adapter.getItem(position);
                        if (view.getId() == R.id.all_item) {
                            if (StandardAddressActivity.isCrud()) {
                                ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSLOOKACTIVITY)
                                        .withString(StandardAddressLookActivity.DETAIL_ID, bean.getId())
                                        .withString(GRID_ID, gridId)
                                        .navigation();
                                deletePosition = position;
                            } else {
                                mAppManger.killActivity(StandardAddressActivity.class);
                                finish();
                                EventBus.getDefault().post(
                                        new EventBusBean(EventBusConstant.UPDATE_UP_ADDRESS, "", bean)
                                        , StandardAddressActivity.source);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            adapter.setOnLoadMoreListener(() -> {
                mPresenter.getGriddingFourRace(false, true, departmentBean.getId(), gridId, keyWord);
            }, recyclerView);
            adapter.setEmptyView(R.layout.null_content_layout, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 更新 item 信息 删除  修改和新增
     *
     * @param eventBusBean eventBusBean
     */
//    @Subscriber(tag = RouterHub.APP_STANDARDADDRESSONEACTIVITY)
    private void onEventCallBack(EventBusBean eventBusBean) {
        switch (eventBusBean.getCode()) {
            case EventBusConstant.ADD:
                if (eventBusBean.getData() instanceof StandardAddressStairBean.StandardAddressStairBaseBean) {
                    StandardAddressStairBean.StandardAddressStairBaseBean bean = (StandardAddressStairBean.StandardAddressStairBaseBean) eventBusBean.getData();
                    if (adapter.getData().size() <= 0) {
                        ArrayList<StandardAddressStairBean.StandardAddressStairBaseBean> beans = new ArrayList<>();
                        beans.add(bean);
                        adapter.setNewData(beans);
                    } else {
                        adapter.addData(0, bean);
                    }
                }
                break;
            case EventBusConstant.ALERT:
                if (eventBusBean.getData() instanceof StandardAddressStairBean.StandardAddressStairBaseBean && deletePosition >= 0) {
                    StandardAddressStairBean.StandardAddressStairBaseBean bean = (StandardAddressStairBean.StandardAddressStairBaseBean) eventBusBean.getData();
                    adapter.setData(deletePosition, bean);
                }
                break;
            case EventBusConstant.DELETE:
                if (deletePosition >= 0) {
                    adapter.remove(deletePosition);
                }
                break;
            default:
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
    public void killMyself() {
        finish();
    }

    /**
     * 设置新参数
     *
     * @param standardAddress StandardAddressBean
     */
    @Override
    public void setNewData(StandardAddressStairBean standardAddress, boolean isLoadMore) {
        if (isLoadMore) {
            adapter.addData(new ArrayList<>(standardAddress.getStandardAddress()));
        } else {
            adapter.setNewData(new ArrayList<>(standardAddress.getStandardAddress()));
            standardAddress.setCurrentRank(departmentBean.getLevel());
            setSuperiorInfo(departmentBean.getName());
        }
    }

    /**
     * 移除要删除的item
     */
    @Override
    public void removeItem() {
        if (deletePosition >= 0) {
            adapter.remove(deletePosition);
        }
    }

    /**
     * 上级信息
     *
     * @param rank rank
     */
    private void setSuperiorInfo(String rank) {
        try {
            rankName.setText(String.format(ArmsUtils.getString(this, R.string.standard_address_t_s), rank));
            rankAddressCode.setText(String.format(ArmsUtils.getString(this, R.string.standard_address_c_s), rank));
            areaName.setText(departmentBean.getName());
            areaCode.setText(departmentBean.getAddressCode());
            codeMyself.setText(departmentBean.getCode());
            tierCode.setText(String.valueOf(departmentBean.getLevel()));
            tierName.setText(rank);
            subInfo.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.add, R.id.right_btn})
    public void onClick(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.add:
                    ARouter.getInstance().build(RouterHub.APP_STANDARDADDRESSADDALERTACTIVITY)
                            .withInt(StandardAddressAddAlertActivity.STATE, EventBusConstant.ADD)
                            .withString(Constant.DEPARTMENT_ID, departmentUpBean.getId())
                            .withString(StandardAddressUpListActivity.ADDRESSTYPE_ID, departmentBean.getId())
                            .withString(MapActivity.POINTS_IN_JSON, departmentUpBean.getPointsInJSON())
                            .navigation();
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
