package com.weique.overhaul.v2.mvp.ui.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GreatKing
 */
public class InformationAddPhotoAdapter extends BaseQuickAdapter<InformationItemPictureBean, BaseViewHolder> {
    /**
     * 最大 图片数量 默认值是2  加上默认的添加图片
     */
    private int maxItem = 1;

    public InformationAddPhotoAdapter(List<InformationItemPictureBean> list) {
        super(R.layout.item_image, list);
    }

    public InformationAddPhotoAdapter() {
        super(R.layout.item_image);
    }

    /**
     * @param maxItem 最大 图片数量
     */
    public void setMaxItem(int maxItem) {
        this.maxItem = maxItem;
    }

    public int getMaxItem() {
        return maxItem;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, InformationItemPictureBean item) {
        try {
            if (item.getType() == InformationItemPictureBean.IS_URL) {
                if (item.getImageUrl().endsWith(Constant.MP4)) {
                    helper.setGone(R.id.play_icon, true);
                    GlideUtil.getLoadVideoBitmap(mContext, StringUtil.setText(item.getImageUrl()), helper.getView(R.id.image_));
                } else {
                    helper.setGone(R.id.play_icon, false);
                    GlideUtil.loadImage(mContext, StringUtil.setText(item.getImageUrl()), helper.getView(R.id.image_));
                }
                helper.setVisible(R.id.remove_image, true);
            } else if (item.getType() == InformationItemPictureBean.IS_VIS_DELETE) {
                if (item.getImageUrl().endsWith(Constant.MP4)) {
                    helper.setGone(R.id.play_icon, true);
                    GlideUtil.getLoadVideoBitmap(mContext, StringUtil.setText(item.getImageUrl()), helper.getView(R.id.image_));
                } else {
                    helper.setGone(R.id.play_icon, false);
                    GlideUtil.loadImage(mContext, StringUtil.setText(item.getImageUrl()), helper.getView(R.id.image_));
                }
                helper.setVisible(R.id.remove_image, false);
            } else {
                helper.setImageResource(R.id.image_, item.getDrawableIntRes());
                helper.setVisible(R.id.remove_image, false);
            }
            helper.addOnClickListener(R.id.image_);
            helper.addOnClickListener(R.id.remove_image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片集合list
     *
     * @return String
     */
    public String getPicPathsJson() {
        List<String> jsons = new ArrayList<>();
        List<InformationItemPictureBean> data = getData();
        for (InformationItemPictureBean datum : data) {
            if (datum.getType() != InformationItemPictureBean.IS_DRAWABLE) {
                jsons.add(datum.getImageUrl());
            }
        }
        return new Gson().toJson(jsons);
    }
}
