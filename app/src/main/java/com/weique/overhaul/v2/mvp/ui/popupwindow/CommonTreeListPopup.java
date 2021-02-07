package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.TreeDataInterface;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonTreeListAdapter;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;
import timber.log.Timber;

/**
 * @author : GreatKing
 * e-mail : 435136054@163.com
 * @date : 2020/4/30  9:49
 * desc   : 通用 树形 列表popup
 * version:
 */
public class CommonTreeListPopup<T extends TreeDataInterface<T>> extends BasePopupWindow {
    private RecyclerView recyclerView;
    private LinearLayout searchLayout;
    private EditText searchContent;
    private ImageView close;
    private CommonTreeListAdapter<T> mAdapter;
    private int mPosition;
    private CommonTreeListPopupListener<T> listener;
    private boolean allOk = false;

    /**
     * 监听
     */
    public interface CommonTreeListPopupListener<T extends TreeDataInterface<T>> {
        /**
         * 点击item  获取 子集数据
         *
         * @param elementId elementId
         */
        void onItemClickGetSubset(String elementId);

        /**
         * 点击选择
         *
         * @param da
         */
        void onItemClickInput(T da);

        /**
         * 搜索 根据关键字
         *
         * @param keyWord keyWord
         */
        void onSearchByKeyword(String keyWord);

        /**
         * 加载更多
         */
        void onLoadMore();
    }

    public void setListener(CommonTreeListPopupListener<T> listener) {
        try {
            this.listener = listener;
            if (isShowing()) {
                searchContent.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if (StringUtil.isNullString(searchContent.getText().toString())) {
                            ArmsUtils.makeText("请输入检索内容");
                            return false;
                        }
                        listener.onSearchByKeyword(searchContent.getText().toString());
                    }
                    return false;
                });
                mAdapter.setOnLoadMoreListener(() -> {
                    listener.onLoadMore();
                }, recyclerView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommonTreeListPopup(Context context) {
        super(context);
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommonTreeListPopup(Context context, boolean allOk) {
        super(context);
        try {
            this.allOk = allOk;
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() throws Exception {
        setPopupGravity(Gravity.BOTTOM);
        setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent48));
        setOutSideDismiss(true);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        recyclerView = findViewById(R.id.recycler_view);
        searchLayout = findViewById(R.id.search_layout);
        searchContent = findViewById(R.id.search_content);
        close = findViewById(R.id.close);
        close.setOnClickListener(v -> {
            dismiss();
        });
    }

    /**
     * 设置数据
     *
     * @param data data
     */
    public void setData(List<T> data, boolean isLoadMore) {
        try {
            if (mAdapter == null) {
                mAdapter = new CommonTreeListAdapter<>(data, allOk);
                mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(mAdapter);
                mAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
                mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        T item = (T) adapter.getItem(position);
                        if (item == null) {
                            ArmsUtils.makeText("获取信息失败");
                            return;
                        }
                        if (view.getId() == R.id.input) {
                            //是否所有item 都可以点击
                            if (allOk) {
                                listener.onItemClickInput(item);
                            } else {
                                if (item.isLeaf()) {
                                    listener.onItemClickInput(item);
                                }
                            }

                        } else if (view.getId() == R.id.arrow_icon
                                || view.getId() == R.id.all_item) {
                            if (item.isExpand()) {
                                adapter.collapse(position, true);
                                mAdapter.notifyItemChanged(position);
                            } else {
                                if (!item.isLeaf()) {
                                    mPosition = position;
                                    if (item.getList() == null || item.getList().size() <= 0) {
                                        listener.onItemClickGetSubset(item.getId());
                                    } else {
                                        adapter.expand(mPosition);
                                        notifyAndScroll(position);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            if (isLoadMore) {
                mAdapter.addData(data);
            } else {
                mAdapter.setNewData(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通知adapter 展开  改变  箭头  个 滚动recycler view
     *
     * @param position position
     */
    private void notifyAndScroll(int position) {
        try {
            mAdapter.expand(position);
            mAdapter.notifyItemChanged(position);
            LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
            llm.scrollToPositionWithOffset(position, 0);
            llm.setStackFromEnd(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置子集
     *
     * @param dataTrees dataTrees
     */
    public void setDataTree(List<T> dataTrees) {
        try {
            T item = mAdapter.getItem(mPosition);
            if (item == null) {
                Timber.i("setDataTree mAdapter.getItem is null");
                return;
            }
            item.setList(dataTrees);
            mAdapter.expand(mPosition);
            mAdapter.notifyItemChanged(mPosition);
            notifyAndScroll(mPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_elastic_list);
    }
}
