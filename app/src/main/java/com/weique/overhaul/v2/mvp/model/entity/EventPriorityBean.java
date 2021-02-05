package com.weique.overhaul.v2.mvp.model.entity;

import androidx.annotation.IntDef;

import com.weique.overhaul.v2.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class EventPriorityBean {
    public static final int COMMON = 0;
    public static final int URGENCY = 1;
    public static final int SIGNIFICANT = 2;

    public static final String[] priority = {"普通事件", "紧急事件", "重大事件"};
    public static final int[] drawableId = {R.drawable.shape_circle_10_b_214bff, R.drawable.shape_circle_10_b_16ceab, R.drawable.shape_circle_10_b_ffb600};
    public static final int[] colorId = {R.color.green_00cfab, R.color.yellow_ffb700, R.color.red_ff2700};
    @EventPriorityEnumBean
    private int mPriorityIndex;

    public EventPriorityBean() {
    }

    public EventPriorityBean(@EventPriorityEnumBean int mPriorityIndex) {
        this.mPriorityIndex = mPriorityIndex;
    }

    //用 @IntDef "包住" 常量；
    // @Retention 定义策略
    // 声明构造器
    @IntDef({COMMON, URGENCY, SIGNIFICANT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventPriorityEnumBean {

    }

    public int getmPriorityIndex() {
        return mPriorityIndex;
    }

    public void setPriority(@EventPriorityEnumBean int priorityIndex) {
        this.mPriorityIndex = priorityIndex;
    }

    public String getPriority() {
        return priority[mPriorityIndex];
    }

    public int getdrawableId() {
        return drawableId[mPriorityIndex];
    }

    public int getColorId() {
        return colorId[mPriorityIndex];
    }

    public String getPriority(@EventPriorityEnumBean int priorityIndex) {
        return priority[priorityIndex];
    }
}
