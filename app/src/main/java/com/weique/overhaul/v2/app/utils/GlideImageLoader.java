package com.weique.overhaul.v2.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.weique.overhaul.v2.R;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.null_content)
                    .error(R.drawable.null_content)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate();
            //Glide 加载图片简单用法
            Glide.with(context).load(path).apply(requestOptions).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
