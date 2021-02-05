package com.weique.overhaul.v2.app.utils;

import android.content.Context;

import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.jessyan.progressmanager.ProgressManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author GreatKing
 */
public class DownLoadUtil {

    /**
     * todo  后面优化 下载速度  起线程池  下载
     *
     * @param context            context
     * @param url                url
     * @param mOkHttpClient      mOkHttpClient
     * @param newVersionInfoBean newVersionInfoBean
     */
    public static void downloadStart(Context context, String url, OkHttpClient mOkHttpClient, NewVersionInfoBean newVersionInfoBean) {
        try {
            File file = Constant.getNewVersionApkFile(newVersionInfoBean.getVersionName());
            if (file.exists()) {
                file.delete();
            }
            String finalUrl = url;
            new Thread(() -> {
                try {
                    Request request = new Request.Builder()
                            .url(finalUrl)
                            .build();
                    Response response = mOkHttpClient.newCall(request).execute();
                    if (response.code() == Constant.HTTP200) {
                        InputStream is = response.body().byteStream();
                        //为了方便就不动态申请权限了,直接将文件放到CacheDir()中
                        FileOutputStream fos = new FileOutputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();
                        fos.close();
                        bis.close();
                        is.close();
                    } else {
                        ArmsUtils.makeText("下载地址无法访问");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法
                    ProgressManager.getInstance().notifyOnErorr(finalUrl, e);
                } catch (Exception e) {
                    e.printStackTrace();
                    //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法
                    ProgressManager.getInstance().notifyOnErorr(finalUrl, e);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * todo  后面优化 下载速度  起线程池  下载
     *
     * @param context       context
     * @param url           url
     * @param mOkHttpClient mOkHttpClient
     * @param fileName      fileName
     */
    public static void downloadStart(Context context, String url, OkHttpClient mOkHttpClient, String fileName) {
        try {
            File file = Constant.getDownloadFile(fileName);
            /*if (file.exists()) {
                new CommonDialog.Builder(context)
                        .setContent("文件已经下载完成，请到文件管理中查看")
                        .setPositiveButton("确定", (v, commonDialog) -> {
                            //todo 打开文件
                            FileUtil.openFile(context, file);
                        }).create().show();
                return;
            }*/
            String finalUrl = url;
            new Thread(() -> {
                try {
                    Request request = new Request.Builder()
                            .url(finalUrl)
                            .build();
                    Response response = mOkHttpClient.newCall(request).execute();
                    if (response.code() == Constant.HTTP200) {
                        InputStream is = response.body().byteStream();
                        //为了方便就不动态申请权限了,直接将文件放到CacheDir()中
                        FileOutputStream fos = new FileOutputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                        }
                        fos.flush();
                        fos.close();
                        bis.close();
                        is.close();
                    } else {
                        ArmsUtils.makeText("下载地址无法访问");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法
                    ProgressManager.getInstance().notifyOnErorr(finalUrl, e);
                } catch (Exception e) {
                    e.printStackTrace();
                    //当外部发生错误时,使用此方法可以通知所有监听器的 onError 方法
                    ProgressManager.getInstance().notifyOnErorr(finalUrl, e);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
