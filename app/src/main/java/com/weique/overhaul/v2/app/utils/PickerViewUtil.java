package com.weique.overhaul.v2.app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;

import java.text.ParseException;
import java.util.Calendar;

/**
 * @author GreatKing
 */
public class PickerViewUtil {
    /**
     * @param activity             context
     * @param precision            精度  在@Constant YM -- YMDHMS 选择
     * @param onTimeSelectListener 回调
     * @return
     */
    public static TimePickerView selectPickerTime(Activity activity, String precision, OnTimeSelectListener onTimeSelectListener) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        int year = selectedDate.get(Calendar.YEAR);
        startDate.set(year - 100, 0, 1);
        endDate.set(year + 100, 11, 31);
        //默认全部展示
        boolean[] precisionT = initPrecision(precision);
        return new TimePickerBuilder(activity, onTimeSelectListener)
                .setType(precisionT)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("时间选择")
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLabel("年", "月", "日", "时", "分", "秒")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //是否显示为对话框样式
                .isDialog(false)
                .build();
    }

    /**
     * 生日选择（年） TimePickerView
     *
     * @param activity             context
     * @param precision            精度  在@Constant YM -- YMDHMS 选择
     * @param onTimeSelectListener 回调
     * @return
     */
    public static TimePickerView selectPickerTimeBirth(Activity activity, String precision,
                                                       OnTimeSelectListener onTimeSelectListener
    ) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day_of_month = selectedDate.get(Calendar.DAY_OF_MONTH);
        startDate.set(year - 100, 0, 1);
        endDate.set(year, month, day_of_month);
        //默认全部展示
        boolean[] precisionT = initPrecision(precision);
        return new TimePickerBuilder(activity, onTimeSelectListener)
                .setType(precisionT)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("时间选择")
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLabel("年", "月", "日", "时", "分", "秒")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //是否显示为对话框样式
                .isDialog(false)
                .build();
    }

    /**
     * 设定起始和结束 和选中 的时间的（年） TimePickerView
     *
     * @param activity             context
     * @param precision            精度  在@Constant YM -- YMDHMS 选择
     * @param onTimeSelectListener 回调
     * @return
     */
    public static TimePickerView selectPickerTimeWithStartEndDialogTrue(Context activity, String precision,
                                                                        Calendar selectedDate, Calendar startDate, Calendar endDate,
                                                                        OnTimeSelectListener onTimeSelectListener
    ) {
        //默认全部展示
        boolean[] precisionT = initPrecision(precision);
        return new TimePickerBuilder(activity, onTimeSelectListener)
                .setType(precisionT)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("时间选择")
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLabel("年", "月", "日", "时", "分", "秒")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //是否显示为对话框样式
                .isDialog(true)
                .build();
    }


    /**
     * 设定起始和结束 和选中 的时间的（年） TimePickerView
     *
     * @param activity             context
     * @param precision            精度  在@Constant YM -- YMDHMS 选择
     * @param onTimeSelectListener 回调
     * @return
     */
    public static TimePickerView selectPickerTimeWithStartEnd(Activity activity, String precision,
                                                              Calendar selectedDate, Calendar startDate, Calendar endDate,
                                                              OnTimeSelectListener onTimeSelectListener
    ) {
        //默认全部展示
        boolean[] precisionT = initPrecision(precision);
        return new TimePickerBuilder(activity, onTimeSelectListener)
                .setType(precisionT)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("时间选择")
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLabel("年", "月", "日", "时", "分", "秒")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //是否显示为对话框样式
                .isDialog(false)
                .build();
    }


    /**
     * 生日选择 dialog显示 TimePickerView
     *
     * @param activity             context
     * @param precision            精度  在@Constant YM -- YMDHMS 选择
     * @param onTimeSelectListener 回调
     * @return
     */
    public static TimePickerView selectPickerTimeBirthIsDialog(Activity activity, String precision,
                                                               OnTimeSelectListener onTimeSelectListener
    ) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day_of_month = selectedDate.get(Calendar.DAY_OF_MONTH);
        startDate.set(year - 100, 0, 1);
        endDate.set(year, month, day_of_month);
        //默认全部展示
        boolean[] precisionT = initPrecision(precision);
        return new TimePickerBuilder(activity, onTimeSelectListener)
                .setType(precisionT)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("时间选择")
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLabel("年", "月", "日", "时", "分", "秒")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //是否显示为对话框样式
                .isDialog(true)
                .build();
    }

    /**
     * 有默认值的时间框
     *
     * @param activity             context
     * @param precision            精度  在@Constant YM -- YMDHMS 选择
     * @param defauleDate          defauleDate
     * @param onTimeSelectListener 回调
     * @return
     */
    public static TimePickerView selectPickerTimeHasDefault(Activity activity, String precision, String defauleDate,
                                                            OnTimeSelectListener onTimeSelectListener) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        int year = selectedDate.get(Calendar.YEAR);
        startDate.set(year - 100, 0, 1);
        endDate.set(year + 100, 11, 31);
        try {
            if (StringUtil.isNotNullString(defauleDate)) {
                selectedDate.setTime(TimeUtil.stringToDateForFotmat(defauleDate, precision));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            selectedDate = Calendar.getInstance();
        }
        //默认全部展示
        boolean[] precisionT = initPrecision(precision);
        return new TimePickerBuilder(activity, onTimeSelectListener)
                .setType(precisionT)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("时间选择")
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLabel("年", "月", "日", "时", "分", "秒")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //是否显示为对话框样式
                .isDialog(false)
                .build();
    }


    /**
     * 结束时间为今天
     * 选择时间为15天前
     *
     * @param activity             context
     * @param precision            精度  在@Constant YM -- YMDHMS 选择
     * @param onTimeSelectListener 回调
     * @return
     */
    public static TimePickerView selectPickerTimeByToday(Activity activity, String precision, OnTimeSelectListener onTimeSelectListener) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        startDate.set(year - 100, 0, 1);
        int day1 = selectedDate.get(Calendar.DATE);
        selectedDate.set(Calendar.DATE, day1 - 30);
        //默认全部展示
        boolean[] precisionT = initPrecision(precision);
        return new TimePickerBuilder(activity, onTimeSelectListener)
                .setType(precisionT)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("时间选择")
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setDate(selectedDate)
                .setRangDate(startDate, Calendar.getInstance())
                .setLabel("年", "月", "日", "时", "分", "秒")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //是否显示为对话框样式
                .isDialog(false)
                .build();
    }


    /**
     * 结束时间为今天
     * 选择时间为今天
     *
     * @param activity             context
     * @param precision            精度  在{@link Constant} YM1 -- YMDHMS 选择
     * @param onTimeSelectListener 回调
     * @return
     */
    public static TimePickerView selectPickerTimeToday(Activity activity, String precision, OnTimeSelectListener onTimeSelectListener) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        int year = selectedDate.get(Calendar.YEAR);
        startDate.set(year - 100, 0, 1);
        //默认全部展示
        boolean[] precisionT = initPrecision(precision);
        return new TimePickerBuilder(activity, onTimeSelectListener)
                .setType(precisionT)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("时间选择")
                .setOutSideCancelable(true)
                .isCyclic(false)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setLabel("年", "月", "日", "时", "分", "秒")
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(false)
                //是否显示为对话框样式
                .isDialog(false)
                .build();
    }

    /**
     * 处理事件精度
     *
     * @param precision 精度
     * @return boolean
     */
    private static boolean[] initPrecision(String precision) {
        boolean[] precisionT;
        switch (precision) {
            case Constant.YM:
            case Constant.YM1:
                precisionT = new boolean[]{true, true, false, false, false, false};
                break;
            case Constant.YMD:
            case Constant.YMD1:
                precisionT = new boolean[]{true, true, true, false, false, false};
                break;
            case Constant.YMDH:
            case Constant.YMDH1:
                precisionT = new boolean[]{true, true, true, true, false, false};
                break;
            case Constant.YMDHM:
            case Constant.YMDHM1:
                precisionT = new boolean[]{true, true, true, true, true, false};
                break;
            case Constant.Y:
                precisionT = new boolean[]{true, false, false, false, false, false};
                break;
            case Constant.M:
                precisionT = new boolean[]{false, true, false, false, false, false};
                break;
            default:
                precisionT = new boolean[]{true, true, true, true, true, true};
                break;
        }
        return precisionT;
    }


    /**
     * @param activity                      context
     * @param onOptionsSelectListener       选中监听
     * @param onOptionsSelectChangeListener 切换监听
     * @return
     */
    public static OptionsPickerView selectPickerSelecter(Activity activity,
                                                         OnOptionsSelectListener onOptionsSelectListener,
                                                         OnOptionsSelectChangeListener onOptionsSelectChangeListener) {
        return new OptionsPickerBuilder(activity, onOptionsSelectListener)
                .setOptionsSelectChangeListener(onOptionsSelectChangeListener)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setTitleText("请选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setContentTextSize(18)//滚轮文字大小
                .setLabels("", "", "")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(false)//切换时是否还原，设置默认选中第一项。
                .build();
    }

    /**
     * @param activity                context
     * @param onOptionsSelectListener 选中监听
     * @return
     */
    public static OptionsPickerView selectPickerSelecter(Activity activity,
                                                         OnOptionsSelectListener onOptionsSelectListener) {
        return new OptionsPickerBuilder(activity, onOptionsSelectListener)
                .setCancelText(activity.getString(R.string.ove))
                .setSubmitText(activity.getString(R.string.affirm))
                .setTitleText("请选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(Color.WHITE)
                .setBgColor(Color.WHITE)
                .setContentTextSize(18)//滚轮文字大小
                .setLabels("", "", "")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(false)//是否显示为对话框样式
                .isRestoreItem(false)//切换时是否还原，设置默认选中第一项。
                .build();
    }

}
