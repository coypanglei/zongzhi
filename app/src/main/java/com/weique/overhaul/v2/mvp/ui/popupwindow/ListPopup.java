package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBean;
import com.weique.overhaul.v2.mvp.ui.adapter.ListPopItemAdapter;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author GK
 */
public class ListPopup extends BasePopupWindow implements View.OnClickListener {
    private RecyclerView recyclerView;
    private ListPopItemAdapter itemAdapter = null;
    private ListItemClickListener listItemClickListener;



    /**
     * reyclerview  点击回调
     */
    public interface ListItemClickListener {
        /**
         * 点击
         *
         * @param id id
         */
        void onItemClick(String id,String name);
    }

    public ListItemClickListener getListItemClickListener() {
        return listItemClickListener;
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.list_pop);
    }

    public ListPopup(Context context,List<KnowledgeBean>list) {
        super(context);
        try {
            setPopupGravity(Gravity.BOTTOM);
//        setBlurBackgroundEnable(true);
            setAlignBackground(true);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        setOutSideTouchable(true);
//        setBackgroundColor(ArmsUtils.getResources(context).getColor(R.color.white));
            setOutSideDismiss(true);


            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            ArmsUtils.configRecyclerView(recyclerView,new LinearLayoutManager(getContext()));
            if (itemAdapter == null) {
                itemAdapter = new ListPopItemAdapter(list);
                recyclerView.setAdapter(itemAdapter);
            } else {
                itemAdapter.setNewData(list);
            }

            itemAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    listItemClickListener.onItemClick(((KnowledgeBean)adapter.getData().get(position)).getId(),((KnowledgeBean)adapter.getData().get(position)).getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
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
