package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.ui.adapter.TreeListAdapter;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author : GreatKing
 * e-mail : 435136054@163.com
 * @date : 2020/4/30  9:49
 * desc   :
 * version:
 */
public class ElasticListPopup extends BasePopupWindow {
    private RecyclerView recyclerView;
    private TreeListAdapter treeListAdapter;
    private int mPosition;
    private ElasticListPopupListener listener;

    /**
     * 监听
     */
    public interface ElasticListPopupListener {
        /**
         * 点击item  获取 子集数据
         *
         * @param elementId elementId
         */
        void onItemClickGetSubset(String elementId);

        /**
         * onInputClick
         *
         * @param elementId   elementId
         * @param elementName elementName
         */
        void onInputClick(String elementId, String elementName, String dataStructureInJson);
    }

    public void setListener(ElasticListPopupListener listener) {
        this.listener = listener;
    }

    public ElasticListPopup(Context context) {
        super(context);
        try {
            setPopupGravity(Gravity.BOTTOM);
            setBackgroundColor(ArmsUtils.getColor(getContext(), R.color.transparent48));
            setOutSideDismiss(true);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            recyclerView = findViewById(R.id.recycler_view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(List<InformationTypeOneSecondBean.ElementTypeListBean> data) {
        try {
            if (treeListAdapter == null) {
                treeListAdapter = new TreeListAdapter(data);
                treeListAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                ArmsUtils.configRecyclerView(recyclerView, new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(treeListAdapter);
                treeListAdapter.setEmptyView(R.layout.null_content_layout, recyclerView);
                treeListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    try {
                        if (AppUtils.isFastClick()) {
                            return;
                        }
                        if (adapter.getItem(position) instanceof InformationTypeOneSecondBean.ElementTypeListBean) {
                            InformationTypeOneSecondBean.ElementTypeListBean item = (InformationTypeOneSecondBean.ElementTypeListBean) adapter.getItem(position);
                            if (item == null) {
                                ArmsUtils.makeText("获取信息失败");
                                return;
                            }
                            if (view.getId() == R.id.input) {
                                if (item.isLeaf()) {
                                    listener.onInputClick(item.getId(), item.getName(), item.getDataStructureInJson());
                                }
                            } else {
                                if (view.getId() == R.id.all_item) {
                                    if (item.isExpanded()) {
                                        adapter.collapse(position, true);
                                        treeListAdapter.getItem(position).setExpand(false);
                                        treeListAdapter.notifyItemChanged(position);
                                    } else {
                                        if (!item.isLeaf()) {
                                            mPosition = position;
                                            if (item.getList() == null || item.getList().size() <= 0) {
                                                listener.onItemClickGetSubset(item.getId());
                                            } else {
                                                notifyAndScroll(position);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }else{
                treeListAdapter.setNewData(data);
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
        treeListAdapter.expand(position);
        treeListAdapter.getItem(position).setExpand(true);
        treeListAdapter.notifyItemChanged(position);
        LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        llm.scrollToPositionWithOffset(position, 0);
        llm.setStackFromEnd(false);
    }

    public void setDataTree(List<InformationTypeOneSecondBean.ElementTypeListBean> dataTrees) {
        try {
            InformationTypeOneSecondBean.ElementTypeListBean item = treeListAdapter.getItem(mPosition);
            item.setList(dataTrees);
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
