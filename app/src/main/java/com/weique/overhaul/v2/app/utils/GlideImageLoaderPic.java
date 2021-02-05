package com.weique.overhaul.v2.app.utils;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.weique.overhaul.v2.R;

import java.io.File;

public class GlideImageLoaderPic implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

        try {
            Glide.with(activity)                             //配置上下文
                    .load(Uri.fromFile(new File(path)))     //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(R.drawable.ic_default_image)           //设置错误图片
                    .placeholder(R.drawable.ic_default_image)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//缓存全尺寸
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        try {
            Glide.with(activity)                             //配置上下文
                    .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)//缓存全尺寸
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearMemoryCache() {
    }
}
