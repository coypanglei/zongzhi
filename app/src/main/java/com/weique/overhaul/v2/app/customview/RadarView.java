package com.weique.overhaul.v2.app.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.weique.overhaul.v2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Depiction:
 * Author:lry
 * Date:2018/5/14
 */
@SuppressLint("AppCompatCustomView")
public class RadarView extends TextView {

    private TextView textview;
    private String timeString = "00:00:00";

    private TimeHandler mTimehandler = new TimeHandler();

    //默认的主题颜色
    private int DEFAULT_COLOR = Color.parseColor("#91D7F4");

    // 圆圈和交叉线的颜色
    private int mCircleColor = DEFAULT_COLOR;
    //圆圈的数量 不能小于1
    private int mCircleNum = 3;
    //扫描的颜色 RadarView会对这个颜色做渐变透明处理
    private int mSweepColor = DEFAULT_COLOR;
    //水滴的颜色
    private int mRaindropColor = DEFAULT_COLOR;
    //水滴的数量 这里表示的是水滴最多能同时出现的数量。因为水滴是随机产生的，数量是不确定的
    private int mRaindropNum = 4;
    //是否显示交叉线
    private boolean isShowCross = true;
    //是否显示水滴
    private boolean isShowRaindrop = true;
    //扫描的转速，表示几秒转一圈
    private float mSpeed = 3.0f;
    //水滴显示和消失的速度
    private float mFlicker = 3.0f;

    private Paint mCirclePaint;// 圆的画笔
    private Paint mSweepPaint; //扫描效果的画笔
    private Paint mRaindropPaint;// 水滴的画笔
    private Paint mTimePaint;// 时间的画笔
    private Paint mUpTimePaint;// 时间上方文字的画笔

    private float mDegrees; //扫描时的扫描旋转角度。
    private boolean isScanning = false;//是否扫描


    private boolean sign = true;

    //保存水滴数据
    private ArrayList<Raindrop> mRaindrops = new ArrayList<>();


    @SuppressLint("HandlerLeak")
    private class TimeHandler extends Handler {
        private boolean mStopped;

        private void post() {
            //每隔1秒发送一次消息
            sendMessageDelayed(obtainMessage(0), 1000);
        }

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            if (!mStopped) {

                updateClock();
                //实现实时更新
                post();

            }
        }

        //开始更新
        public void startScheduleUpdate() {

            mStopped = false;

            post();

        }

//停止更新

        public void stopScheduleUpdate() {

            mStopped = true;

            removeMessages(0);

        }
        //返回当前的时间，并结束handler的信息发送

        public String getTime() {
            //停止发送消息
            mTimehandler.stopScheduleUpdate();

            return timeString;

        }

        private void updateClock() {

//获取当前的时间

            Calendar calendar = Calendar.getInstance();

            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            int minute = calendar.get(Calendar.MINUTE);

            int second = calendar.get(Calendar.SECOND);

            String s = "";

            String m = "";

            String h = "";

            if (hour < 10) {

                h = "0" + hour;

            } else {

                h = hour + "";

            }

            if (minute < 10) {

                m = "0" + minute;

            } else {

                m = minute + "";

            }

            if (second < 10) {

                s = "0" + second;

            } else {

                s = second + "";

            }
            timeString = h + ":" + m + ":" + s;


        }


    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public RadarView(Context context) {
        super(context);
        this.textview = this;
        init();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.textview = this;
        getAttrs(context, attrs);
        init();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context, attrs);
        this.textview = this;
        init();
    }

    /**
     * 获取自定义属性值
     *
     * @param context
     * @param attrs
     */
    private void getAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarView);
            mCircleColor = mTypedArray.getColor(R.styleable.RadarView_circleColor, DEFAULT_COLOR);
            mCircleNum = mTypedArray.getInt(R.styleable.RadarView_circleNum, mCircleNum);
