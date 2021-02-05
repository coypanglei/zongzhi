package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.SearchShowListBean;
import com.weique.overhaul.v2.mvp.ui.adapter.SearchShowListAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/8/11 18:32
 */
public class SearchShowToListDialog<T extends SearchShowListBean> extends Dialog {
    private Context mContext;
    private EditText editText;
    private RecyclerView resultList;
    private SearchShowListAdapter<T> mAdapter;

    public SearchShowToListDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.mContext = context;
        initView();
    }

    public interface OnSearchShowToListListener {
        /**
         * item点击
         *
         * @param adapter  adapter
         * @param view     view
         * @param position position
         */
        void onItemClick(BaseQuickAdapter adapter, View view, int position);

        /**
         * 搜索点击
         *
         * @param text text
         */
        void onSearch(String text);

        /**
         * 加载更多
         */
        void onLoadMore();
    }

    public void setListener(OnSearchShowToListListener listener) {
        try {
            if (mAdapter != null) {
                mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    if (AppUtils.isFastClick()) {
                        return;
                    }
                    listener.onItemClick(adapter, view, position);
                    dismiss();
                });
                mAdapter.setOnLoadMoreListener(listener::onLoadMore, resultList);
            }
            editText.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    listener.onSearch(editText.getText().toString());
                }
                return false;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        try {
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_search_show_list, null);
            setContentView(view);
            //alertDialog是否可以点击外围消失
            setCanceledOnTouchOutside(false);
            setCancelable(true);
            editText = view.findViewById(R.id.search_content);

            resultList = view.findViewById(R.id.result_list);
            ImageView close = view.findViewById(R.id.close);
            close.setOnClickListener(v -> dismiss());
            mAdapter = new SearchShowListAdapter<T>();
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            ArmsUtils.configRecyclerView(resultList, new LinearLayoutManager(mContext));
            resultList.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mContext)
                    .colorResId(R.color.gray_eee)
                    .sizeResId(R.dimen.dp_1)
                    .build());
            mAdapter.setEmptyView(R.layout.null_content_home_layout, resultList);
            resultList.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        editText.setText("");
        super.show();
    }

    public void setNewData(List<T> data, boolean isLoadMore) {
        if (isLoadMore) {
            mAdapter.addData(data);
        } else {
            mAdapter.setNewData(data);
        }
        //20是每页几条  先写死
        if (data.size() < 20) {
            mAdapter.loadMoreEnd(true);
        }
    }
}
