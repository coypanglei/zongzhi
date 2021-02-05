package com.weique.overhaul.v2.mvp.ui.activity.epidemic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.di.component.DaggerStaffTemperatureComponent;
import com.weique.overhaul.v2.mvp.contract.StaffTemperatureContract;
import com.weique.overhaul.v2.mvp.model.entity.StaffListBean;
import com.weique.overhaul.v2.mvp.model.entity.StaffTemperatureBean;
import com.weique.overhaul.v2.mvp.presenter.StaffTemperaturePresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.StaffTemperatureAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.StaffTemperatureHistoryAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_STAFFTEMPERATUREACTIVITY)
public class StaffTemperatureActivity extends BaseActivity<StaffTemperaturePresenter> implements StaffTemperatureContract.View {
    @Inject
    Gson gson;
    @Inject
    StaffTemperatureAdapter mAdapter;
    @Inject
    StaffTemperatureHistoryAdapter mAdapterHistory;
    @Inject
    HorizontalDividerItemDecoration decoration;
    @BindView(R.id.select_years)
    TextView selectYears;
    @BindView(R.id.reset_time)
    TextView resetTime;
    @BindView(R.id.select_time_layout)
    LinearLayout selectTimeLayout;
    @BindView(R.id.toolbar_submit)
    RelativeLayout toolbarSubmit;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.sr)
    SwipeRefreshLayout sr;
    private String yearMonth;
    private TimePickerView pvTime;

    @Autowired(name = ARouerConstant.SOURCE)
    String source;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStaffTemperatureComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_staff_temperature;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        try {
            ImmersionBar.with(this).statusBarColor(R.color.white).init();
            sr.setEnabled(false);
            ARouter.getInstance().inject(this);
            if (RouterHub.APP_STAFFLISTACTIVITY.equals(source)) {
                setTitle("历史体温");
                selectTimeLayout.setVisibility(View.VISIBLE);
                toolbarSubmit.setVisibility(View.GONE);
                initTimePicker();
                initAdapterHistory();
            } else {
                setTitle("上报体温");
                selectTimeLayout.setVisibility(View.GONE);
                toolbarSubmit.setVisibility(View.VISIBLE);
                initAdapter();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 温度历史adapter
     */
    private void initAdapterHistory() {
        try {
            sr.setOnRefreshListener(() -> {
                getCurrentYearMouthInfo();
            });
            ArmsUtils.configRecyclerView(rv, new LinearLayoutManager(this));
            rv.setAdapter(mAdapterHistory);
            rv.addItemDecoration(decoration);
            mAdapterHistory.setEmptyView(R.layout.null_content_layout, rv);
            getCurrentYearMouthInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前时间信息
     */
    private void getCurrentYearMouthInfo() {
        try {
            Calendar date = Calendar.getInstance();
            String year = String.valueOf(date.get(Calendar.YEAR));
            String month = String.valueOf(date.get(Calendar.MONTH) + 1);
            String day = String.valueOf(date.get(Calendar.DAY_OF_MONTH));
            yearMonth = year + "-" + month + "-" + day;
            selectYears.setText(yearMonth);
            mPresenter.GetReportEmpRecordHistory(yearMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {
        try {
            pvTime = PickerViewUtil.selectPickerTime(this, Constant.YMD, (date, v) -> {
                try {
                    SimpleDateFormat format = new SimpleDateFormat(Constant.YMD, Locale.CHINA);
                    selectYears.setText(format.format(date));
                    yearMonth = format.format(date);
                    if (!TextUtils.isEmpty(yearMonth)) {
                        assert mPresenter != null;
                        mPresenter.GetReportEmpRecordHistory(yearMonth);
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
     * 输入温度 adapter
     */
    private void initAdapter() {
        try {
            sr.setOnRefreshListener(() -> {
                mPresenter.getStaffTemperatureList();
            });
            ArmsUtils.configRecyclerView(rv, new LinearLayoutManager(this));
            rv.setAdapter(mAdapter);
            rv.addItemDecoration(decoration);
            mAdapter.setEmptyView(R.layout.null_content_layout, rv);
            mPresenter.getStaffTemperatureList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading() {
        sr.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        sr.setRefreshing(false);
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

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setNewData(List<StaffListBean.ListBean> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void setNewHistoryData(List<StaffTemperatureBean> staffTemperatureBeans) {
        mAdapterHistory.setNewData(staffTemperatureBeans);
    }

    @OnClick({R.id.select_years, R.id.reset_time, R.id.toolbar_submit})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.select_years:
                    pvTime.show();
                    break;
                case R.id.reset_time:
                    getCurrentYearMouthInfo();
                    break;
                case R.id.toolbar_submit:
                    List<StaffListBean.ListBean> data = mAdapter.getData();
                    for (StaffListBean.ListBean listBean : data) {
                        listBean.setIgnore(false);
                    }
                    int pos = 0;
                    Map<String, Object> map = new HashMap<>();
                    List<StaffTemperatureBean> mapList = new ArrayList<>();
                    submitList(data, pos, map, mapList);
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交
     */
    private void submitList(List<StaffListBean.ListBean> data, int pos, Map<String, Object> map, List<StaffTemperatureBean> mapList) {
        try {
            StaffTemperatureBean map1;
            for (StaffListBean.ListBean listBean : data) {
                map1 = new StaffTemperatureBean();
                pos++;
                if (listBean.getAMTemperature() <= 0 && listBean.getPMTemperature() <= 0) {
                    StaffTemperatureBean finalMap = map1;
                    int finalPos = pos;
                    if (!listBean.isIgnore()) {
                        new CommonDialog.Builder(this)
                                .setContent("员工[" + (listBean.getName()) + "]体温没有填写,是否继续提交")
                                .setPositiveButton("继续提交", (v, commonDialog) -> {
                                    finalMap.setEmployeeInfoRegisterId(listBean.getId());
                                    finalMap.setAMTemperature(listBean.getAMTemperature());
                                    finalMap.setPMTemperature(listBean.getPMTemperature());
                                    mapList.add(finalMap);
                                    listBean.setIgnore(true);
                                    submitList(data, finalPos, map, mapList);
                                }).setNegativeButton("去填写", (v, commonDialog) -> {
                            rv.scrollToPosition(finalPos);
                        }).create().show();
                        return;
                    }
                } else {
                    if (!listBean.isIgnore()) {
                        listBean.setIgnore(true);
                        map1.setEmployeeInfoRegisterId(listBean.getId());
                        map1.setAMTemperature(listBean.getAMTemperature());
                        map1.setPMTemperature(listBean.getPMTemperature());
                        mapList.add(map1);
                    }
                }
            }
            map.put("reportEmps", mapList);
            mPresenter.createReportEmpRecord(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
