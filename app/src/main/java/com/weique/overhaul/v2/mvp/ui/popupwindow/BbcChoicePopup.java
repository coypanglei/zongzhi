package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.customview.MyFlexboxLayoutManager;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.mvp.model.entity.PartyCenterRecommendedBean;
import com.weique.overhaul.v2.mvp.ui.adapter.PartyChoicePopupItemAdapter;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author GK
 */
public class BbcChoicePopup extends BasePopupWindow {

    private RecyclerView recyclerView;
    private PartyChoicePopupItemAdapter itemAdapter = null;
    private ListItemClickListener listItemClickListener;

    /**
     * reyclerview  点击回调
     */
    public interface ListItemClickListener {
        /**
         * 点击
         *
         * @param pos pos
         */
        void onItemClick(int pos);
    }

    public ListItemClickListener getListItemClickListener() {
        return listItemClickListener;
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public BbcChoicePopup(Context context, List<PartyCenterRecommendedBean.TypesBean> typesBeans) {
        super(context);
        try {
            recyclerView = findViewById(R.id.choice_list);
            LinearLayout closeIcon = findViewById(R.id.close_icon);
            View close_view = findViewById(R.id.close_view);
            //2，增加谷歌流式布局

            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setPopupGravity(Gravity.BOTTOM);
            setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent));
            MyFlexboxLayoutManager manager = new MyFlexboxLayoutManager(context);
            //设置主轴排列方式
            manager.setFlexDirection(FlexDirection.ROW);
            //设置是否换行
            manager.setFlexWrap(FlexWrap.WRAP);
            manager.setAlignItems(AlignItems.STRETCH);
            manager.setJustifyContent(JustifyContent.FLEX_START);
            ArmsUtils.configRecyclerView(recyclerView, manager);
            if (itemAdapter == null) {
                itemAdapter = new PartyChoicePopupItemAdapter(typesBeans);
                recyclerView.setAdapter(itemAdapter);
            } else {
                itemAdapter.setNewData(typesBeans);
            }
            itemAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    listItemClickListener.onItemClick(position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            closeIcon.setOnClickListener(v -> dismiss());
            close_view.setOnClickListener(v -> dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.bbc_choice_layout);
    }
}
