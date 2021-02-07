package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.JustifyContent;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.customview.MyFlexboxLayoutManager;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;
import com.weique.overhaul.v2.mvp.ui.adapter.NewOrderSortPopupAdapter;

import java.util.ArrayList;
import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author GK
 */
public class NewOrderSortPopup<T extends NameAndIdInterface> extends BasePopupWindow implements View.OnClickListener {

    private RecyclerView recyclerView;
    private View fillView;
    private Button reset;
    private Button sure;
    private NewOrderSortPopupAdapter<T> itemAdapter = null;
    private ListItemClickListener listItemClickListener;
    private int mCheckPos = -10;
    private List<T> beans;
    private T bean = null;

    public List<T> getBeans() {
        return beans;
    }

    public void setBeans(List<T> beans) {
        this.beans = beans;
        itemAdapter.setNewData(beans);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                itemAdapter.setCheckPos(-1);
                mCheckPos = -1;
                break;
            case R.id.sure:
                if (mCheckPos < 0) {
                    bean = null;
                    listItemClickListener.reset();
                } else {
                    listItemClickListener.onItemClick(bean.getId(), bean.getName());
                }
                dismiss();
                break;
            case R.id.fill_view:
                dismiss();
                break;
            default:
        }
    }

    /**
     * reyclerview  点击回调
     */
    public interface ListItemClickListener {
        /**
         * 点击
         *
         * @param orderStatus orderStatus
         */
        void onItemClick(String orderStatus, String name);

        /**
         * 重置
         */
        void reset();
    }

    public ListItemClickListener getListItemClickListener() {
        return listItemClickListener;
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }


    public void setCheckPos() {
        itemAdapter.setCheckPos(mCheckPos);
    }

    public void setCheckPos(int pos) {
        itemAdapter.setCheckPos(pos);
    }

    public NewOrderSortPopup(Context context) {
        super(context);
        try {
            setPopupGravity(Gravity.BOTTOM);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//            setBackgroundColor(ArmsUtils.getResources(context).getColor(R.color.white_9));
            setBackgroundColor(ArmsUtils.getColor(context, R.color.transparent));
            setOutSideDismiss(true);
            recyclerView = findViewById(R.id.choice_list);
            fillView = findViewById(R.id.fill_view);
            fillView.setOnClickListener(this);
            reset = findViewById(R.id.reset);
            reset.setOnClickListener(this);
            sure = findViewById(R.id.sure);
            sure.setOnClickListener(this);
            //2，增加谷歌流式布局
            MyFlexboxLayoutManager manager = new MyFlexboxLayoutManager(context);
            //设置主轴排列方式
            manager.setFlexDirection(FlexDirection.ROW);
            //设置是否换行
            manager.setFlexWrap(FlexWrap.WRAP);
            manager.setAlignItems(AlignItems.STRETCH);
            manager.setJustifyContent(JustifyContent.FLEX_START);
            ArmsUtils.configRecyclerView(recyclerView, manager);
            if (beans == null) {
                beans = new ArrayList<>();
            }
            itemAdapter = new NewOrderSortPopupAdapter(beans);
            recyclerView.setAdapter(itemAdapter);
            itemAdapter.setOnItemClickListener((adapter, view, position) -> {
                //这里直接 已 position 对用 订单状态， -2是因为  后端定义订单状态 暂存是-1  加上全部占用了一个下标
                try {
                    mCheckPos = position;
                    Object item = adapter.getItem(position);
                    bean = (T) item;
                    itemAdapter.setCheckPos(mCheckPos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_order_sort);
    }
}
