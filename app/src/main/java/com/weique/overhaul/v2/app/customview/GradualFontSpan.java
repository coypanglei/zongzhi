package com.weique.overhaul.v2.app.customview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.style.ReplacementSpan;

/**
 * 渐变色控制
 * @author GreatKing
 */
public class GradualFontSpan extends ReplacementSpan {
    // 文字宽度
    private int mSize;
    // 渐变开始颜色
    private int startColor = Color.BLUE;
    // 渐变结束颜色
    private int endColor = Color.RED;
    // 是否左右渐变
    private boolean isLeftToRight = true;

    public GradualFontSpan() {
    }

    public GradualFontSpan(int startColor, int endColor, boolean leftToRight) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.isLeftToRight = leftToRight;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end));
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        // 修改y1的值从上到下渐变， 修改x1的值从左到右渐变
        LinearGradient lg;
        if (isLeftToRight) {
            lg = new LinearGradient(0, 0, mSize, 0,
                    startColor,
                    endColor,
                    Shader.TileMode.REPEAT);
        } else {
            lg = new LinearGradient(0, 0, 0, paint.descent() - paint.ascent(),
                    startColor,
                    endColor,
                    Shader.TileMode.REPEAT);
        }
        paint.setShader(lg);

        canvas.drawText(text, start, end, x, y, paint);//绘制文字
    }

    public void setLeftToRight(boolean leftToRight) {
        isLeftToRight = leftToRight;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }
}
