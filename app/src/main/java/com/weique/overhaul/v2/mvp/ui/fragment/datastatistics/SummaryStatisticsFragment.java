package com.weique.overhaul.v2.mvp.ui.fragment.datastatistics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.ocr.ui.util.DimensionUtil;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.customview.linetu.CustomXAxisRendererHorizontalBarChart;
import com.weique.overhaul.v2.app.customview.linetu.XYMarkerView;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.app.utils.TimeUtil;
import com.weique.overhaul.v2.di.component.DaggerSummaryStatisticsComponent;
import com.weique.overhaul.v2.mvp.contract.SummaryStatisticsContract;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;
import com.weique.overhaul.v2.mvp.model.entity.IncidentReportingBean;
import com.weique.overhaul.v2.mvp.model.entity.StatisticalReviewLineBean;
import com.weique.overhaul.v2.mvp.presenter.SummaryStatisticsPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationDataStatisticsAdapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindAnim;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2020 09:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SummaryStatisticsFragment extends BaseFragment<SummaryStatisticsPresenter> implements SummaryStatisticsContract.View {


    @BindView(R.id.chart)
    LineChart attendanceChart;

    @BindColor(R.color.StatisticsBlueColor)
    int blue;

    @BindColor(R.color.StatisticsGreenColor)
    int green;

    @BindColor(R.color.StatisticsColumnColor)
    int columnColor;

    @BindDrawable(R.drawable.fade_blue)
    Drawable fadeBlue;

    @BindDrawable(R.drawable.fade_green)
    Drawable fadeGreen;

    @BindColor(R.color.gray_eee)
    int grayEee;

    @BindColor(R.color.white)
    int white;
    @Inject
    Typeface mTf;
    @Inject
    XYMarkerView xyMarkerView;
    @BindView(R.id.start_data)
    TextView startData;
    @BindView(R.id.end_data)
    TextView endData;
    @BindView(R.id.arrow_icon)
    ImageView arrowIcon;
    @BindView(R.id.contradiction_adjustment_chart)
    LineChart contradictionAdjustmentChart;


    /**
     * //走访柱状图
     */
    @BindView(R.id.grid_patrol)
    HorizontalBarChart gridPatrol;
    @BindView(R.id.arrow_icon_visit)
    ImageView arrowIconVisit;
    @BindView(R.id.arrow_icon_network_inspection)
    ImageView arrowIconNetworkInspection;
    /**
     * 网络巡检
     */
    @BindView(R.id.network_inspection_chart)
    HorizontalBarChart networkInspectionChart;
    /**
     * 上报事件
     */
    @BindView(R.id.up_tell_intent_chart)
    HorizontalBarChart upTellIntentChart;
    @BindView(R.id.arrow_icon_up_tell_intent)
    ImageView arrowIconUpTellIntent;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    @BindView(R.id.recycler_label)
    RecyclerView recyclerLabel;
    @BindView(R.id.arrow_icon_info_type)
    ImageView arrowIconInfoType;


    private Typeface mHorizontalTf;
    /**
     * 折线数据
     */

    private ArrayList<Entry> values2 = new ArrayList<>();

    /**
     * 折线数据
     */
    private ArrayList<Entry> CaValues = new ArrayList<>();
    /**
     * //走访数据
     */
    private List<GridDataBean> mVistList = new ArrayList<>();

    /**
     * //巡检数据
     */
    private List<GridDataBean> mNetworkInspectionList = new ArrayList<>();

    /**
     * //事件上报数据
     */
    private List<GridDataBean> mIncidentReportingList = new ArrayList<>();


    private TimePickerView startTimePickerView;


    private TimePickerView endTimePickerView;


    @Inject
    GridLayoutManager gridLayoutManager;
    @Inject
    InformationDataStatisticsAdapter mgAdapter;
    @BindAnim(R.anim.view_rotate)
    Animation rotate;

    @BindAnim(R.anim.view_rotate_reversal)
    Animation rotateReversal;

    public static SummaryStatisticsFragment newInstance() {
        return new SummaryStatisticsFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSummaryStatisticsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary_statistics, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initLineChart();
        initTime();
        initTimePicker();
        initPieChart();
        initRecyclerAdapter();
        setHorizontalChart(gridPatrol);
        setHorizontalChartTwo(networkInspectionChart);
        setHorizontalChartThree(upTellIntentChart);


    }

    /**
     * 初始化 信息类型
     */
    private void initRecyclerAdapter() {
        try {
            mgAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            mgAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    GridDataBean informarionTypeBean = (GridDataBean) adapter.getItem(position);

                    String start = startData.getText().toString().trim();
                    String end = endData.getText().toString().trim();
//
//                    if (0 == start.compareTo(end)) {
//                        ArmsUtils.makeText("至少选择时间间隔要大于一天");
//                        return;
//                    }

                    if (ArmsUtils.isEmpty(informarionTypeBean)) {
                        return;
                    }
                    ARouter.getInstance().build(RouterHub.APP_STATISTICS_DETAIL_ACTIVITY)
                            .withString(ARouerConstant.LEVEL, "2")
                            .withString(ARouerConstant.ID, informarionTypeBean.getId())
                            .withString(ARouerConstant.START_DATA, start)
                            .withString(ARouerConstant.END_DATA, end)
                            .withString(ARouerConstant.TYPE, "1")
                            .withString(ARouerConstant.SOURCE, "information_acquisition")
                            .withString(ARouerConstant.TITLE, informarionTypeBean.getName())
                            .navigation();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });
            ArmsUtils.configRecyclerView(recyclerLabel, gridLayoutManager);
            recyclerLabel.setClipToPadding(false);
            recyclerLabel.setAdapter(mgAdapter);
            mgAdapter.setEmptyView(R.layout.null_content_layout, recyclerLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
          返回界面时
         */
        String start = startData.getText().toString().trim();
        String end = endData.getText().toString().trim();

        if (0 == start.compareTo(end)) {
            return;
        }
        mPresenter.getList(start, end);
    }

    /**
     * 初始化时间选择器
     */
    private void initTimePicker() {

        startTimePickerView = PickerViewUtil.selectPickerTimeByToday(getActivity(), Constant.YMD, (date, v) -> {
            try {
                //开始时间
                SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                startData.setText(format.format(date));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        endTimePickerView = PickerViewUtil.selectPickerTimeToday(getActivity(), Constant.YMD, (date, v) -> {
            try {
                //结束时间
                SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
                endData.setText(format.format(date));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    /**
     * 初始化数据折线默认显示
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    private void generateDataLine(String startDate, String endDate) {
        values2.clear();
        List<String> list = TimeUtil.getDays(startDate, endDate);
        for (int i = 0; i < list.size(); i++) {
            //数字
            values2.add(new Entry(i, 0, list.get(i)));
        }

        LineDataSet d2 = new LineDataSet(values2, "打卡");
        d2.setHighLightColor(blue);
        d2.setLineWidth(2f);
        d2.setCircleRadius(3f);
        d2.setColor(green);
        d2.setCircleColor(green);
        d2.setDrawValues(false);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d2);

        attendanceChart.setData(new LineData(sets));

        //折线图动画
        attendanceChart.animateX(750);
    }

    /**
     * 初始化考勤折线图
     */

    @Override
    public void initLineChart() {

        try {
            showLoading();
            attendanceChart.getDescription().setEnabled(false);
            attendanceChart.setDrawGridBackground(false);
            attendanceChart.setMarker(xyMarkerView);
            attendanceChart.setScaleEnabled(false);
            XAxis xAxis = attendanceChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTypeface(mTf);
            xAxis.setDrawGridLines(false);
            xAxis.setAxisLineWidth(1.5f);
            xAxis.setAxisLineColor(blue);
            xAxis.setDrawAxisLine(true);
            xAxis.setGranularity(1f);
            xAxis.setLabelCount(7, false);
            /*
             *格式化标签
             */
            xAxis.setValueFormatter(new ValueFormatter() {

                @Override
                public String getFormattedValue(float value) {

                    try {
                        return values2.get((int) value).getData() + "";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            });

            YAxis rightAxis = attendanceChart.getAxisRight();
            rightAxis.setEnabled(false);
            YAxis leftAxis = attendanceChart.getAxisLeft();
            leftAxis.setTypeface(mTf);
            leftAxis.setAxisLineWidth(1f);
            leftAxis.setAxisLineColor(blue);
            leftAxis.setLabelCount(5, false);

            //最小为0
            leftAxis.setAxisMinimum(0f);
            //1的倍数
            leftAxis.setGranularity(1f);
            //保证Y轴从0开始，不然会上移一点
            leftAxis.setAxisMinimum(0f);
            //网格线
            leftAxis.setDrawGridLines(true);
            leftAxis.enableGridDashedLine(5f, 5f, 1f);
            leftAxis.setGridColor(grayEee);
            /*
             * 去掉标签
             */
            attendanceChart.getLegend().setEnabled(false);
            //   Legend legend = attendanceChart.getLegend();
//            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//            legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//            legend.setDrawInside(false);
            setLineChart(contradictionAdjustmentChart);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 矛盾调节折线图
     *
     * @param attendanceChart 折线图View
     */
    private void setLineChart(LineChart attendanceChart) {
        //当数据为空的时候提醒用户

        attendanceChart.getDescription().setEnabled(false);
        attendanceChart.setDrawGridBackground(false);
        attendanceChart.setMarker(new XYMarkerView(getActivity(), "总量"));
        attendanceChart.setScaleEnabled(false);
        // 没有数据时不显示
        attendanceChart.setNoDataText("Loading...");
        attendanceChart.setNoDataTextColor(Color.TRANSPARENT);
        attendanceChart.invalidate();
        XAxis xAxis = attendanceChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7, false);
//格式化标签
        xAxis.setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {

                try {
                    return CaValues.get((int) value).getData() + "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
        });

        YAxis rightAxis = attendanceChart.getAxisRight();
        rightAxis.setEnabled(false);
        YAxis leftAxis = attendanceChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setAxisLineWidth(1f);
        leftAxis.setAxisLineColor(blue);
        leftAxis.setLabelCount(5, false);
        //网格线
        leftAxis.setDrawGridLines(true);
        leftAxis.enableGridDashedLine(5f, 5f, 1f);
        leftAxis.setGridColor(grayEee);

        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineWidth(1.5f);
        xAxis.setAxisLineColor(blue);

        //最小为0
        leftAxis.setAxisMinimum(0f);
        //1的倍数
        leftAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        attendanceChart.getLegend().setEnabled(false);

    }

    /**
     * 设置走访柱状图
     */
    private void setHorizontalChart(HorizontalBarChart gridPatrol) {
        try {

            gridPatrol.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {

                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        int i = (int) e.getX();
                        Timber.e(mVistList.get(i).getName());

                        if (mVistList.get(i).getCount() == 0) {
                            return;
                        }
                        String start = startData.getText().toString().trim();
                        String end = endData.getText().toString().trim();

//                        if (0 == start.compareTo(end)) {
//                            ArmsUtils.makeText("至少选择时间间隔要大于一天");
//                            return;
//                        }
                        ARouter.getInstance().build(RouterHub.APP_STATISTICS_DETAIL_ACTIVITY)
                                .withString(ARouerConstant.LEVEL, "2")
                                .withString(ARouerConstant.ID, mVistList.get(i).getId())
                                .withString(ARouerConstant.START_DATA, start)
                                .withString(ARouerConstant.END_DATA, end)
                                .withString(ARouerConstant.TYPE, "2")
                                .navigation();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected() {

                }
            });

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
     * 设置巡检柱状图
     *
     * @param gridPatrol 柱状图View
     */
    private void setHorizontalChartTwo(HorizontalBarChart gridPatrol) {
        gridPatrol.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    int i = (int) e.getX();
                    Timber.e(mNetworkInspectionList.get(i).getName());

                    if (mNetworkInspectionList.get(i).getCount() == 0) {
                        return;
                    }
                    String start = startData.getText().toString().trim();
                    String end = endData.getText().toString().trim();

//                    if (0 == start.compareTo(end)) {
//                        ArmsUtils.makeText("至少选择时间间隔要大于一天");
//                        return;
//                    }
                    ARouter.getInstance().build(RouterHub.APP_STATISTICS_DETAIL_ACTIVITY)
                            .withString(ARouerConstant.LEVEL, "2")
                            .withString(ARouerConstant.ID, mNetworkInspectionList.get(i).getId())
                            .withString(ARouerConstant.START_DATA, start)
                            .withString(ARouerConstant.END_DATA, end)
                            .withString(ARouerConstant.TYPE, "3")
                            .navigation();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
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


    /**
     * 设置事件上报柱状图
     *
     * @param gridPatrol 柱状图View
     */
    private void setHorizontalChartThree(HorizontalBarChart gridPatrol) {
        gridPatrol.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    int i = (int) e.getX();
                    Timber.e(mIncidentReportingList.get(i).getName());

                    if (mIncidentReportingList.get(i).getCount() == 0) {
                        return;
                    }
                    String start = startData.getText().toString().trim();
                    String end = endData.getText().toString().trim();

//                    if (0 == start.compareTo(end)) {
//                        ArmsUtils.makeText("至少选择时间间隔要大于一天");
//                        return;
//                    }
                    ARouter.getInstance().build(RouterHub.APP_STATISTICS_DETAIL_ACTIVITY)
                            .withString(ARouerConstant.LEVEL, "2")
                            .withString(ARouerConstant.ID, mIncidentReportingList.get(i).getId())
                            .withString(ARouerConstant.START_DATA, start)
                            .withString(ARouerConstant.END_DATA, end)
                            .withString(ARouerConstant.TYPE, "4")
                            .navigation();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        //图标 横坐标数据
        gridPatrol.getXAxis().setValueFormatter(new ValueFormatter() {

            @Override
            public String getFormattedValue(float value) {
                try {
                    int i = (int) value;
                    String name;
                    if (mIncidentReportingList.get(i).getName().length() > 6) {
                        name = mIncidentReportingList.get(i).getName().substring(0, 6) + "...  ";
                    } else {
                        name = mIncidentReportingList.get(i).getName() + "  ";
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
        yl.setTypeface(mHorizontalTf);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(false);
        yl.setAxisMinimum(0f);
        yl.setGranularity(1f);

        YAxis yr = gridPatrol.getAxisRight();
        yr.setTypeface(mHorizontalTf);
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
        xl.setTypeface(mHorizontalTf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGranularity(1f);

        // 没有数据时不显示
        gridPatrol.setNoDataText("Loading...");
        gridPatrol.setNoDataTextColor(Color.TRANSPARENT);
        gridPatrol.invalidate();
    }

    /**
     * 初始化默认显示 30天时间
     */
    @Override
    public void initTime() {

        try {
            //获取当前时间
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat(Constant.YMD1, Locale.CHINA);
            Date date = calendar.getTime();
            endData.setText(format.format(date));
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day - 30);
            startData.setText(format.format(calendar.getTime()));

            //折线图获取数据
            String start = startData.getText().toString().trim();
            String end = endData.getText().toString().trim();

            assert mPresenter != null;
            mPresenter.GetSignIn(start, end);
            //数据采集
            mPresenter.getList(start, end);
            //初始化折线数据
            generateDataLine(start, end);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 饼状图
     */
    @Override
    public void initPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        //事件上报总数
        pieChart.setCenterText("总数");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.animateY(1400, Easing.EaseInOutQuad);


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);
        // 没有数据时不显示
        pieChart.setNoDataText("Loading...");
        pieChart.setNoDataTextColor(Color.TRANSPARENT);
        pieChart.invalidate();
    }

    /**
     * 信息类型
     * 传递数据给 界面显示
     *
     * @param beans beans
     */
    @Override
    public void setData(List<GridDataBean> beans) {
        try {
            mgAdapter.setNewData(beans);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLineData(StatisticalReviewLineBean lineData) {
        try {
            hideLoading();
            //时间数据
            values2.clear();
            for (int i = 0; i < lineData.getDate().size(); i++) {
                //数字
                values2.add(new Entry(i, lineData.getCounts().get(i), lineData.getDate().get(i)));
            }

            LineDataSet d2 = new LineDataSet(values2, "打卡");
            d2.setHighLightColor(blue);
            d2.setLineWidth(2f);
            d2.setCircleRadius(3f);
            d2.setColor(green);
            d2.setCircleColor(green);
            d2.setDrawValues(false);
            d2.setDrawFilled(true);
            d2.setFillDrawable(fadeGreen);
            ArrayList<ILineDataSet> sets = new ArrayList<>();
            sets.add(d2);
            attendanceChart.setData(new LineData(sets));

            //折线图动画
            attendanceChart.animateX(750);
            attendanceChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 网络巡检
     * 设置横柱表格数据
     *
     * @param gridPatrol 柱状图View
     * @param list       数据源
     */
    private void setHorizontalData(HorizontalBarChart gridPatrol, List<GridDataBean> list) {

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
            if (para1.height > 9000) {
                para1.height = 9000;
            }
            gridPatrol.setLayoutParams(para1);

            ArrayList<BarEntry> values = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                values.add(new BarEntry(i * 1f, list.get(i).getCount()
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
            xl.setTypeface(mHorizontalTf);
            xl.setDrawAxisLine(true);
            xl.setDrawGridLines(false);
            xl.setGranularity(1f);
            xl.setLabelCount(list.size(), false);
            gridPatrol.setXAxisRenderer(new CustomXAxisRendererHorizontalBarChart(gridPatrol.getViewPortHandler(), xl, gridPatrol.getTransformer(YAxis.AxisDependency.LEFT), gridPatrol, values.size()));
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mHorizontalTf);
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
     * 设置饼状图
     */
    private void setPiechartData(int reportCount, int selfCount) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        entries.add(new PieEntry((float) reportCount, "   上报   " + reportCount));
        entries.add(new PieEntry((float) selfCount, "   自处理   " + selfCount));
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(green);
        colors.add(blue);


        PieDataSet dataSet = new PieDataSet(entries, "");
        PieData data = new PieData(dataSet);
        dataSet.setColors(colors);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(7f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        // undo all highlights
        pieChart.setCenterText((reportCount + selfCount) + "");
        pieChart.highlightValues(null);
        pieChart.setDrawEntryLabels(false);
        pieChart.invalidate();
        pieChart.animateXY(1400, 1400);
    }


    /**
     * 矛盾调节折线图
     *
     * @param lineData 折线图view
     */
    @Override
    public void setContradictionAdjustmentLineData(StatisticalReviewLineBean lineData) {
        try {
            hideLoading();
            //时间数据
            CaValues.clear();
            for (int i = 0; i < lineData.getDate().size(); i++) {
                //数字
                CaValues.add(new Entry(i, lineData.getCounts().get(i), lineData.getDate().get(i)));
            }

            LineDataSet d2 = new LineDataSet(CaValues, "总计");
            d2.setHighLightColor(blue);
            d2.setLineWidth(2f);
            d2.setCircleRadius(3f);
            d2.setColor(blue);
            d2.setCircleColor(blue);
            d2.setDrawValues(false);
            d2.setDrawFilled(true);
            d2.setFillDrawable(fadeBlue);
            ArrayList<ILineDataSet> sets = new ArrayList<>();
            sets.add(d2);
            contradictionAdjustmentChart.setData(new LineData(sets));

            //折线图动画
            contradictionAdjustmentChart.animateX(750);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取走访记录统计
     *
     * @param vistList 走访集合
     */
    @Override
    public void setVisitList(List<GridDataBean> vistList) {
        mVistList = vistList;
        setHorizontalData(gridPatrol, mVistList);
    }


    @Override
    public void setPatrolInspectionList(List<GridDataBean> patrolInspectionList) {

        try {
            mNetworkInspectionList = patrolInspectionList;
            setHorizontalData(networkInspectionChart, mNetworkInspectionList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getIncidentReportingList(IncidentReportingBean incidentReportingBean) {

        try {
            mIncidentReportingList = incidentReportingBean.getEvents();
            //上报
            int reportCount = incidentReportingBean.getReportCount();
            //自处理
            int selfCount = incidentReportingBean.getSelfCount();
            setPiechartData(reportCount, selfCount);
            setHorizontalData(upTellIntentChart, mIncidentReportingList);
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
    public void setData(@Nullable Object data) {

    }

    @Override
    public Context getContext() {
        return getActivity();
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

    }

    @OnClick({R.id.start_data, R.id.end_data, R.id.search, R.id.ll_contradiction_adjustment, R.id.ll_visit
            , R.id.ll_network_inspection, R.id.ll_up_tell_intent, R.id.ll_info_type})
    public void onViewClicked(View view) {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            String start = startData.getText().toString().trim();
            String end = endData.getText().toString().trim();
            if (ArmsUtils.isEmpty(mPresenter)) {
                return;
            }

            rotate.setInterpolator(new LinearInterpolator());
            rotate.setFillAfter(true);

            rotateReversal.setInterpolator(new LinearInterpolator());
            rotateReversal.setFillAfter(true);
            switch (view.getId()) {
                //起始时间
                case R.id.start_data:
                    if (!startTimePickerView.isShowing()) {
                        startTimePickerView.show();
                    }
                    break;
                //结束时间
                case R.id.end_data:
                    if (!endTimePickerView.isShowing()) {
                        endTimePickerView.show();
                    }
                    break;
                case R.id.search:
                    //折线图获取数据  签到记录

                    mPresenter.GetSignIn(start, end);

                    mPresenter.getVisitList(start, end);

                    mPresenter.GetContradictionAdjustment(start, end);

                    mPresenter.getPatrolnspectionList(start, end);

                    mPresenter.getIncidentReportingList(start, end);

                    mPresenter.getList(start, end);
                    break;
                case R.id.ll_contradiction_adjustment:
                    //矛盾调节折线图
                    if (contradictionAdjustmentChart.getVisibility() == View.VISIBLE) {
                        contradictionAdjustmentChart.setVisibility(View.GONE);
                        arrowIcon.startAnimation(rotateReversal);
                    } else {
                        contradictionAdjustmentChart.setVisibility(View.VISIBLE);
                        mPresenter.GetContradictionAdjustment(start, end);
                        arrowIcon.startAnimation(rotate);
                    }
                    break;
                case R.id.ll_visit:
                    //网络走访
                    if (gridPatrol.getVisibility() == View.VISIBLE) {
                        gridPatrol.setVisibility(View.GONE);
                        arrowIconVisit.startAnimation(rotateReversal);
                        gridPatrol.animateX(1000);
                    } else {
                        mPresenter.getVisitList(start, end);
                        arrowIconVisit.startAnimation(rotate);
                        gridPatrol.setVisibility(View.VISIBLE);
                    }
                    break;
                //网络巡检
                case R.id.ll_network_inspection:
                    if (networkInspectionChart.getVisibility() == View.VISIBLE) {
                        networkInspectionChart.setVisibility(View.GONE);
                        arrowIconNetworkInspection.startAnimation(rotateReversal);
                        networkInspectionChart.animateX(1000);
                    } else {
                        mPresenter.getPatrolnspectionList(start, end);
                        arrowIconNetworkInspection.startAnimation(rotate);
                        networkInspectionChart.setVisibility(View.VISIBLE);
                    }
                    break;
                //网络上报
                case R.id.ll_up_tell_intent:
                    if (upTellIntentChart.getVisibility() == View.VISIBLE) {
                        pieChart.setVisibility(View.GONE);
                        upTellIntentChart.setVisibility(View.GONE);
                        arrowIconUpTellIntent.startAnimation(rotateReversal);
                        upTellIntentChart.animateX(1000);
                    } else {
                        mPresenter.getIncidentReportingList(start, end);
                        arrowIconUpTellIntent.startAnimation(rotate);
                        pieChart.setVisibility(View.VISIBLE);
                        upTellIntentChart.setVisibility(View.VISIBLE);
                    }
                    break;
                //信息采集
                case R.id.ll_info_type:


                    if (recyclerLabel.getVisibility() == View.VISIBLE) {
                        recyclerLabel.setVisibility(View.GONE);
                        upTellIntentChart.animateX(1000);
                        arrowIconInfoType.startAnimation(rotateReversal);
                    } else {

                        recyclerLabel.setVisibility(View.VISIBLE);
                        arrowIconInfoType.startAnimation(rotate);
                    }

                    break;
                default:
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
