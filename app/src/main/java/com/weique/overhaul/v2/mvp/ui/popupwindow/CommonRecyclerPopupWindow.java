package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * 公共  可选择 item popup window
 *
 * @author GreatKing
 */
public class CommonRecyclerPopupWindow<T extends NameAndIdInterface> extends BasePopupWindow {

    private BaseQuickAdapter<T, BaseViewHolder> baseQuickAdapter;
    private RecyclerView recyclerView;
    private TextView ove;
    private TextView sure;
    private View view;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setNewData(List<T> data, boolean isLoadMore) {
        if (baseQuickAdapter != null) {
            if (isLoadMore) {
                baseQuickAdapter.addData(data);
                if (data.size() < ReworkBasePresenter.pageSize) {
                    baseQuickAdapter.loadMoreComplete();
                } else {
                    baseQuickAdapter.loadMoreEnd(true);
                }
            } else {
                baseQuickAdapter.setNewData(data);
                baseQuickAdapter.loadMoreEnd(true);
            }
        }
    }

    public void setNewData(List<T> data) {
        if (baseQuickAdapter != null) {
            baseQuickAdapter.setNewData(data);
        }
    }


    public CommonRecyclerPopupWindow(Context context, BaseQuickAdapter baseQuickAdapter,
                                     BaseQuickAdapter.OnItemChildClickListener onItemCListener) {
        super(context);
        try {
            initView();
            initRecycler(context, baseQuickAdapter, onItemCListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecycler(Context context, BaseQuickAdapter baseQuickAdapter,
                              BaseQuickAdapter.OnItemChildClickListener onItemClickListener) {
        this.baseQuickAdapter = baseQuickAdapter;
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setPopupGravity(Gravity.BOTTOM);
        setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent48));
        ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .colorResId(R.color.gray_eee)
                .sizeResId(R.dimen.dp_1)
                .build());
        if (baseQuickAdapter != null) {
            recyclerView.setAdapter(baseQuickAdapter);
            baseQuickAdapter.setOnItemChildClickListener(onItemClickListener);
        }
        ove.setOnClickListener(v -> dismiss());
        sure.setOnClickListener(v -> dismiss());
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        ove = findViewById(R.id.ove);
        sure = findViewById(R.id.sure);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_common_window);
    }




//    @Override
//    protected View onFindDecorView(Activity activity) {
//        if (ObjectUtils.isEmpty(view)) {
//            return super.onFindDecorView(activity);
//        } else {
//            return view;
//        }
//    }
}
