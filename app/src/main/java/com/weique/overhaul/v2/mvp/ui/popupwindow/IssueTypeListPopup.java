package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.mvp.ui.adapter.IssueTypeListPopupAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

public class IssueTypeListPopup extends BasePopupWindow {

    private RecyclerView recyclerView;
    private IssueTypeListPopupAdapter itemAdapter = null;
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
        void onItemClick(int pos, String text);
    }

    public ListItemClickListener getListItemClickListener() {
        return listItemClickListener;
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public void setNewData(List<String> strings) {
        itemAdapter.setNewData(strings);
    }

    public IssueTypeListPopup(Context context) {
        super(context);
        try {
            recyclerView = findViewById(R.id.recycler_view);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            setPopupGravity(Gravity.BOTTOM);
            setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent));
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(context));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
            if (itemAdapter == null) {
                itemAdapter = new IssueTypeListPopupAdapter();
                recyclerView.setAdapter(itemAdapter);
            }
            itemAdapter.setOnItemClickListener((adapter, view, position) -> {
                try {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    String item = (String) adapter.getItem(position);
                    listItemClickListener.onItemClick(position, item);
                    dismiss();
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
        return createPopupById(R.layout.popup_issue_type);
    }
}
