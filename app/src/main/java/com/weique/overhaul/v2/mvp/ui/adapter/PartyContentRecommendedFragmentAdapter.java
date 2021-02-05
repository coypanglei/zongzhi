package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.PartyCenterRecommendedBean;

import java.util.List;

/**
 * @author GK
 */
public class PartyContentRecommendedFragmentAdapter extends BaseMultiItemQuickAdapter<PartyCenterRecommendedBean.PartyBaseBean, BaseViewHolder> {

    /**
     * 党建中心首页 下列表 中的大标题布局表示 @ R.layout.item_party_main
     */
    public static final int PARTY_MAIN_ITEM_ONE = 1;
    /**
     * 党建中心首页 下列表 中的内容item局表示 @ R.layout.item_party_second
     */
    public static final int PARTY_MAIN_ITEM_SECOND = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public PartyContentRecommendedFragmentAdapter(List<PartyCenterRecommendedBean.PartyBaseBean> data) {
        super(data);
        addItemType(PARTY_MAIN_ITEM_ONE, R.layout.item_party_main);
        addItemType(PARTY_MAIN_ITEM_SECOND, R.layout.item_party_second);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, PartyCenterRecommendedBean.PartyBaseBean item) {
        try {
            switch (helper.getItemViewType()) {
                case PARTY_MAIN_ITEM_ONE:
                    helper.addOnClickListener(R.id.more_layout);
                    helper.setText(R.id.title, (item.getTitleName()));
                    break;
                case PARTY_MAIN_ITEM_SECOND:
                    helper.addOnClickListener(R.id.front_layout);
                    ImageView pic = helper.getView(R.id.pic);
                    if (StringUtil.isNotNullString(item.getCoverPicturePath())) {
                        pic.setVisibility(View.VISIBLE);
                        GlideUtil.loadImage(mContext, item.getCoverPicturePath(), pic);
                    } else {
                        pic.setVisibility(View.GONE);
                    }
                    TextView itemTitle = helper.getView(R.id.item_title);
                    itemTitle.setText((item.getTitle()));
//                    itemTitle.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "ttf/SourceHanSerifCN-Light.ttf"));
                    helper.setText(R.id.author, (item.getAuthor()));
                    helper.setText(R.id.time, (item.getRelease()));
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
