package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.BusinessVisitContract;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 14:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BusinessVisitPresenter extends ReworkBasePresenter<BusinessVisitContract.Model, BusinessVisitContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    IRepositoryManager iRepositoryManager;
    @Inject
    public BusinessVisitPresenter(BusinessVisitContract.Model model, BusinessVisitContract.View rootView) {
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

    public void getPermission(int max) {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, () -> {
            mRootView.goToPhotoAlbum(max);
        }, Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
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

    /**
     * submit
     *
     * @param id            企业id
     * @param time          时间
     * @param issueDescribe 问题描述
     * @param feedback      问题回馈
     */
    public void submit(String id, String time, String issueDescribe, String feedback, List<String> images) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("EnterpriseInfId", id);
            map.put("VisitDateTime", time);
            map.put("TroubleOrProblem", issueDescribe);
            map.put("Suggestion", feedback);
            map.put("CreateUserId", UserInfoUtil.getUserInfo().getUid());
            map.put("UpdateUserId", UserInfoUtil.getUserInfo().getUid());
            if (images != null && images.size() > 0) {
                map.put("Photos", new Gson().toJson(images));
            }
            commonGetData(mModel.submit(map), mErrorHandler, o -> {
                mRootView.killMyself();
                ARouter.getInstance().build(RouterHub.APP_BUSINESSINTERVIEWLISTACTIVITY)
                        .navigation();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