//            if (mCircleNum < 1) {
//                mCircleNum = 3;
//            }
            mSweepColor = mTypedArray.getColor(R.styleable.RadarView_sweepColor, DEFAULT_COLOR);
            mRaindropColor = mTypedArray.getColor(R.styleable.RadarView_raindropColor, DEFAULT_COLOR);
            mRaindropNum = mTypedArray.getInt(R.styleable.RadarView_raindropNum, mRaindropNum);
            isShowCross = mTypedArray.getBoolean(R.styleable.RadarView_showCross, true);
            isShowRaindrop = mTypedArray.getBoolean(R.styleable.RadarView_showRaindrop, true);
            mSpeed = mTypedArray.getFloat(R.styleable.RadarView_speed, mSpeed);
            if (mSpeed <= 0) {
                mSpeed = 3;
            }
            mFlicker = mTypedArray.getFloat(R.styleable.RadarView_flicker, mFlicker);
            if (mFlicker <= 0) {
                mFlicker = 3;
            }
            mTypedArray.recycle();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        try {
            // 初始化画笔
            mCirclePaint = new Paint();
            mCirclePaint.setColor(mCircleColor);
            mCirclePaint.setStrokeWidth(1);
            mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mCirclePaint.setAntiAlias(true);

            mRaindropPaint = new Paint();
            mRaindropPaint.setStyle(Paint.Style.FILL);
            mRaindropPaint.setAntiAlias(true);

            mSweepPaint = new Paint();
            mSweepPaint.setAntiAlias(true);

            mTimePaint = new Paint();
            mTimePaint.setAntiAlias(true);
            mTimePaint.setColor(Color.parseColor("#bfe1fb"));

            mUpTimePaint = new Paint();
            mUpTimePaint.setAntiAlias(true);
            mUpTimePaint.setColor(Color.parseColor("#fefefe"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mTimehandler.startScheduleUpdate();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置宽高,默认200dp
        int defaultSize = dp2px(getContext(), 200);
        setMeasuredDimension(measureWidth(widthMeasureSpec, defaultSize),
                measureHeight(heightMeasureSpec, defaultSize));
    }

    /**
     * 测量宽
     *
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    private int measureWidth(int measureSpec, int defaultSize) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize + getPaddingLeft() + getPaddingRight();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        result = Math.max(result, getSuggestedMinimumWidth());
        return result;
    }

    /**
     * 测量高
     *
     * @param measureSpec
     * @param defaultSize
     * @return
     */
    private int measureHeight(int measureSpec, int defaultSize) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        result = Math.max(result, getSuggestedMinimumHeight());
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //计算圆的半径
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int radius = Math.min(width, height) / 2;

        //计算圆的圆心
        int cx = getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) / 2;
        int cy = getPaddingTop() + (getHeight() - getPaddingTop() - getPaddingBottom()) / 2;

//        drawCircle(canvas, cx, cy, radius);
//
//        if (isShowCross) {
//            drawCross(canvas, cx, cy, radius);
//        }


        //正在扫描
        if (isScanning) {
            drawText(canvas, cx, cy, "定位中...", 10);

            if (isShowRaindrop) {
                drawRaindrop(canvas, cx, cy, radius);
            }
            drawSweep(canvas, cx, cy, radius);
            //计算雷达扫描的旋转角度
            mDegrees = (mDegrees + (360 / mSpeed / 60)) % 360;

            //触发View重新绘制，通过不断的绘制View的扫描动画效果
            invalidate();
        } else {
            if (!TextUtils.isEmpty(timeString)) {
                mTimePaint.reset();
                mTimePaint.setAntiAlias(true);
                mTimePaint.setColor(Color.parseColor("#bfe1fb"));
                mUpTimePaint.setTextAlign(Paint.Align.CENTER);
                mUpTimePaint.setTextSize(40);
                Paint.FontMetricsInt fontMetrics1 = mUpTimePaint.getFontMetricsInt();
                canvas.drawText(sign ? "签退" : "签到", cx, cy - 10, mUpTimePaint);
                drawText(canvas, cx, cy, timeString, 40);
                invalidate();
            }
        }
    }

    private void drawText(Canvas canvas, int cx, int cy, String time, int hight) {

        mTimePaint.setTextAlign(Paint.Align.CENTER);
        mTimePaint.setTextSize(30);
        Paint.FontMetricsInt fontMetrics = mTimePaint.getFontMetricsInt();
        canvas.drawText(time, cx, cy + hight, mTimePaint);


    }

    /**
     * 画圆
     */
//    private void drawCircle(Canvas canvas, int cx, int cy, int radius) {
//        //画mCircleNum个半径不等的圆圈。
//        for (int i = 0; i < mCircleNum; i++) {
//            canvas.drawCircle(cx, cy, radius - (radius / mCircleNum * i), mCirclePaint);
//        }
//    }

    /**
     * 画交叉线
     */
