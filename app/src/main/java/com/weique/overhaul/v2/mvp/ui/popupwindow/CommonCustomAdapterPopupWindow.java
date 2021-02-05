package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.utils.ArmsUtils;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameIdCheckedInterface;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * @author GK
 * @description: 通用自定义adapter popup window
 * @date :2021/2/2 13:48
 */
public class CommonCustomAdapterPopupWindow<T extends NameIdCheckedInterface> extends BottomPopupView implements View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView ove, title, sure;
    private LinearLayout headView;
    private BaseQuickAdapter<T, BaseViewHolder> baseQuickAdapter;

    private OnCustomAdapterPopupListener<T> onCustomAdapterPopupListener;
    private T checkedItem;

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_common_window;
    }

    public CommonCustomAdapterPopupWindow(@NonNull Context context, BaseQuickAdapter<T, BaseViewHolder> baseQuickAdapter) {
        super(context);
        this.baseQuickAdapter = baseQuickAdapter;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        recyclerView = findViewById(R.id.recycler_view);
        ove = findViewById(R.id.ove);
        title = findViewById(R.id.title);
        sure = findViewById(R.id.sure);
        headView = findViewById(R.id.head_view);
        initRecycler();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 设置新数据
     *
     * @param data       data
     */
    public void setMoreData(List<T> data) {
        if (baseQuickAdapter != null) {
            baseQuickAdapter.addData(data);
            if (data.size() < ReworkBasePresenter.pageSize) {
                baseQuickAdapter.loadMoreComplete();
            } else {
                baseQuickAdapter.loadMoreEnd(true);
            }
        }
    }

    public void setNewData(List<T> data) {
        if (baseQuickAdapter != null) {
            baseQuickAdapter.setNewData(data);
        }
    }

    public BasePopupView show(T t) {
        if (t != null) {
            checkedItem = t;
            if (baseQuickAdapter != null) {
                List<T> data = baseQuickAdapter.getData();
                for (T datum : data) {
                    if (t.getName().equals(datum.getName())) {
                        datum.setChecked(true);
                    } else {
                        datum.setChecked(false);
                    }
                }
            }
        }
        return super.show();
    }

    /**
     * item的点击事件
     *
     * @param <T> t
     */
    public interface OnCustomAdapterPopupListener<T> {
        void onItemClick(CommonCustomAdapterPopupWindow popup, T t, int pos);
    }

    public void setOnCustomAdapterPopupListener(OnCustomAdapterPopupListener<T> onCustomAdapterPopupListener) {
        this.onCustomAdapterPopupListener = onCustomAdapterPopupListener;
    }

    public void setLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener listener) {
        if (baseQuickAdapter != null) {
            baseQuickAdapter.setOnLoadMoreListener(listener, recyclerView);
        }
    }


    private void initRecycler() {
        try {
            ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
            if (baseQuickAdapter != null) {
                baseQuickAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    T t = baseQuickAdapter.getItem(position);
                    onCustomAdapterPopupListener.onItemClick(CommonCustomAdapterPopupWindow.this, t, position);
                    dismiss();
                });
                recyclerView.setAdapter(baseQuickAdapter);
            }
            ove.setOnClickListener(v -> dismiss());
            sure.setOnClickListener(v -> dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getScreenHeight(getContext()) * .7f);
    }
}
