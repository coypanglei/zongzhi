package com.weique.overhaul.v2.mvp.ui.popupwindow;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.PopupWindow;

import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.customview.flowtag.FlowTagLayout;
import com.weique.overhaul.v2.app.customview.flowtag.OnTagSelectListener;
import com.weique.overhaul.v2.app.customview.flowtag.TagAdapter;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBean;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * @author GK
 */
public class KnowledgePopup extends BasePopupWindow implements View.OnClickListener {

    private FlowTagLayout mMobileFlowTagLayout;
    private Button Remove;
    private Button Sure;
    private List<KnowledgeBean >mList;
    private ListItemClickListener listItemClickListener;
    private String select;
    private String name;


    /**
     * reyclerview  点击回调
     */
    public interface ListItemClickListener {
        /**
         * 点击
         *
         * @param pos pos
         */
        void onItemClick(String pos,String name);
    }

    public ListItemClickListener getListItemClickListener() {
        return listItemClickListener;
    }

    public void setListItemClickListener(ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }


    private TagAdapter<KnowledgeBean> mMobileTagAdapter;

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.knowledge_popup);
    }

    public KnowledgePopup(Context context,List<KnowledgeBean>list) {
        super(context);
        try {
            mList = list;
            setPopupGravity(Gravity.BOTTOM);
//        setBlurBackgroundEnable(true);
            setAlignBackground(true);
            setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        setOutSideTouchable(true);
//        setBackgroundColor(ArmsUtils.getResources(context).getColor(R.color.white));
            setOutSideDismiss(true);
            mMobileFlowTagLayout = findViewById(R.id.mobile_flow_layout);
            Remove = findViewById(R.id.remove);
            Sure = findViewById(R.id.sure);
            Remove.setOnClickListener(this);
            Sure.setOnClickListener(this);

            //移动研发标签
            mMobileTagAdapter = new TagAdapter<>(getContext());
            mMobileFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
            mMobileFlowTagLayout.setAdapter(mMobileTagAdapter);
            mMobileFlowTagLayout.setOnTagSelectListener(new OnTagSelectListener() {

                @Override
                public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                    try {
                        if (selectedList != null && selectedList.size() > 0) {
                            StringBuilder sb = new StringBuilder();
                            StringBuilder sb1 = new StringBuilder();

                            for (int i : selectedList) {
                                sb.append(((KnowledgeBean)parent.getAdapter().getItem(i)).getId());
                                sb.append(",");
                                sb1.append(((KnowledgeBean)parent.getAdapter().getItem(i)).getName());
                                sb1.append(",");
                            }
                            select = sb.substring(0,sb.length()-1);
                            name = sb1.substring(0,sb1.length()-1);
        //                    Snackbar.make(parent, select, Snackbar.LENGTH_LONG)
        //                            .setAction("Action", null).show();
                        } else {
        //                    Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
        //                            .setAction("Action", null).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mMobileTagAdapter.onlyAddAll(mList);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remove:
                mMobileFlowTagLayout.clearAllOption();
                mMobileTagAdapter.clearAndAddAll(mList);
                select = null;
                break;
            case R.id.sure:

                listItemClickListener.onItemClick(select,name);

                break;

            default:
        }
    }


}
