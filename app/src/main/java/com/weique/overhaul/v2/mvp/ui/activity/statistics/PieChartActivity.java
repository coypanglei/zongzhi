package com.weique.overhaul.v2.mvp.ui.activity.statistics;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.PickerViewUtil;
import com.weique.overhaul.v2.di.component.DaggerPieChartComponent;
import com.weique.overhaul.v2.mvp.contract.PieChartContract;
import com.weique.overhaul.v2.mvp.model.entity.PieChartBean;
import com.weique.overhaul.v2.mvp.presenter.PieChartPresenter;
import com.weique.overhaul.v2.mvp.ui.adapter.PieChartListAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.PieChartListAdapter1;
import com.weique.overhaul.v2.mvp.ui.adapter.PieChartListAdapter2;
import com.weique.overhaul.v2.mvp.ui.adapter.PieChartListAdapter3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * @author GreatKing
 * 统计界面
 */

@Route(path = RouterHub.APP_PIECHARTACTIVITY)
public class PieChartActivity extends BaseActivity<PieChartPresenter> implements PieChartContract.View,
        OnChartValueSelectedListener {

    @BindView(R.id.chart1)
    PieChart mPieChart;
    @BindView(R.id.select_years)
    TextView selectYears;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.chart2)
    PieChart mPieChart2;
    @BindView(R.id.recyclerView2)
    RecyclerView recyclerView2;

    @BindView(R.id.chart3)
    PieChart mPieChart3;
    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView3;


    @BindView(R.id.chart4)
    PieChart mPieChart4;
    @BindView(R.id.recyclerView4)
    RecyclerView recyclerView4;

    private TimePickerView timePickerView;
    private String yearMonth;


    private PieChartListAdapter pieChartListAdapter;
    private PieChartListAdapter1 pieChartListAdapter1;
    private PieChartListAdapter2 pieChartListAdapter2;
    private PieChartListAdapter3 pieChartListAdapter3;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPieChartComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_pie_chart;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.YM1, Locale.CHINA);
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        yearMonth = simpleDateFormat.format(date);
        selectYears.setText(yearMonth);
        setTitle("统计");
        initApapter();
        assert mPresenter != null;
        mPresenter.getPieChartData(yearMonth);
        initTimePicker();


    }

    private void initApapter() {
        try {

            pieChartListAdapter = new PieChartListAdapter();
            pieChartListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(this));
            recyclerView.setAdapter(pieChartListAdapter);
            pieChartListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);


            pieChartListAdapter1 = new PieChartListAdapter1();
            pieChartListAdapter1.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView2, new LinearLayoutManager(this));
            recyclerView2.setAdapter(pieChartListAdapter1);
            pieChartListAdapter1.setEmptyView(R.layout.null_content_layout, recyclerView2);

            pieChartListAdapter2 = new PieChartListAdapter2();
            pieChartListAdapter2.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView3, new LinearLayoutManager(this));
            recyclerView3.setAdapter(pieChartListAdapter2);
            pieChartListAdapter2.setEmptyView(R.layout.null_content_layout, recyclerView3);

            pieChartListAdapter3 = new PieChartListAdapter3();
            pieChartListAdapter3.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(recyclerView4, new LinearLayoutManager(this));
            recyclerView4.setAdapter(pieChartListAdapter3);
            pieChartListAdapter3.setEmptyView(R.layout.null_content_layout, recyclerView4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initPie(ArrayList<PieEntry> entries, String count) {
        try {
            mPieChart.setExtraOffsets(12, 5, 12, 5);
            // 设置饼图是否接收点击事件，默认为true
            mPieChart.setTouchEnabled(true);
            //设置饼图是否使用百分比
            mPieChart.setUsePercentValues(false);
            //设置图表转动阻力摩擦系数[0,1]  就是转动的摩擦系数 如果是0表示手指滑动立即停止
            mPieChart.setDragDecelerationFrictionCoef(0.95f);
            //设置饼图右下角的文字描述
            Description des = new Description();
//        des.setText("测试");
            //设置饼图右下角的文字大小
            des.setTextSize(16f);
            mPieChart.setDescription(des);
            //设置pieChart图表的描述是否显示
            mPieChart.getDescription().setEnabled(false);
            //是否显示圆盘中间文字，默认显示
            mPieChart.setDrawCenterText(true);
            //设置圆盘中间文字
            mPieChart.setCenterText("事件数量" + "\n" + count);
            //设置圆盘中间文字的大小
            mPieChart.setCenterTextSize(20);
            //设置圆盘中间文字的颜色
            mPieChart.setCenterTextColor(Color.RED);
            //设置圆盘中间文字的字体
            mPieChart.setCenterTextTypeface(Typeface.MONOSPACE);
            //设置中间透明圈的半径,值为所占饼图的百分比
            //mPieChart.setTransparentCircleRadius(50);

            mPieChart.setTransparentCircleAlpha(110);

            mPieChart.setHoleRadius(58f);
            mPieChart.setTransparentCircleRadius(61f);
            mPieChart.setDrawEntryLabels(true);


            mPieChart.setEntryLabelColor(Color.TRANSPARENT);       //设置pieChart图表文本字体颜色
            mPieChart.setEntryLabelTypeface(Typeface.SANS_SERIF);     //设置pieChart图表文本字体样式
            mPieChart.setEntryLabelTextSize(12f); //设置pieChart图表文本字体大小

            mPieChart.setDrawSliceText(false);
            //是否显示饼图中间空白区域，默认显示
            mPieChart.setDrawHoleEnabled(true);
            //设置圆盘是否转动，默认转动
            mPieChart.setRotationEnabled(true);
            //设置初始旋转角度
            mPieChart.setRotationAngle(0);
            //设置比例图
            Legend l = mPieChart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setWordWrapEnabled(true);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setForm(Legend.LegendForm.CIRCLE);
            l.setYOffset(0f);

            //设置X轴动画
            mPieChart.animateX(1800);

            //显示在比例图上
            PieDataSet dataSet = new PieDataSet(entries, "");
            //设置个饼状图之间的距离
            dataSet.setSliceSpace(3f);
            // 部分区域被选中时多出的长度
            dataSet.setSelectionShift(9f);


            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.COLORFUL_COLORS) {
                colors.add(c);
            }
            for (int c : ColorTemplate.LIBERTY_COLORS) {
                colors.add(c);
            }
            for (int c : ColorTemplate.PASTEL_COLORS) {
                colors.add(c);
            }
            for (int c : ColorTemplate.VORDIPLOM_COLORS) {
                colors.add(c);
            }


            for (int c : ColorTemplate.JOYFUL_COLORS) {
                colors.add(c);
            }


            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);
            dataSet.setValueTextSize(0);

//        //设置提示区域的线开始点距离圆形偏离百分比
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        //再环内部分线的长
//        dataSet.setValueLinePart1Length(0.4f);
//        //再环外部分线的长
//        dataSet.setValueLinePart2Length(0.2f);
//        //提示字体大小
//        dataSet.setValueTextSize(14f);
//        //线的颜色
//        dataSet.setValueLineColor(Color.GRAY);
//        //线的粗细
//        dataSet.setValueLineWidth(2);
//        //Y的值现在饼上还是再饼外
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            PieData data = new PieData(dataSet);


            mPieChart.setData(data);
            // 撤销所有的亮点
            mPieChart.highlightValues(null);

            mPieChart.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initPie1(ArrayList<PieEntry> entries, String count) {
        try {
            mPieChart2.setExtraOffsets(12, 5, 12, 5);
            // 设置饼图是否接收点击事件，默认为true
            mPieChart2.setTouchEnabled(true);
            //设置饼图是否使用百分比
            mPieChart2.setUsePercentValues(false);
            //设置图表转动阻力摩擦系数[0,1]  就是转动的摩擦系数 如果是0表示手指滑动立即停止
            mPieChart2.setDragDecelerationFrictionCoef(0.95f);
            //设置饼图右下角的文字描述
            Description des = new Description();
//        des.setText("测试");
            //设置饼图右下角的文字大小
            des.setTextSize(16f);
            mPieChart2.setDescription(des);
            //设置pieChart图表的描述是否显示
            mPieChart2.getDescription().setEnabled(false);
            //是否显示圆盘中间文字，默认显示
            mPieChart2.setDrawCenterText(true);
            //设置圆盘中间文字
            mPieChart2.setCenterText("资源数量" + "\n" + count);
            //设置圆盘中间文字的大小
            mPieChart2.setCenterTextSize(20);
            //设置圆盘中间文字的颜色
            mPieChart2.setCenterTextColor(Color.RED);
            //设置圆盘中间文字的字体
            mPieChart2.setCenterTextTypeface(Typeface.MONOSPACE);
            //设置中间透明圈的半径,值为所占饼图的百分比
            //mPieChart.setTransparentCircleRadius(50);

            mPieChart2.setTransparentCircleAlpha(110);

            mPieChart2.setHoleRadius(58f);
            mPieChart2.setTransparentCircleRadius(61f);

            mPieChart2.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
            mPieChart2.setEntryLabelTypeface(Typeface.SANS_SERIF);     //设置pieChart图表文本字体样式
            mPieChart2.setEntryLabelTextSize(12f); //设置pieChart图表文本字体大小
            mPieChart2.setDrawSliceText(false);

            //是否显示饼图中间空白区域，默认显示
            mPieChart2.setDrawHoleEnabled(true);
            //设置圆盘是否转动，默认转动
            mPieChart2.setRotationEnabled(true);
            //设置初始旋转角度
            mPieChart2.setRotationAngle(0);
            //设置比例图
            Legend l = mPieChart2.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setWordWrapEnabled(true);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setForm(Legend.LegendForm.CIRCLE);
            l.setYOffset(0f);

            //设置X轴动画
            mPieChart2.animateX(1800);

//
            //显示在比例图上
            PieDataSet dataSet = new PieDataSet(entries, "");
            //设置个饼状图之间的距离
            dataSet.setSliceSpace(3f);
            // 部分区域被选中时多出的长度
            dataSet.setSelectionShift(9f);


            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.PASTEL_COLORS) {
                colors.add(c);
            }
            for (int c : ColorTemplate.LIBERTY_COLORS) {
                colors.add(c);
            }

            for (int c : ColorTemplate.COLORFUL_COLORS) {
                colors.add(c);
            }


            for (int c : ColorTemplate.VORDIPLOM_COLORS) {
                colors.add(c);
            }


            for (int c : ColorTemplate.JOYFUL_COLORS) {
                colors.add(c);
            }


            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);


//        //设置提示区域的线开始点距离圆形偏离百分比
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        //再环内部分线的长
//        dataSet.setValueLinePart1Length(0.4f);
//        //再环外部分线的长
//        dataSet.setValueLinePart2Length(0.2f);
//        //提示字体大小
//        dataSet.setValueTextSize(14f);
//        //线的颜色
//        dataSet.setValueLineColor(Color.GRAY);
//        //线的粗细
//        dataSet.setValueLineWidth(2);
//        //Y的值现在饼上还是再饼外
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            PieData data = new PieData(dataSet);
            dataSet.setValueTextSize(0);


            mPieChart2.setData(data);
            // 撤销所有的亮点
            mPieChart2.highlightValues(null);

            mPieChart2.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initPie2(ArrayList<PieEntry> entries, String count) {
        try {
            mPieChart3.setExtraOffsets(12, 5, 12, 5);
            // 设置饼图是否接收点击事件，默认为true
            mPieChart3.setTouchEnabled(true);
            //设置饼图是否使用百分比
            mPieChart3.setUsePercentValues(false);
            //设置图表转动阻力摩擦系数[0,1]  就是转动的摩擦系数 如果是0表示手指滑动立即停止
            mPieChart3.setDragDecelerationFrictionCoef(0.95f);
            //设置饼图右下角的文字描述
            Description des = new Description();
//        des.setText("测试");
            //设置饼图右下角的文字大小
            des.setTextSize(16f);
            mPieChart3.setDescription(des);
            //设置pieChart图表的描述是否显示
            mPieChart3.getDescription().setEnabled(false);
            //是否显示圆盘中间文字，默认显示
            mPieChart3.setDrawCenterText(true);
            //设置圆盘中间文字
            mPieChart3.setCenterText("巡检数量" + count);
            //设置圆盘中间文字的大小
            mPieChart3.setCenterTextSize(20);
            //设置圆盘中间文字的颜色
            mPieChart3.setCenterTextColor(Color.RED);
            //设置圆盘中间文字的字体
            mPieChart3.setCenterTextTypeface(Typeface.MONOSPACE);
            //设置中间透明圈的半径,值为所占饼图的百分比
            //mPieChart.setTransparentCircleRadius(50);

            mPieChart3.setTransparentCircleAlpha(110);
            mPieChart3.setDrawSliceText(false);

            mPieChart3.setHoleRadius(58f);
            mPieChart3.setTransparentCircleRadius(61f);

            mPieChart3.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
            mPieChart3.setEntryLabelTypeface(Typeface.SANS_SERIF);     //设置pieChart图表文本字体样式
            mPieChart3.setEntryLabelTextSize(12f); //设置pieChart图表文本字体大小

            //是否显示饼图中间空白区域，默认显示
            mPieChart3.setDrawHoleEnabled(true);
            //设置圆盘是否转动，默认转动
            mPieChart3.setRotationEnabled(true);
            //设置初始旋转角度
            mPieChart3.setRotationAngle(0);
            //设置比例图
            Legend l = mPieChart3.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setWordWrapEnabled(true);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setForm(Legend.LegendForm.CIRCLE);
            l.setYOffset(0f);

            //设置X轴动画
            mPieChart3.animateX(1800);

//
            //显示在比例图上
            PieDataSet dataSet = new PieDataSet(entries, "");
            //设置个饼状图之间的距离
            dataSet.setSliceSpace(3f);
            // 部分区域被选中时多出的长度
            dataSet.setSelectionShift(9f);


            ArrayList<Integer> colors = new ArrayList<>();

            for (int c : ColorTemplate.VORDIPLOM_COLORS) {
                colors.add(c);
            }

            for (int c : ColorTemplate.PASTEL_COLORS) {
                colors.add(c);
            }
            for (int c : ColorTemplate.LIBERTY_COLORS) {
                colors.add(c);
            }

            for (int c : ColorTemplate.COLORFUL_COLORS) {
                colors.add(c);
            }


            for (int c : ColorTemplate.JOYFUL_COLORS) {
                colors.add(c);
            }


            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);
            dataSet.setValueTextSize(0);


//        //设置提示区域的线开始点距离圆形偏离百分比
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        //再环内部分线的长
//        dataSet.setValueLinePart1Length(0.4f);
//        //再环外部分线的长
//        dataSet.setValueLinePart2Length(0.2f);
//        //提示字体大小
//        dataSet.setValueTextSize(14f);
//        //线的颜色
//        dataSet.setValueLineColor(Color.GRAY);
//        //线的粗细
//        dataSet.setValueLineWidth(2);
//        //Y的值现在饼上还是再饼外
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            PieData data = new PieData(dataSet);


            mPieChart3.setData(data);
            // 撤销所有的亮点
            mPieChart3.highlightValues(null);

            mPieChart3.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initPie3(ArrayList<PieEntry> entries, String count) {
        try {
            mPieChart4.setExtraOffsets(12, 5, 12, 5);
            // 设置饼图是否接收点击事件，默认为true
            mPieChart4.setTouchEnabled(true);
            //设置饼图是否使用百分比
            mPieChart4.setUsePercentValues(false);
            //设置图表转动阻力摩擦系数[0,1]  就是转动的摩擦系数 如果是0表示手指滑动立即停止
            mPieChart4.setDragDecelerationFrictionCoef(0.95f);
            //设置饼图右下角的文字描述
            Description des = new Description();
//        des.setText("测试");
            //设置饼图右下角的文字大小
            des.setTextSize(16f);
            mPieChart4.setDescription(des);
            //设置pieChart图表的描述是否显示
            mPieChart4.getDescription().setEnabled(false);
            //是否显示圆盘中间文字，默认显示
            mPieChart4.setDrawCenterText(true);
            //设置圆盘中间文字
            mPieChart4.setCenterText("走访数量" + count);
            //设置圆盘中间文字的大小
            mPieChart4.setCenterTextSize(20);
            //设置圆盘中间文字的颜色
            mPieChart4.setCenterTextColor(Color.RED);
            //设置圆盘中间文字的字体
            mPieChart4.setCenterTextTypeface(Typeface.MONOSPACE);
            //设置中间透明圈的半径,值为所占饼图的百分比
            //mPieChart.setTransparentCircleRadius(50);

            mPieChart4.setTransparentCircleAlpha(110);
            mPieChart4.setDrawSliceText(false);

            mPieChart4.setHoleRadius(58f);
            mPieChart4.setTransparentCircleRadius(61f);

            mPieChart4.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
            mPieChart4.setEntryLabelTypeface(Typeface.SANS_SERIF);     //设置pieChart图表文本字体样式
            mPieChart4.setEntryLabelTextSize(12f); //设置pieChart图表文本字体大小

            //是否显示饼图中间空白区域，默认显示
            mPieChart4.setDrawHoleEnabled(true);
            //设置圆盘是否转动，默认转动
            mPieChart4.setRotationEnabled(true);
            //设置初始旋转角度
            mPieChart4.setRotationAngle(0);
            //设置比例图
            Legend l = mPieChart4.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setWordWrapEnabled(true);
            l.setXEntrySpace(7f);
            l.setYEntrySpace(0f);
            l.setForm(Legend.LegendForm.CIRCLE);
            l.setYOffset(0f);

            //设置X轴动画
            mPieChart4.animateX(1800);

//
            //显示在比例图上
            PieDataSet dataSet = new PieDataSet(entries, "");
            //设置个饼状图之间的距离
            dataSet.setSliceSpace(3f);
            // 部分区域被选中时多出的长度
            dataSet.setSelectionShift(9f);


            ArrayList<Integer> colors = new ArrayList<>();
            for (int c : ColorTemplate.COLORFUL_COLORS) {
                colors.add(c);
            }
            for (int c : ColorTemplate.VORDIPLOM_COLORS) {
                colors.add(c);
            }

            for (int c : ColorTemplate.PASTEL_COLORS) {
                colors.add(c);
            }
            for (int c : ColorTemplate.LIBERTY_COLORS) {
                colors.add(c);
            }


            for (int c : ColorTemplate.JOYFUL_COLORS) {
                colors.add(c);
            }


            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);
            dataSet.setValueTextSize(0);


//        //设置提示区域的线开始点距离圆形偏离百分比
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        //再环内部分线的长
//        dataSet.setValueLinePart1Length(0.4f);
//        //再环外部分线的长
//        dataSet.setValueLinePart2Length(0.2f);
//        //提示字体大小
//        dataSet.setValueTextSize(14f);
//        //线的颜色
//        dataSet.setValueLineColor(Color.GRAY);
//        //线的粗细
//        dataSet.setValueLineWidth(2);
//        //Y的值现在饼上还是再饼外
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

            PieData data = new PieData(dataSet);


            mPieChart4.setData(data);
            // 撤销所有的亮点
            mPieChart4.highlightValues(null);

            mPieChart4.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initTimePicker() {
        timePickerView = PickerViewUtil.selectPickerTime(this, Constant.YM, (date, v) -> {
            try {
                SimpleDateFormat format = new SimpleDateFormat(Constant.YM1, Locale.CHINA);
                selectYears.setText(format.format(date));
                yearMonth = format.format(date);
                if (!TextUtils.isEmpty(yearMonth)) {
                    assert mPresenter != null;
                    mPresenter.getPieChartData(yearMonth);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected() {

    }


    @OnClick(R.id.select_years)
    public void onViewClicked() {
        try {
            if (AppUtils.isFastClick()) {
                return;
            }
            if (!timePickerView.isShowing()) {
                timePickerView.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setPieChartData(PieChartBean itemBean) {
        try {
            pieChartListAdapter.setNewData(itemBean.getData2());
            pieChartListAdapter1.setNewData(itemBean.getData());
            pieChartListAdapter2.setNewData(itemBean.getData3());
            pieChartListAdapter3.setNewData(itemBean.getData4());

            ArrayList<PieEntry> entries = new ArrayList<>();
            for (PieChartBean.DataBean datum : itemBean.getData()) {
                if (datum.getSize() > 0) {
                    entries.add(new PieEntry(datum.getSize(), datum.getName()));
                }
            }


            ArrayList<PieEntry> entries1 = new ArrayList<>();
            for (PieChartBean.Data2Bean data2Bean : itemBean.getData2()) {
                if (data2Bean.getSize() > 0) {
                    entries1.add(new PieEntry(data2Bean.getSize(), data2Bean.getName()));
                }
            }

            initPie(entries1, itemBean.getTotalCount2() + "");
            initPie1(entries, itemBean.getTotalCount() + "");

            ArrayList<PieEntry> entries2 = new ArrayList<>();
            for (PieChartBean.Data3Bean data3Bean : itemBean.getData3()) {
                if (data3Bean.getSize() > 0) {
                    entries2.add(new PieEntry(data3Bean.getSize(), data3Bean.getName()));
                }
            }

            initPie2(entries2, itemBean.getTotalCount3() + "");


            ArrayList<PieEntry> entries3 = new ArrayList<>();
            for (PieChartBean.Data4Bean data4Bean : itemBean.getData4()) {
                if (data4Bean.getSize() > 0) {
                    entries3.add(new PieEntry(data4Bean.getSize(), data4Bean.getName()));
                }

            }
            initPie3(entries3, itemBean.getTotalCount4() + "");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SpannableString generateCenterSpannableText(String name, String count) {

        SpannableString s = new SpannableString(name + "\n" + count);
        try {
            s.setSpan(new RelativeSizeSpan(1.1f), 0, 4, 0);
            s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, 4, 0);
            s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 3, s.length(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


}
