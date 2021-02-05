package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.mvp.model.entity.BaseSearchPopupBean;
import com.weique.overhaul.v2.mvp.ui.adapter.SimpleTextAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * 带搜索的popup
 *
 * @author GreatKing
 */
public class SearchPopup extends BasePopupWindow {

    private SimpleTextAdapter mAdapter;
    private RecyclerView recyclerView;
    private TextView searchBtn;
    private EditText searchText;
    private LinearLayout searchLayout;
    private PopupClickListener popupClickListener;

    /**
     * 数据大于多少时  显示搜索框
     */
    public static final int SHOW_SEARCH_VIEW_NUMBER = 10;
    /**
     * 每页几条
     */
    public static final int PAGE_NUMBER = 20;

    public void setLoadMoreViewHide(boolean b) {
        if (b) {
            mAdapter.loadMoreEnd(true);
        } else {
            mAdapter.loadMoreComplete();
        }
    }

    /**
     * Popup 监听
     */
    public interface PopupClickListener {
        /**
         * 点击搜索监听
         *
         * @param keyword keyword
         */
        void onSearchBtnClick(String keyword);

        /**
         * popup 中加载更多
         *
         * @param keyword keyword
         */
        void onLoadMore(String keyword);

        /**
         * item点击事件
         *
         * @param bean bean
         */
        void onItemClick(BaseSearchPopupBean bean);
    }

    /**
     * @param data   data
     * @param isMore isMore  是否是加载更多
     */
    public void setRecyclerViewData(List<? extends BaseSearchPopupBean> data, boolean isMore, boolean needAddMore) {
        try {
            if (mAdapter == null) {
                mAdapter = new SimpleTextAdapter();
                ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(mAdapter);
                recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext())
                        .colorResId(R.color.gray_eee)
                        .sizeResId(R.dimen.dp_1)
                        .build());
                mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
                mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        DeviceUtils.hideSoftKeyboard(getContext(), searchText);
                        BaseSearchPopupBean item = (BaseSearchPopupBean) adapter.getItem(position);
                        popupClickListener.onItemClick(item);
                        dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                if (needAddMore) {
                    mAdapter.setOnLoadMoreListener(() -> {
                        popupClickListener.onLoadMore(searchText.getText().toString());
                    }, recyclerView);
                }
            }
            if (isMore) {
                mAdapter.addData(data);
            } else {
                if (data.size() > SHOW_SEARCH_VIEW_NUMBER) {
                    searchLayout.setVisibility(View.VISIBLE);
                } else {
                    searchLayout.setVisibility(View.GONE);
                }
                searchText.setText("");
                mAdapter.setNewData((List<BaseSearchPopupBean>) data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPopupClickListener(PopupClickListener popupClickListener) {
        this.popupClickListener = popupClickListener;
    }

    public SearchPopup(Context context) {
        super(context);
        try {
            setPopupGravity(Gravity.BOTTOM);
            setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent48));
            setOutSideDismiss(true);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            recyclerView = findViewById(R.id.recycler_view);
            searchBtn = findViewById(R.id.search_btn);
            searchText = findViewById(R.id.search_text);
            searchLayout = findViewById(R.id.search_layout);
            searchBtn.setOnClickListener(v -> {
                String st = searchText.getText().toString();
                DeviceUtils.hideSoftKeyboard(getContext(), searchText);
                popupClickListener.onSearchBtnClick(st);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_search_layout);
    }
}
