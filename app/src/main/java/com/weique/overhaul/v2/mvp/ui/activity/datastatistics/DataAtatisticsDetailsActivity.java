package com.weique.overhaul.v2.mvp.ui.activity.datastatistics;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.di.component.DaggerDataAtatisticsDetailsComponent;
import com.weique.overhaul.v2.mvp.contract.DataAtatisticsDetailsContract;
import com.weique.overhaul.v2.mvp.model.entity.DataReportInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;
import com.weique.overhaul.v2.mvp.presenter.DataAtatisticsDetailsPresenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2020 13:54
 * <p>
 * ================================================
 *
 * @author 43460
 */

@Route(path = RouterHub.APP_STATISTICS_DETAIL_ACTIVITY, name = "柱状图界面")
public class DataAtatisticsDetailsActivity extends BaseActivity<DataAtatisticsDetailsPresenter> implements DataAtatisticsDetailsContract.View {

    @Autowired(name = ARouerConstant.LEVEL)
    String level;

    @Autowired(name = ARouerConstant.ID)
    String id;
    @BindColor(R.color.StatisticsColumnColor)
    int columnColor;

    @Autowired(name = ARouerConstant.START_DATA)
    String startData;

    @Autowired(name = ARouerConstant.END_DATA)
    String endData;


    @Autowired(name = ARouerConstant.TYPE)
    String type;


    @Autowired(name = ARouerConstant.DEPARTMENT_ID)
    String departmentId;


    @Autowired(name = ARouerConstant.TITLE)
    String superiorTitle;

    @Autowired(name = ARouerConstant.SOURCE)
    String informationAcquisition;

    @BindColor(R.color.gray_eee)
    int grayEee;

    @BindView(R.id.horizontal_chart)
    HorizontalBarChart horizontalChart;


    @BindString(R.string.data_statistics)
    String title;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_center)
    TextView tvCenter;
    /**
     * 信息获取 标题 走不同接口
     */
    private static final String INFORMATION_ACQUISITION = "information_acquisition";

    /**
     * 选中的柱子
     */
    int i = 0;

    /**
     * 图表数据
     */
    private List<GridDataBean> mVistList = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDataAtatisticsDetailsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_data_atatistics_details;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //注册路由
        ARouter.getInstance().inject(this);
        setTitle(title);
