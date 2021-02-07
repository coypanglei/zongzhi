package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.animation.AlphaConfig;
import razerdp.util.animation.AnimationHelper;
import razerdp.util.animation.ScaleConfig;
import razerdp.util.animation.TranslationConfig;

public class AnswerPopWin extends BasePopupWindow implements View.OnClickListener {

    @BindView(R.id.answer)
    TextView mAnswer;
    @BindView(R.id.close)
    ImageView close;

    public AnswerPopWin(Context context, String answer) {
        super(context);
        ButterKnife.bind(this, getContentView());
        try {
            setPopupGravity(Gravity.BOTTOM);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//            setBackgroundColor(ArmsUtils.getResources(context).getColor(R.color.white_9));
            setBackgroundColor(ArmsUtils.getColor(context, R.color.transparent));
            setOutSideDismiss(true);

            if (TextUtils.isEmpty(answer)) {
                mAnswer.setText("暂无解析");
            } else {
                mAnswer.setText(answer);
            }
            close.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_answer);
    }


    @Override
    protected Animation onCreateShowAnimation() {
        return AnimationHelper.asAnimation()
                .withAlpha(AlphaConfig.IN)
                .toShow();
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return AnimationHelper.asAnimation()
                .withAlpha(AlphaConfig.OUT)
                .toDismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                dismiss();
                break;
            default:
        }
    }
}
