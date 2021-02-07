package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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

    private BaseQuickAdapter baseQuickAdapter;
    private RecyclerView recyclerView;
    private LinearLayout operationLayout;
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
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNewData(List<T> data) {
        if (baseQuickAdapter != null) {
            baseQuickAdapter.setNewData(data);
        }
    }

    /**
     * CommonRecyclerPopupListener
     */
    public interface CommonRecyclerPopupListener {
        /**
         * onSure
         */
        void onSure();
    }

    public CommonRecyclerPopupWindow(Context context, BaseQuickAdapter baseQuickAdapter,
                                     BaseQuickAdapter.OnItemChildClickListener onItemClickListener,
                                     BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        super(context);
        try {
            initView();
            initRecycler(context, baseQuickAdapter, onItemClickListener);
            baseQuickAdapter.setOnLoadMoreListener(requestLoadMoreListener, recyclerView);
        } catch (Exception e) {
            e.printStackTrace();
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

    public CommonRecyclerPopupWindow(Context context, BaseQuickAdapter baseQuickAdapter,
                                     BaseQuickAdapter.OnItemChildClickListener onItemCListener,
                                     CommonRecyclerPopupListener popupListener) {
        super(context);
        try {
            initView();
            initRecycler(context, baseQuickAdapter, onItemCListener, popupListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public CommonRecyclerPopupWindow(Context context, BaseQuickAdapter baseQuickAdapter, DialogFragment dialogFragment) {
        super(dialogFragment);
        try {
            initView();
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
            }
            ove.setOnClickListener(v -> dismiss());
            sure.setOnClickListener(v -> dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initRecycler(Context context, BaseQuickAdapter baseQuickAdapter,
                              BaseQuickAdapter.OnItemChildClickListener onItemClickListener) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecycler(Context context, BaseQuickAdapter baseQuickAdapter,
                              BaseQuickAdapter.OnItemChildClickListener onItemClickListener,
                              CommonRecyclerPopupListener popupListener) {
        try {
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
            operationLayout.setVisibility(View.VISIBLE);
            ove.setOnClickListener(v -> dismiss());
            sure.setOnClickListener(v -> {
                popupListener.onSure();
                dismiss();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_view);
        operationLayout = findViewById(R.id.operation_layout);
        ove = findViewById(R.id.ove);
        sure = findViewById(R.id.sure);
    }


    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_common_window);
    }

}