//    private void drawCross(Canvas canvas, int cx, int cy, int radius) {
//        //水平线
//        canvas.drawLine(cx - radius, cy, cx + radius, cy, mCirclePaint);
//
//        //垂直线
//        canvas.drawLine(cx, cy - radius, cx, cy + radius, mCirclePaint);
//    }

    /**
     * 生成水滴。水滴的生成是随机的，并不是每次调用都会生成一个水滴。
     */
    private void generateRaindrop(int cx, int cy, int radius) {

        // 最多只能同时存在mRaindropNum个水滴。
        if (mRaindrops.size() < mRaindropNum) {
            // 随机一个20以内的数字，如果这个数字刚好是0，就生成一个水滴。
            // 用于控制水滴生成的概率。
            boolean probability = (int) (Math.random() * 20) == 0;
            if (probability) {
                int x = 0;
                int y = 0;
                int xOffset = (int) (Math.random() * (radius - 20));
                int yOffset = (int) (Math.random() * (int) Math.sqrt(1.0 * (radius - 20) * (radius - 20) - xOffset * xOffset));

                if ((int) (Math.random() * 2) == 0) {
                    x = cx - xOffset;
                } else {
                    x = cx + xOffset;
                }

                if ((int) (Math.random() * 2) == 0) {
                    y = cy - yOffset;
                } else {
                    y = cy + yOffset;
                }

                mRaindrops.add(new Raindrop(x, y, 0, mRaindropColor));
            }
        }
    }

    /**
     * 删除水滴
     */
    private void removeRaindrop() {
        Iterator<Raindrop> iterator = mRaindrops.iterator();

        while (iterator.hasNext()) {
            Raindrop raindrop = iterator.next();
            if (raindrop.radius > 20 || raindrop.alpha < 0) {
                iterator.remove();
            }
        }
    }

    /**
     * 画雨点(就是在扫描的过程中随机出现的点)。
     */
    private void drawRaindrop(Canvas canvas, int cx, int cy, int radius) {
        generateRaindrop(cx, cy, radius);
        for (Raindrop raindrop : mRaindrops) {
            mRaindropPaint.setColor(raindrop.changeAlpha());
            canvas.drawCircle(raindrop.x, raindrop.y, raindrop.radius, mRaindropPaint);
            //水滴的扩散和透明的渐变效果
            raindrop.radius += 1.0f * 20 / 60 / mFlicker;
            raindrop.alpha -= 1.0f * 255 / 60 / mFlicker;
        }
        removeRaindrop();
    }

    /**
     * 画扫描效果
     */
    private void drawSweep(Canvas canvas, int cx, int cy, int radius) {
        //扇形的透明的渐变效果
        SweepGradient sweepGradient = new SweepGradient(cx, cy,
                new int[]{Color.TRANSPARENT, changeAlpha(mSweepColor, 0), changeAlpha(mSweepColor, 168),
                        changeAlpha(mSweepColor, 255), changeAlpha(mSweepColor, 255)
                }, new float[]{0.0f, 0.6f, 0.99f, 0.998f, 1f});
        mSweepPaint.setShader(sweepGradient);
        //先旋转画布，再绘制扫描的颜色渲染，实现扫描时的旋转效果。
        canvas.rotate(-90 + mDegrees, cx, cy);
        canvas.drawCircle(cx, cy, radius, mSweepPaint);
    }

    /**
     * 开始扫描
     */
    public void start() {
        if (!isScanning) {
            isScanning = true;
            invalidate();
        }
    }

    /**
     * 停止扫描
     */
    public void stop() {
        if (isScanning) {
            isScanning = false;
            mRaindrops.clear();
            mDegrees = 0.0f;
        }
    }

    /**
     * 关闭
     */
    public void close() {
        mRaindrops.clear();
        mDegrees = 0.0f;
        mTimehandler.stopScheduleUpdate();
        mTimehandler = null;
    }

    /**
     * 水滴数据类
     */
    private static class Raindrop {

        int x;
        int y;
        float radius;
        int color;
        float alpha = 255;

        public Raindrop(int x, int y, float radius, int color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }

        /**
         * 获取改变透明度后的颜色值
         *
         * @return
         */
        public int changeAlpha() {
            return RadarView.changeAlpha(color, (int) alpha);
        }

    }

    /**
     * dp转px
     */
    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * a
     * 改变颜色的透明度
     *
     * @param color
     * @param alpha
     * @return
     */
    private static int changeAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    //定义一个接口对象listerner
    private OnItemSelectListener listener;

    //获得接口对象的方法。
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    //定义一个接口
    public interface OnItemSelectListener {
        void onItemSelect(String indexString);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:

                if (!isScanning) {
                    if (listener != null) {
                        listener.onItemSelect(timeString);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:


                return true;

            default:
        }
        //这句话不要修改
        return super.onTouchEvent(event);
    }
}
