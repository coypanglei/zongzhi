package com.weique.overhaul.v2.app.common;

import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.NetworkUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.model.api.service.CommonRequestServer;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author GK
 * @description: 所有公共网络请求写在这里
 * @date :2020/8/16 10:26
 */
public class CommonNetworkRequest {

    /**
     * @param elementId          elementId
     * @param compressPaths      文件路径
     * @param mErrorHandler      mErrorHandler 错误回调
     * @param mRootView          mRootView
     * @param mRepositoryManager mRepositoryManager
     * @param handle             handle
     */
    public static void upLoadFile(String elementId,
                                  List<String> compressPaths,
                                  RxErrorHandler mErrorHandler,
                                  IView mRootView,
                                  IRepositoryManager mRepositoryManager,
                                  @Nullable ReworkBasePresenter.ProgressMonitorHandle<List<UploadFileRsponseBean>> handle) {
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("BusinessId", StringUtil.setText(elementId))
                    .addFormDataPart(Constant.USER_ID, StringUtil.setText(UserInfoUtil.getUserInfo().getUid()));
            for (String s : compressPaths) {
                File file = new File(s);
                String parse = "";
                if (s.endsWith(Constant.PNG)) {
                    parse = "image/png";
                } else if (s.endsWith(Constant.JPG)) {
                    parse = "image/JPG";
                } else if (s.endsWith(Constant.JPEG)) {
                    parse = "image/jpeg";
                } else {
                    parse = "application/octet-stream";
                }
                RequestBody requestFile = RequestBody.create(MediaType.parse(parse), file);
                builder.addFormDataPart("file", file.getName(), requestFile);
            }
            Observable<BaseBean<List<UploadFileRsponseBean>>> baseBeanObservable = mRepositoryManager.
                    obtainRetrofitService(MainService.class).
                    UploadFileForApp(builder.build());
            NetworkUtils.commonGetData(baseBeanObservable, mErrorHandler, mRootView, handle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param elementId          elementId
     * @param s                  文件路径
     * @param mErrorHandler      mErrorHandler 错误回调
     * @param mRootView          mRootView
     * @param mRepositoryManager mRepositoryManager
     * @param handle             handle
     */
    public static void upLoadFile(String elementId,
                                  String s,
                                  RxErrorHandler mErrorHandler,
                                  IView mRootView,
                                  IRepositoryManager mRepositoryManager,
                                  @Nullable ReworkBasePresenter.ProgressMonitorHandle<List<UploadFileRsponseBean>> handle) {
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("BusinessId", StringUtil.setText(elementId))
                    .addFormDataPart(Constant.USER_ID, StringUtil.setText(UserInfoUtil.getUserInfo().getUid()));
            File file = new File(s);
            String parse = "";
            if (s.endsWith(Constant.PNG)) {
                parse = "image/png";
            } else if (s.endsWith(Constant.JPG)) {
                parse = "image/JPG";
            } else if (s.endsWith(Constant.JPEG)) {
                parse = "image/jpeg";
            } else {
                parse = "application/octet-stream";
            }
            RequestBody requestFile = RequestBody.create(MediaType.parse(parse), file);
            builder.addFormDataPart("file", file.getName(), requestFile);
            Observable<BaseBean<List<UploadFileRsponseBean>>> baseBeanObservable = mRepositoryManager.
                    obtainRetrofitService(MainService.class).
                    UploadFileForApp(builder.build());
            NetworkUtils.commonGetData(baseBeanObservable, mErrorHandler, mRootView, handle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param elementId          elementId
     * @param file               文件路径
     * @param mErrorHandler      mErrorHandler 错误回调
     * @param mRootView          mRootView
     * @param mRepositoryManager mRepositoryManager
     * @param handle             handle
     */
    public static void upLoadFile(String elementId,
                                  File file,
                                  RxErrorHandler mErrorHandler,
                                  IView mRootView,
                                  IRepositoryManager mRepositoryManager,
                                  @Nullable ReworkBasePresenter.ProgressMonitorHandle<List<UploadFileRsponseBean>> handle) {
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("BusinessId", StringUtil.setText(elementId))
                    .addFormDataPart(Constant.USER_ID, StringUtil.setText(UserInfoUtil.getUserInfo().getUid()));
            String parse = "";
//            if (file.getName().endsWith(Constant.PNG)) {
//                parse = "image/png";
//            } else if (file.getName().endsWith(Constant.JPG)) {
//                parse = "image/JPG";
//            } else if (file.getName().endsWith(Constant.JPEG)) {
//                parse = "image/jpeg";
//            } else {
                parse = "application/octet-stream";
//            }
            RequestBody requestFile = RequestBody.create(MediaType.parse(parse), file);
            builder.addFormDataPart("file", file.getName(), requestFile);
            Observable<BaseBean<List<UploadFileRsponseBean>>> baseBeanObservable = mRepositoryManager.
                    obtainRetrofitService(MainService.class).
                    UploadFileForApp(builder.build());
            NetworkUtils.commonGetData(baseBeanObservable, mErrorHandler, mRootView, handle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param map                公共请求
     * @param path               动态的url
     * @param mErrorHandler      mErrorHandler 错误处理
     * @param mRootView          mRootView Viw对象
     * @param mRepositoryManager mRepositoryManager 接口调用管理者
     * @param handle             handle 回调
     */
    public static void commonRequestOne(Map<String, Object> map,
                                            String path,
                                            RxErrorHandler mErrorHandler,
                                            IView mRootView,
                                            IRepositoryManager mRepositoryManager,
                                            @Nullable ReworkBasePresenter.ProgressMonitorHandle<NameAndIdBean> handle) {
        try {
            Observable<BaseBean<NameAndIdBean>> observable = mRepositoryManager
                    .obtainRetrofitService(CommonRequestServer.class)
                    .commonRequestGet(path, SignUtil.paramSign(map));
            NetworkUtils.commonGetData(observable, mErrorHandler, mRootView, handle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
