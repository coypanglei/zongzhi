package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.model.entity.EventPriorityBean;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author GreatKing
 */
public class EventPriorityPopup extends BasePopupWindow implements View.OnClickListener {

    private EventPriorityBean eventPriorityBean;

    public interface OnLevelItemClick {
        /**
         * onLevelClick
         *
         * @param eventPriorityBean eventPriorityBean
         */
        void onLevelClick(EventPriorityBean eventPriorityBean);
    }


    private LinearLayout eventOne;
    private TextView eventOneText;
    private LinearLayout eventTwo;
    private TextView eventTwoText;
    private LinearLayout eventThree;
    private TextView eventThreeText;

    private OnLevelItemClick onLevelItemClick;

    public void setOnLevelItemClick(OnLevelItemClick onLevelItemClick) {
        this.onLevelItemClick = onLevelItemClick;
    }


    /**
     * 支持延迟加载的PopupWindow
     *
     * @param context
     */
    public EventPriorityPopup(Context context) {
        super(context);
        initpopup();
    }

    private void initpopup() {
        try {
            setPopupGravity(Gravity.BOTTOM);
//        setBlurBackgroundEnable(true);
            setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent48));
            setOutSideDismiss(true);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            eventPriorityBean = new EventPriorityBean();
            eventOne = findViewById(R.id.event_one);
            eventOneText = findViewById(R.id.event_one_text);
            eventOneText.setText(eventPriorityBean.getPriority(EventPriorityBean.COMMON));
            eventOne.setOnClickListener(this);
            eventTwo = findViewById(R.id.event_two);
            eventTwoText = findViewById(R.id.event_two_text);
            eventTwoText.setText(eventPriorityBean.getPriority(EventPriorityBean.URGENCY));
            eventTwo.setOnClickListener(this);
            eventThree = findViewById(R.id.event_three);
            eventThreeText = findViewById(R.id.event_three_text);
            eventThreeText.setText(eventPriorityBean.getPriority(EventPriorityBean.SIGNIFICANT));
            eventThree.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_event_priority);
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.event_one:
                    eventPriorityBean.setPriority(EventPriorityBean.COMMON);
                    dismiss();
                    break;
                case R.id.event_two:
                    eventPriorityBean.setPriority(EventPriorityBean.URGENCY);
                    dismiss();
                    break;
                case R.id.event_three:
                    eventPriorityBean.setPriority(EventPriorityBean.SIGNIFICANT);
                    dismiss();
                    break;
                default:
            }
            onLevelItemClick.onLevelClick(eventPriorityBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
