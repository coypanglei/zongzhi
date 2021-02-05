package com.weique.overhaul.v2.app.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.weique.overhaul.v2.R;

/**
 * @author  GK
 */
public class RoundnessProgressBar extends HorizontalProgressBarWithNumber {

    private int mMaxPaintWidth;
    private int mRadius = dp2px(30);

    //线1的x轴
    private int line1_x = 0;
    //线1的y轴
    private int line1_y = 0;
    //线2的x轴
    private int line2_x = 0;
    //线2的y轴
    private int line2_y = 0;


    public RoundnessProgressBar(Context context) {
        this(context, null);
    }

    public RoundnessProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mReachedProgressBarHeight = (int) (mUnReachedProgressBarHeight * 2.5f);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBarWidthNumber);
        mRadius = (int) typedArray.getDimension(R.styleable.RoundProgressBarWidthNumber_radius, mRadius);
        typedArray.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMaxPaintWidth = Math.max(mReachedProgressBarHeight, mUnReachedProgressBarHeight);
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingLeft() + getPaddingRight();
        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);
        int realWidth = Math.min(width, height);


        mRadius = (realWidth - getPaddingRight() - getPaddingLeft() - mMaxPaintWidth) / 2;
        setMeasuredDimension(realWidth, realWidth);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;
        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop() + mMaxPaintWidth / 2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mUnReachedBarColor);
        mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        mPaint.setColor(mReachedBarColor);
        mPaint.setStrokeWidth(mReachedProgressBarHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), 0, sweepAngle, false, mPaint);
        mPaint.setStyle(Paint.Style.FILL);



        //获取圆心的x坐标
        int center = getWidth() / 2;
        int center1 = center - getWidth() / 5;
        //圆弧半径
        int radius = getWidth() / 2 - 5;

        if (getProgress() >= 100) {
            canvas.drawText("", mRadius - textWidth / 2, mRadius - textHeight, mPaint);
            canvas.restore();
            if (line1_x < radius / 3) {
                line1_x++;
                line1_y++;
            }
            //画第一根线
            canvas.drawLine(center1, center, center1 + line1_x, center + line1_y, mPaint);

            if (line1_x == radius / 3) {
                line2_x = line1_x;
                line2_y = line1_y;
                line1_x++;
                line1_y++;
            }
            if (line1_x >= radius / 3 && line2_x <= radius) {
                line2_x++;
                line2_y--;
            }
            //画第二根线
            canvas.drawLine(center1 + line1_x - 1, center + line1_y, center1 + line2_x, center + line2_y, mPaint);
        }else {
            canvas.drawText(text, mRadius - textWidth / 2, mRadius - textHeight, mPaint);
            canvas.restore();
        }

        //每隔10毫秒界面刷新
        postInvalidateDelayed(100);
    }


}
