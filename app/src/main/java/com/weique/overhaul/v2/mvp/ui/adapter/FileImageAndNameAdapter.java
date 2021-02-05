package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.FileImageAndNameBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/8/11 15:02
 */
public class FileImageAndNameAdapter<T extends FileImageAndNameBean> extends BaseQuickAdapter<T, BaseViewHolder> {
    private boolean showRemoveImage = true;

    public FileImageAndNameAdapter() {
        super(R.layout.item_image_name);
    }

    public void setShowRemoveImage(boolean showRemoveImage) {
        this.showRemoveImage = showRemoveImage;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, T item) {
        try {
            if (StringUtil.isNotNullString(item.getUrl())) {
//                GlideUtil.loadImage(mContext, StringUtil.setText(item.getUrl()), helper.getView(R.id.image_));
            }
            helper.setText(R.id.name, item.getName());
            RelativeLayout imgeMove = helper.getView(R.id.remove_image);
            if (showRemoveImage) {
                imgeMove.setVisibility(View.VISIBLE);
            } else {
                imgeMove.setVisibility(View.GONE);
            }
            helper.addOnClickListener(R.id.remove_image, R.id.all_item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getDataUrlString() {
        List<String> list = new ArrayList<>();
        List<T> data = getData();
        for (T datum : data) {
            list.add(datum.getUrl());
        }
        return list;
    }
}
