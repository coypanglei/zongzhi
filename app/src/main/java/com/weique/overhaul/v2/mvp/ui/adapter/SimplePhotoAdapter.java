package com.weique.overhaul.v2.mvp.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.GlideUtil;

import java.util.List;

public class SimplePhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SimplePhotoAdapter(@Nullable List<String> data) {
        super(R.layout.item_simple_photo, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        try {
            ImageView imageView = helper.getView(R.id.image);
            if (item.endsWith(Constant.PNG)
                    || item.endsWith(Constant.JPG)
                    || item.endsWith(Constant.BMP)
                    || item.endsWith(Constant.JPEG)) {
                GlideUtil.loadImage(mContext, (item), imageView);
            } else {
                imageView.setImageResource(R.drawable.file_icon_88);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