//
//        Timber.e(startData);
//
//        Timber.e(endData);
//
//        Timber.e(type);
//
//        Timber.e(id);
        assert mPresenter != null;
        if (INFORMATION_ACQUISITION.equals(informationAcquisition)) {

            tvTitle.setText(String.format("信息采集 - %s   统计", superiorTitle));

            mPresenter.getIncidentReportingListByData(startData, endData, type, id, departmentId);
        } else {
            mPresenter.getIncidentReportingList(startData, endData, type, id, departmentId);
        }
        setHorizontalChart(horizontalChart);

    }


    /**
     * 初始化柱状图
     */
    private void setHorizontalChart(HorizontalBarChart gridPatrol) {
        try {
            gridPatrol.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
                    try {
                        i = (int) e.getX();
                        onClickView(i);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected() {
                    /*    Timber.e("未点击坐标点" + i);
                     */

                    onClickView(i);
                }
            });

            gridPatrol.setTouchEnabled(true);
            gridPatrol.setDragEnabled(false);
            gridPatrol.setScaleYEnabled(false);

            gridPatrol.setScaleXEnabled(false);
            gridPatrol.setDrawBarShadow(false);

            gridPatrol.setDrawValueAboveBar(true);

            gridPatrol.getDescription().setEnabled(false);

            // if more than 60 entries are displayed in the chart, no values will be
            // drawn
            gridPatrol.setMaxVisibleValueCount(100);

            // scaling can now only be done on x- and y-axis separately
            gridPatrol.setPinchZoom(false);

            // draw shadows for each bar that show the maximum value
            // chart.setDrawBarShadow(true);

            gridPatrol.setDrawGridBackground(false);

            XAxis xl = gridPatrol.getXAxis();
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setDrawAxisLine(true);
            xl.setDrawGridLines(false);
            xl.setGranularity(1f);


            xl.setValueFormatter(new ValueFormatter() {

                @Override
                public String getFormattedValue(float value) {

                    try {

                        int i = (int) value;

                        String name;
                        if (mVistList.get(i).getName().length() > 6) {
                            name = mVistList.get(i).getName().substring(0, 6) + "...";
                        } else {
                            name = mVistList.get(i).getName();
                        }
                        return name;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            });
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

            // 没有数据时不显示
            gridPatrol.setNoDataText("Loading...");
            gridPatrol.setNoDataTextColor(Color.TRANSPARENT);
            gridPatrol.invalidate();
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
    public void getDataReportInfoList(DataReportInfoBean dataReportInfoBean) {
        hideLoading();

        tvTitle.setText(String.format("信息采集 - %s   统计", dataReportInfoBean.getTypeName()));
        tvCenter.setText(dataReportInfoBean.getDepartmentName());
        try {
            int listSize = dataReportInfoBean.getDepartments().size();

            mVistList = dataReportInfoBean.getDepartments();
            ViewGroup.LayoutParams para1;
            para1 = horizontalChart.getLayoutParams();
            if (listSize > 6) {
                para1.height = DimensionUtil.dpToPx(35 * listSize);
            }else if(listSize==1){
                para1.height = DimensionUtil.dpToPx(100 * listSize);
            }else {
                para1.height = DimensionUtil.dpToPx(60 * listSize);
            }
            horizontalChart.setLayoutParams(para1);

            horizontalChart.setVisibility(View.VISIBLE);
            ArrayList<BarEntry> values = new ArrayList<>();

            for (int i = 0; i < mVistList.size(); i++) {
                values.add(new BarEntry(i * 1f, mVistList.get(i).getCount()));
            }

            BarDataSet set1;
            set1 = new BarDataSet(values, null);
            set1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    String str = null;
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
            set1.setHighLightAlpha(0);

            set1.setDrawIcons(false);
            set1.setColor(columnColor);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            XAxis xl = horizontalChart.getXAxis();
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setDrawAxisLine(true);
            xl.setDrawGridLines(false);
            xl.setGranularity(1f);

            xl.setLabelCount(mVistList.size(), false);
            BarData data = new BarData(dataSets);

            data.setValueTextSize(10f);
            data.setBarWidth(0.6f);
            data.setDrawValues(true);
            horizontalChart.setData(data);
            horizontalChart.animateX(1000);
            horizontalChart.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDataAcquisition(List<GridDataBean> gridDataBeans) {
        hideLoading();
        tvCenter.setVisibility(View.GONE);
        try {
            int listSize = gridDataBeans.size();
            mVistList = gridDataBeans;
            ArrayList<BarEntry> values = new ArrayList<>();
            ViewGroup.LayoutParams para1;
            para1 = horizontalChart.getLayoutParams();
            if (listSize > 6) {
                para1.height = DimensionUtil.dpToPx(35 * listSize);
            }else if(listSize==1){
                para1.height = DimensionUtil.dpToPx(100 * listSize);
            }else {
                para1.height = DimensionUtil.dpToPx(60 * listSize);
            }
            horizontalChart.setLayoutParams(para1);

            horizontalChart.setVisibility(View.VISIBLE);
            for (int i = 0; i < mVistList.size(); i++) {
                values.add(new BarEntry(i * 1f, mVistList.get(i).getCount()
                ));
            }

            BarDataSet set1;
            set1 = new BarDataSet(values, null);
            set1.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {

                    String str = null;
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
            set1.setHighLightAlpha(0);

            set1.setDrawIcons(false);
            set1.setColor(columnColor);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            XAxis xl = horizontalChart.getXAxis();
            xl.setPosition(XAxis.XAxisPosition.BOTTOM);
            xl.setDrawAxisLine(true);
            xl.setDrawGridLines(false);
            xl.setGranularity(1f);

            xl.setLabelCount(mVistList.size(), false);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.6f);
            data.setDrawValues(true);
            horizontalChart.setData(data);
            horizontalChart.animateX(1000);
            horizontalChart.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClickView(int i) {
        try {
//            Timber.e("点击坐标点" + i);
//            Timber.e(mVistList.get(i).getName());
//            Timber.e(id);
            if (mVistList.get(i).getCount() == 0) {
                return;
            }
            if (AppUtils.isFastClick()) {
                return;
            }
            //点击事件
            if (0 != mVistList.get(i).getLevel()) {
                //等于0 继续跳
                //等于1下一级
                if (!INFORMATION_ACQUISITION.equals(informationAcquisition)) {
                    ARouter.getInstance().build(RouterHub.APP_STATISTICS_DETAIL_ACTIVITY)
                            .withString(ARouerConstant.LEVEL, mVistList.get(i).getLevel() + "")
                            //资源id
                            .withString(ARouerConstant.ID, id)
                            //区域id
                            .withString(ARouerConstant.DEPARTMENT_ID, INFORMATION_ACQUISITION.equals(informationAcquisition) ? UserInfoUtil.getUserInfo().getDepartmentId() : mVistList.get(i).getId())
                            .withString(ARouerConstant.START_DATA, startData)
                            .withString(ARouerConstant.END_DATA, endData)
                            .withString(ARouerConstant.TYPE, type)
                            .navigation();
                } else {
                    ARouter.getInstance().build(RouterHub.APP_STATISTICS_DETAIL_ACTIVITY)
                            .withString(ARouerConstant.LEVEL, mVistList.get(i).getLevel() + "")
                            //资源id
                            .withString(ARouerConstant.ID, mVistList.get(i).getId())
                            //区域id
                            .withString(ARouerConstant.DEPARTMENT_ID, UserInfoUtil.getUserInfo().getDepartmentId())
                            .withString(ARouerConstant.START_DATA, startData)
                            .withString(ARouerConstant.END_DATA, endData)
                            .withString(ARouerConstant.TYPE, type)
                            .navigation();
                }
            } else {
                if (INFORMATION_ACQUISITION.equals(informationAcquisition)) {
                    ARouter.getInstance().build(RouterHub.APP_STATISTICS_DETAIL_ACTIVITY)
                            .withString(ARouerConstant.LEVEL, "2")
                            .withString(ARouerConstant.ID, mVistList.get(i).getId())
                            .withString(ARouerConstant.START_DATA, startData)
                            .withString(ARouerConstant.END_DATA, endData)
                            .withString(ARouerConstant.TYPE, "1")
                            .withString(ARouerConstant.SOURCE, INFORMATION_ACQUISITION)
                            .withString(ARouerConstant.TITLE, mVistList.get(i).getName())
                            .navigation();
                } else {
                    ArmsUtils.makeText("没有下一层");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
