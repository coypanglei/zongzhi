package com.weique.overhaul.v2.app.customview;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * @author meitu.xujun  on 2017/5/2 14:43
 * @version 0.1
 */

public class NestedLinearLayout extends LinearLayout implements NestedScrollingChild {

    private static final String TAG = "NestedLinearLayout";

    private final int[] offset = new int[2];
    private final int[] consumed = new int[2];

    private NestedScrollingChildHelper mScrollingChildHelper;
    private int lastY;
    private int mDownY;
    private int mScaledTouchSlop;

    public NestedLinearLayout(Context context) {
        this(context, null);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
            mScrollingChildHelper.setNestedScrollingEnabled(true);
        }

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mScaledTouchSlop = configuration.getScaledTouchSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = (int) event.getRawY();
                // 当开始滑动的时候，告诉父view
                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL
                        | ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                // 确保不消耗 ACTION_DOWN 事件
                if (Math.abs(event.getRawY() - mDownY) > mScaledTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL
                        | ViewCompat.SCROLL_AXIS_VERTICAL);
                return true;
            case MotionEvent.ACTION_MOVE:
                int dy = (int) (event.getRawY() - lastY);
                lastY = (int) event.getRawY();
                //  dy < 0 上滑， dy>0 下拉
                if (dy < 0) { // 上滑的时候才交给父类去处理
                    if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) // 如果找到了支持嵌套滚动的父类
                            && dispatchNestedPreScroll(0, -dy, consumed, offset)) {//
                        // 父类进行了一部分滚动

                    }
                } else {
                    if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) // 如果找到了支持嵌套滚动的父类
                            && dispatchNestedScroll(0, 0, 0, -dy, offset)) {//
                        // 父类进行了一部分滚动

                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
                break;

        }
        return super.onTouchEvent(event);
    }



    private NestedScrollingChildHelper getScrollingChildHelper() {
        return mScrollingChildHelper;
    }

    // 接口实现--------------------------------------------------

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed,
                                        int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed,
                dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed,
                                           int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy,
                consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY,
                                       boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX,
                velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX,
                velocityY);
    }
}
