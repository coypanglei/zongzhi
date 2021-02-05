package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author GK
 */
public class UrgencyPopup extends BasePopupWindow implements View.OnClickListener {


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.home_popup);
    }

    public UrgencyPopup(Context context) {
        super(context);
        try {
            setPopupGravity(Gravity.BOTTOM);
            setBlurBackgroundEnable(true);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setBackgroundColor(ArmsUtils.getResources(context).getColor(R.color.white_9));
            setOutSideDismiss(false);
            LinearLayout up_cell = findViewById(R.id.up_cell);
            up_cell.setOnClickListener(this);
            LinearLayout up_tell = findViewById(R.id.up_tell);
            up_tell.setOnClickListener(this);
            ImageView close = findViewById(R.id.close);
            close.setColorFilter(ArmsUtils.getColor(context,R.color.gray_8f));
            close.setOnClickListener(this);
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                dismiss();
                break;
            case R.id.up_cell:
                ArmsUtils.makeText("一键呼叫");
                dismiss();
                break;
            case R.id.up_tell:
                ArmsUtils.makeText("一键上报");
                dismiss();
                break;
            default:
        }
    }
}
