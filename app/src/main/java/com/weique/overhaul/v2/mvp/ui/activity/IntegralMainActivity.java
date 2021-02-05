package com.weique.overhaul.v2.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.di.component.DaggerIntegralMainComponent;
import com.weique.overhaul.v2.mvp.contract.IntegralMainContract;
import com.weique.overhaul.v2.mvp.model.entity.IntergralDetailsBean;
import com.weique.overhaul.v2.mvp.presenter.IntegralMainPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.IntegralAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/19/2020 11:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 *
 * @author Administrator
 */
@Route(path = RouterHub.APP_INTEGRAL_ACTIVITY, name = "积分界面")
public class IntegralMainActivity extends BaseActivity<IntegralMainPresenter> implements IntegralMainContract.View {


    /**
     * 上个界面跳转的积分
     */
    @Autowired(name = ARouerConstant.ID)
    String id;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.right_btn_text)
    TextView rightBtnText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    LinearLayoutManager layoutManager;
    @Inject
    HorizontalDividerItemDecoration itemDecoration;
    @Inject
    IntegralAdapter mAdapter;
    @BindView(R.id.select_data)
    TextView selectData;

    private TimePickerView startTimePickerView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIntegralMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_integral_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        setTitle("考核积分");
        initTimePicker();
        mPresenter.getIntegralDetails(true, false, time);
        rightBtnText.setText("积分规则");
        initRecycler();
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
    public void getIntegralDetails(IntergralDetailsBean integral, boolean isLoadMore) {
        try {
            tvIntegral.setText(integral.getCurrentMonthSumScore() + "");
            if (isLoadMore) {
                mAdapter.addData(integral.getList());
            } else {
                mAdapter.setNewData(integral.getList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }


    /**
     * 初始化 recycler
     */
    private void initRecycler() {
        try {

            swipeRefreshLayout.setOnRefreshListener(() -> {
                try {
                    mPresenter.getIntegralDetails(true, false, time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    try {
                        swipeRefreshLayout.setEnabled(recyclerView.getChildCount() == 0 || recyclerView.getChildAt(0).getTop() >= 0);
                        super.onScrollStateChanged(recyclerView, newState);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            ArmsUtils.configRecyclerView(recyclerView, layoutManager);
            recyclerView.setClipToPadding(false);
            recyclerView.setAdapter(mAdapter);
            recyclerView.addItemDecoration(itemDecoration);
            mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
            mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mAdapter.setOnLoadMoreListener(() -> {
                try {
                    mPresenter.getIntegralDetails(false, true, time);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, recyclerView);
        } catch (
                Exception e) {
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
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
        }
    }


    @OnClick({R.id.right_btn, R.id.select_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_btn:
                ARouter.getInstance()
                        .build(RouterHub.APP_INTEGRAL_RULE_ACTIVITY)
                        .navigation();
                break;
            case R.id.select_data:
                if (!startTimePickerView.isShowing()) {
                    startTimePickerView.show();
                }
                break;
            default:
                break;
        }
    }

    private String time = "";

    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {

        startTimePickerView = PickerViewUtil.selectPickerTimeToday(this, Constant.YM1, (date, v) -> {
            try {
                //开始时间
                SimpleDateFormat format = new SimpleDateFormat(Constant.YM1, Locale.CHINA);
                selectData.setText(format.format(date));
                time = format.format(date);
                mPresenter.getIntegralDetails(true, false, time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Calendar selectedDate = Calendar.getInstance();
        //开始时间
        SimpleDateFormat format = new SimpleDateFormat(Constant.YM1, Locale.CHINA);
        selectData.setText("请选择时间");
    }

}
