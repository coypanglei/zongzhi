package com.weique.overhaul.v2.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jess.arms.http.imageloader.glide.BlurTransformation;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.common.ACacheConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.mvp.model.api.Api;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GlideUtil {
    public static final String SVG = "svg";

    /**
     * 默认加载方式
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        try {
            if (StringUtil.isNullString(url)) {
                return;
            }
            url = handleUrl(context, url);
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.null_content)
                    .error(R.drawable.null_content)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate();
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 默认加载方式
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView, @DrawableRes int resourceId) {
        try {
            if (StringUtil.isNullString(url)) {
                return;
            }
            url = handleUrl(context, url);
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
//                    .placeholder(resourceId)
                    .error(resourceId)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate();
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageForPath(Context context, Object path, ImageView imageView) {
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

    /**
     * 默认加载方式
     *
     * @param context
     * @param imageView
     */
    public static void loadBitmap(Context context, Bitmap bitmap, ImageView imageView) {
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    .placeholder(R.drawable.null_content)
                    .error(R.drawable.null_content)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontAnimate();

            Glide.with(context)
                    .load(bitmap)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 统一处理 url  t添加 http 协议地址  有缓存就取缓存
     *
     * @param context
     * @param url
     * @return
     */
    @NotNull
    public static String handleUrl(Context context, String url) {
        try {
            if (StringUtil.isNullString(url) ||
                    StringUtil.isNotNullString(url) && url.startsWith(Constant.HTTP)) {
                url = URLEncoder.encode(url, "utf-8")
                        .replaceAll(" ", "")
                        .replaceAll("%20", "")
                        .replaceAll("%5C", "/")
                        .replaceAll("\\\\", "/")
                        .replaceAll("%3A", ":")
                        .replaceAll("%2F", "/");
            } else {
                String asString = ACache.get(context).getAsString(ACacheConstant.DYNAMIC_URL);
                if (StringUtil.isNotNullString(asString)) {
                    if (url.startsWith("/")) {
                        url = asString + url;
                    } else {
                        url = asString + "/" + url;
                    }
                } else {
                    url = Api.APP_DOMAIN + url;
                }
            }
            url = URLEncoder.encode(url, "utf-8")
                    .replaceAll(" ", "")
                    .replaceAll("%20", "")
                    .replaceAll("%5C", "/")
                    .replaceAll("\\\\", "/")
                    .replaceAll("%3A", ":")
                    .replaceAll("%2F", "/");
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    @SuppressLint("CheckResult")
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        try {
            url = handleUrl(context, url);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.priority(Priority.HIGH);
            requestOptions.error(R.drawable.defult_heard_1);
            requestOptions.dontAnimate();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.circleCrop();
            RequestOptions.bitmapTransform(new CircleCrop());

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param radius    圆角大小
     */
    public static void loadRoundImage(Context context, String url, ImageView imageView, int radius) {
        try {
            url = handleUrl(context, url);
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    //                .placeholder(R.drawable.null_content)
                    .error(R.drawable.null_content)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transforms(new CenterCrop(), new RoundedCorners(radius));

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载图片指定大小
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void loadSizeImage(Context context, String url, ImageView imageView, int width, int height) {
        try {
            url = handleUrl(context, url);
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    //                .placeholder(R.drawable.null_content)
                    .error(R.drawable.null_content)
                    .override(width, height)
                    .diskCacheStrategy(DiskCacheStrategy.NONE);

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载本地图片文件
     *
     * @param context
     * @param file
     * @param imageView
     */
    public static void loadFileImage(Context context, File file, ImageView imageView) {
        try {
            RequestOptions requestOptions = new RequestOptions()
                    .priority(Priority.HIGH)
                    //                .placeholder(R.drawable.null_content)
                    .error(R.drawable.null_content)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop();


            Glide.with(context)
                    .load(file)
                    .apply(requestOptions)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载高斯模糊
     *
     * @param context
     * @param url
     * @param imageView
     * @param radius    模糊级数 最大25
     */
    public static void loadBlurImage(Context context, String url, ImageView imageView, int radius) {
        try {
            url = handleUrl(context, url);
            RequestOptions requestOptions = new RequestOptions()
                    .override(300)
                    //                .placeholder(R.drawable.null_content)
                    .error(R.drawable.null_content)
                    .transforms(new BlurTransformation(radius));

            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 加载gif图
     *
     * @param
     * @param url
     * @param imageView
     */
    public static void loadGifImage(Context context, String url, ImageView imageView) {
        try {
            url = handleUrl(context, url);
            Glide.with(context)
                    .load(url)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载gif图
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageWithOptions(Context context, String url, ImageView imageView, RequestOptions requestOptions) {
        try {
            url = handleUrl(context, url);
            Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getLoadVideoBitmap(Context context, String videoUrl, ImageView imageView) {
        Observable.create((ObservableOnSubscribe<Bitmap>) emitter -> {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                String newvideoUrl = handleUrl(context, videoUrl);
                Bitmap bitmap = null;
                //根据url获取缩略图
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    retriever.setDataSource(newvideoUrl, new HashMap<String, String>());
                } else {
                    retriever.setDataSource(newvideoUrl);
                }
                //获得第一帧图片
                bitmap = retriever.getFrameAtTime();
                emitter.onNext(bitmap);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                retriever.release();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> loadBitmap(context, bitmap, imageView));
    }

    /**
     * 清除图片磁盘缓存
     */
    private static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(() -> {
                    try {
                        Glide.get(context).clearDiskCache();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    private static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache(Context context) {
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalPreferredCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
