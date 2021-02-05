package com.weique.overhaul.v2.mvp.ui.activity.economicmanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.ocr.ui.util.DimensionUtil;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.linetu.CustomXAxisRendererHorizontalBarChart;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.di.component.DaggerEconomicManagementMainComponent;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementMainContract;
import com.weique.overhaul.v2.mvp.model.entity.CommentBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.EconomicManageMianBean;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;
import com.weique.overhaul.v2.mvp.model.entity.LocationAndName;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;
import com.weique.overhaul.v2.mvp.presenter.EconomicManagementMainPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.EconomicManagementTitleAdapter;
import com.weique.overhaul.v2.mvp.ui.popupwindow.NewAddPopupWindow;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.simple.eventbus.Subscriber;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * 经济管理首页
 * ================================================
 *
 * @author GreatKing
 */
@Route(path = RouterHub.APP_ECONOMIC_MANAGEMENT, name = "经济管理首页")
public class EconomicManagementMainActivity extends BaseActivity<EconomicManagementMainPresenter> implements EconomicManagementMainContract.View {


    @BindString(R.string.economic_management)
    String title;
    List<CommonTitleBean> beans = new ArrayList<>();
    NewAddPopupWindow orderSortPopup;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rv_title)
    RecyclerView rv_title;

    @BindColor(R.color.gray_eee)
    int grayEee;

    @Inject
    GridLayoutManager gridLayoutManager;

    @Inject
    EconomicManagementTitleAdapter mAdapter;

    @Inject
    HorizontalDividerItemDecoration decoration;
    @BindView(R.id.grid_patrol)
    HorizontalBarChart gridPatrol;

    @BindColor(R.color.StatisticsColumnColor)
    int columnColor;
    @BindView(R.id.hoirizontalBC)
    HorizontalBarChart hoirizontalBC;
    /**
     * //行业数据
     */
    private List<CommentBean> mVistList = new ArrayList<>();


    /**
     * //企业数据
     */
    private List<CommentBean> mNetworkInspectionList = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEconomicManagementMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_economic_management_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        setTitle(title);
        beans.add(new CommonTitleBean(0+"", "企业信息采集"));
        beans.add(new CommonTitleBean(1+"", "项目信息采集"));
        orderSortPopup = new NewAddPopupWindow(this, (orderStatus, name) -> {
            orderSortPopup.dismiss();
            if ("0".equals(orderStatus)) {

                ARouter.getInstance().build(RouterHub.APP_ENTERPRISE_INFORMATION_COLLECTION).
                        withString(ARouerConstant.TITLE, "企业信息采集").
                        navigation();

            } else {
                ARouter.getInstance().build(RouterHub.APP_PROJECT_COLLECTION)
                        .withString(ARouerConstant.TITLE, "项目信息采集")
                        .navigation();
            }
        });


        initRecyclerAdapter();

        setHorizontalChart(gridPatrol);
        setHorizontalChartTwo(hoirizontalBC);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAllData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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


    @OnClick(R.id.iv_add)
    public void onViewClicked() {

        orderSortPopup.setBeans(beans);
        orderSortPopup.showPopupWindow(ivAdd);

    }


    /**
     * 初始化 信息类型
     */
    private void initRecyclerAdapter() {
        try {

            ArmsUtils.configRecyclerView(rv_title, gridLayoutManager);
            rv_title.addItemDecoration(decoration);
            rv_title.setClipToPadding(false);
            rv_title.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setAllData(EconomicManageMianBean datas) {
        try {
            mAdapter.setNewData(datas.getSummary().getAllList());
            mVistList = datas.getStatistic().getMyChart1();
            mNetworkInspectionList = datas.getStatistic().getMyChart2();
            setHorizontalData(gridPatrol, mVistList);
            setHorizontalData(hoirizontalBC, mNetworkInspectionList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置走访柱状图
     */
    private void setHorizontalChart(HorizontalBarChart gridPatrol) {
        try {


            XAxis xl = gridPatrol.getXAxis();


            xl.setValueFormatter(new ValueFormatter() {

                @Override
                public String getFormattedValue(float value) {

                    try {

                        int i = (int) value;
                        String name;
                        if (mVistList.get(i).getName().length() > 6) {
                            name = mVistList.get(i).getName().substring(0, 6) + "...  ";
                        } else {
                            name = mVistList.get(i).getName() + "  ";
                        }
                        return name;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            });

            initChart(gridPatrol);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化柱状图
     */
    private void initChart(HorizontalBarChart gridPatrol) {

        gridPatrol.setDrawBarShadow(false);

        gridPatrol.setDrawValueAboveBar(true);
        gridPatrol.setTouchEnabled(true);
        gridPatrol.setDragEnabled(false);
        gridPatrol.setScaleYEnabled(false);

        gridPatrol.setScaleXEnabled(false);
        gridPatrol.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        gridPatrol.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        gridPatrol.setPinchZoom(false);


        gridPatrol.setDrawGridBackground(false);


        YAxis yl = gridPatrol.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisMinimum(0f);
        yl.setGranularity(1f);

        YAxis yr = gridPatrol.getAxisRight();
        yr.setDrawAxisLine(true);
        //网格线
        yr.setDrawGridLines(true);
        yr.enableGridDashedLine(5f, 5f, 0f);
        yr.setGridColor(grayEee);
        yr.setAxisMinimum(0f);
        yr.setGranularity(1f);
        Legend l = gridPatrol.getLegend();
        l.setEnabled(false);

        XAxis xl = gridPatrol.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGranularity(1f);

        // 没有数据时不显示
        gridPatrol.setNoDataText("Loading...");
        gridPatrol.setNoDataTextColor(Color.TRANSPARENT);
        gridPatrol.invalidate();
    }


    /**
     * 网络巡检
     * 设置横柱表格数据
     *
     * @param gridPatrol 柱状图View
     * @param list       数据源
     */
    private void setHorizontalData(HorizontalBarChart gridPatrol, List<CommentBean> list) {

        try {


            int listSize = list.size();
            ViewGroup.LayoutParams para1;
            para1 = gridPatrol.getLayoutParams();
            if (listSize > 6) {
                para1.height = DimensionUtil.dpToPx(35 * listSize);
            } else if (listSize == 1) {
                para1.height = DimensionUtil.dpToPx(100 * listSize);
            } else {
                para1.height = DimensionUtil.dpToPx(60 * listSize);
            }
            gridPatrol.setLayoutParams(para1);

            ArrayList<BarEntry> values = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                values.add(new BarEntry(i * 1f, list.get(i).getValue()
                ));
            }

            BarDataSet set1;


            set1 = new BarDataSet(values, null);
            set1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    String str = "";
                    try {
                        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
                        str = decimalFormat.format(value);

                        return str;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return str;
                }
            });
            set1.setDrawIcons(false);
            set1.setColor(columnColor);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            XAxis xl = gridPatrol.getXAxis();
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setDrawAxisLine(true);
            xl.setDrawGridLines(false);
            xl.setGranularity(1f);
            xl.setLabelCount(list.size(), false);
            gridPatrol.setXAxisRenderer(new CustomXAxisRendererHorizontalBarChart(gridPatrol.getViewPortHandler(), xl, gridPatrol.getTransformer(YAxis.AxisDependency.LEFT), gridPatrol, values.size()));
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.6f);
            data.setDrawValues(true);
            gridPatrol.setData(data);
            gridPatrol.animateX(1000);
            gridPatrol.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置巡检柱状图
     *
     * @param gridPatrol 柱状图View
     */
    private void setHorizontalChartTwo(HorizontalBarChart gridPatrol) {

        gridPatrol.getXAxis().setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {

                try {
                    int i = (int) value;
                    String name;
                    if (mNetworkInspectionList.get(i).getName().length() > 6) {
                        name = mNetworkInspectionList.get(i).getName().substring(0, 6) + "...  ";
                    } else {
                        name = mNetworkInspectionList.get(i).getName() + "  ";
                    }
                    return name;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
        });

        initChart(gridPatrol);

    }


    @OnClick({R.id.enterprise_btn, R.id.project_management_btn})
    public void onViewClicked(View view) {

        if (AppUtils.isFastClick()) {
            return;
        }
        switch (view.getId()) {
              /*
               企业管理
               */
            case R.id.enterprise_btn:
                ARouter.getInstance().build(RouterHub.APP_PROJECT_COLLECTION_SEARCH)
                        .withString(ARouerConstant.TITLE, getString(R.string.enterprise_management))
                        .withInt(ARouerConstant.TYPE, SearchEconoicManageMentActivity.IS_ENTERPRISE)
                        .navigation();
                break;
            case R.id.project_management_btn:
                ARouter.getInstance().build(RouterHub.APP_PROJECT_COLLECTION_SEARCH)
                        .withString(ARouerConstant.TITLE, getString(R.string.project_management))
                        .withInt(ARouerConstant.TYPE, SearchEconoicManageMentActivity.IS_PROJECT)
                        .navigation();
                break;
            default:
                break;
        }
    }
}