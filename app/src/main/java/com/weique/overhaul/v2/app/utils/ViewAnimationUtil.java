package com.weique.overhaul.v2.app.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class ViewAnimationUtil {
    /**
     * 旋转动画
     *
     * @param v      view
     * @param to_fro to_fro 怎么转
     */
    public static void rotateAnimation(View v, boolean to_fro) {
        try {
            Animation an = null;
            if (to_fro) {
                an = new RotateAnimation(0, 180,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
            } else {
                an = new RotateAnimation(180, 0,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
            }
            an.setDuration(500);
            an.setRepeatCount(0);
            an.setInterpolator(new LinearInterpolator());//不停顿
            an.setFillAfter(true);//
//                an.setFillAfter(true);
            v.startAnimation(an);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
