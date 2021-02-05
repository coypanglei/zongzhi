package com.weique.overhaul.v2.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppProgressListenerUtil;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.DownLoadUtil;
import com.weique.overhaul.v2.app.utils.GlideUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.LawWorksOrderDetailContract;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@ActivityScope
public class LawWorksOrderDetailPresenter extends ReworkBasePresenter<LawWorksOrderDetailContract.Model, LawWorksOrderDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    OkHttpClient okHttpClient;
    @Inject
    IRepositoryManager iRepositoryManager;
    @Inject
    public LawWorksOrderDetailPresenter(LawWorksOrderDetailContract.Model model, LawWorksOrderDetailContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    /**
     * 获取相机权限
     *
     * @param max
     */
    public void getPermission(int max) {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, () ->
                mRootView.goToPhotoAlbum(max), Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }

    /**
     * 上传文件
     *
     * @param compressPaths compressPath 文件压缩路径
     */
    public void upLoadFile(String elementId, List<String> compressPaths) {
        try {
            CommonNetworkRequest.upLoadFile(elementId, compressPaths,
                    mErrorHandler, mRootView, iRepositoryManager,
                    uploadFileRsponseBeans -> {
                        mRootView.updatePicture(uploadFileRsponseBeans);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getLawDetailListData(String id) {

        commonGetData(mModel.getLawDetailListData(id), mErrorHandler, itemBean -> {
            mRootView.setLawDetailListData(itemBean);
        });
    }

    /**
     * 接单
     *
     * @param id
     */
    public void getOrderData(String id) {
        commonGetData(mModel.getOrderData(id), mErrorHandler, itemBean -> {
            mRootView.setOrderData(itemBean);
        });
    }


    /**
     * 反馈
     */
    public void getOrderIntoData(String CourtOrderID, String Content, String ImgsInJson) {
        commonGetData(mModel.getOrderIntoData(CourtOrderID, Content, ImgsInJson), mErrorHandler, itemBean -> {
            mRootView.setOrderIntoData(itemBean);
        });
    }


    /**
     * @param url      文件url
     * @param fileName 文件名称
     */
    @SuppressLint("CheckResult")
    public void getFilePermission(String url, String fileName, int pos) {
        CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
            try {
                String url_ = GlideUtil.handleUrl(mRootView.getActivity(), url);
                if (StringUtil.isNullString(url_)) {
                    ArmsUtils.makeText("下载地址为空");
                    return;
                }
                AppUtils.checkUrl(url_)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean -> {
                            try {
                                if (aBoolean) {
                                    ProgressManager.getInstance().addResponseListener(url_, AppProgressListenerUtil.getDownloadListenerToNotif(pos, RouterHub.APP_LAWWORKSORDERDETAILACTIVITY));
                                    //下载文件
                                    DownLoadUtil.downloadStart(mRootView.getActivity(),
                                            GlideUtil.handleUrl(mRootView.getActivity(), url),
                                            okHttpClient, fileName);
                                } else {
                                    ArmsUtils.makeText("文件出现错误，无法下载");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Constant.READ_WRITE);
    }

}
