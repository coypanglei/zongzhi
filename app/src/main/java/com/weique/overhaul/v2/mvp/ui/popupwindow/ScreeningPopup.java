package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.PopupWindow;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author GK
 */
public class ScreeningPopup extends BasePopupWindow implements View.OnClickListener {
    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.screening_popup);
    }

    public ScreeningPopup(Context context) {
        super(context);
        try {
            setPopupGravity(Gravity.BOTTOM);
//        setBlurBackgroundEnable(true);
            setAlignBackground(true);
//            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        setOutSideTouchable(true);
            setBackgroundColor(ArmsUtils.getColor(context, R.color.white));
            setOutSideDismiss(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // 以下为可选代码（非必须实现）
    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }


    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }

    @Override
    public void onClick(View view) {

    }


}
