package com.weique.overhaul.v2.mvp.ui.activity.standardaddress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.VerticalSwipeRefreshLayout;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.di.component.DaggerStandardAddressComponent;
import com.weique.overhaul.v2.mvp.contract.StandardAddressContract;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.presenter.StandardAddressPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.StandardAddressAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 信息采集--标准地址
 *
 * @author GK
 */
@Deprecated
//@Route(path = RouterHub.APP_STANDARDADDRESSACTIVITY)
public class StandardAddressActivity extends BaseActivity<StandardAddressPresenter> implements StandardAddressContract.View {
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.bar_one)
    TextView barOne;
    @BindView(R.id.bar_two)
    TextView barTwo;
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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    VerticalSwipeRefreshLayout swipeRefreshLayout;
    private StandardAddressAdapter adapter;


    /**
     * 判断从那个界面跳转过来的
     */
    @Autowired(name = ARouerConstant.SOURCE)
    public static String source;

    private static boolean isCRUD;

    /**
     * 当前等级(省 市 区/县 (镇) 街道 社区 网格) 对应的id
     */
    public static final String CURRENT_LEVEL_ID = "CURRENT_LEVEL_ID";
    @Autowired(name = CURRENT_LEVEL_ID)
    String id;
    private StandardAddressStairBean.StandardAddressStairBaseBean departmentUpBean;

    /**
     * @return 获取 用户行为 true 是 来选择地址的 false 是来CRUD的
     */
    public static boolean isCrud() {
        return isCRUD;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStandardAddressComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_standard_address;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ARouter.getInstance().inject(this);
            if (StringUtil.isNotNullString(source)) {
                isCRUD = source.equals(RouterHub.APP_INFORMATIONCOLLECTIONACTIVITY);
            }
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
            mPresenter.getUpDownRankInfo(false, false, id);
        } catch (Exception e) {
            e.printStackTrace();
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
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    private void initRecycler() {
        try {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                mPresenter.getUpDownRankInfo(true, false, id);
            });
            adapter = new StandardAddressAdapter(R.layout.standard_address_item, null);
            adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
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
                        if (view.getId() == R.id.input) {
                            if (bean != null) {
                                //> 0  就是网格以上的层级
                                if (departmentUpBean.getLevel() > 0) {
                                    ARouter.getInstance()
                                            .build(RouterHub.APP_STANDARDADDRESSONENEWACTIVITY)
                                            .withString(CURRENT_LEVEL_ID, bean.getId())
                                            .withString(ARouerConstant.SOURCE, source)
                                            .navigation();
                                } else {
                                    //点击网格级别列表 跳转下一个界面
                                    ARouter.getInstance()
                                            .build(RouterHub.APP_STANDARDADDRESSONENEWACTIVITY)
                                            .withParcelable(StandardAddressOneActivity.DEPARTMENT_BEAN, bean)
                                            .withParcelable(StandardAddressOneActivity.DEPARTMENT_UP_BEAN, departmentUpBean)
                                            .withString(StandardAddressOneActivity.GRID_ID, id)
                                            .navigation();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            adapter.setOnLoadMoreListener(() -> {
                mPresenter.getUpDownRankInfo(false, true, id);
            }, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置数据
     *
     * @param standardAddressStairBean standardAddressStairBean
     */
    @Override
    public void newData(StandardAddressStairBean standardAddressStairBean, boolean isLoadMore) {
        try {
            if (standardAddressStairBean != null) {
                if (standardAddressStairBean.getDepartmentUp() != null) {
                    appbar.setVisibility(View.VISIBLE);
                    departmentUpBean = standardAddressStairBean.getDepartmentUp();
                    //获取 层级名称
                    standardAddressStairBean.setCurrentRank(departmentUpBean.getLevel());
                } else {
                    appbar.setVisibility(View.GONE);
                }
                //上级层级 是网格 取值 getAddressType   否则取值 getDepartment
                List<StandardAddressStairBean.StandardAddressStairBaseBean> beans;
                if (departmentUpBean.getLevel() == StandardAddressStairBean.GRIDDING) {
                    beans = new ArrayList<>(standardAddressStairBean.getAddressType());
                } else {
                    beans = new ArrayList<>(standardAddressStairBean.getDepartment());
                }
                if (isLoadMore) {
                    adapter.addData(beans);
                } else {
                    adapter.setNewData(beans);
                    setSuperiorInfo(standardAddressStairBean.getCurrentRankNameWithUp());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSuperiorInfo(String rank) {
        try {
            rankName.setText(String.format(ArmsUtils.getString(this, R.string.standard_address_t_s), rank));
            rankAddressCode.setText(String.format(ArmsUtils.getString(this, R.string.standard_address_c_s), rank));
            areaName.setText(departmentUpBean.getName());
            areaCode.setText(departmentUpBean.getAddressCode());
            codeMyself.setText(departmentUpBean.getCode());
            tierCode.setText(String.valueOf(departmentUpBean.getLevel()));
            tierName.setText(rank);
            subInfo.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
