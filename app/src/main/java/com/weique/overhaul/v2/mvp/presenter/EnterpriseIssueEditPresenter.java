package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.EventBusConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueEditContract;
import com.weique.overhaul.v2.mvp.model.entity.event.EventBusBean;

import org.simple.eventbus.EventBus;

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
 * ================================================
 */
@ActivityScope
public class EnterpriseIssueEditPresenter extends ReworkBasePresenter<EnterpriseIssueEditContract.Model, EnterpriseIssueEditContract.View> {
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
    public EnterpriseIssueEditPresenter(EnterpriseIssueEditContract.Model model, EnterpriseIssueEditContract.View rootView) {
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
     * 企业获取上报问题类型列表
     */
    public void getIssueTypeList() {
        commonGetData(mModel.getIssueTypeList(), mErrorHandler, o -> {
            try {
                if (o == null || o.size() <= 0) {
                    ArmsUtils.makeText("未获取到数据");
                    return;
                }
                mRootView.setIssueTypeData(o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 提交企业问题
     *
     * @param issueType 问题类型
     * @param content   问题描述
     */
    public void submitInfo(int issueType, String content, List<String> images) {
        Map<String, Object> map = new HashMap<>();
        map.put("EnumTroubleType", issueType);
        map.put("Desc", content);
        map.put("CreateUserId", UserInfoUtil.getUserInfo().getUid());
        map.put("UpdateUserId", UserInfoUtil.getUserInfo().getUid());
        if (images != null && images.size() > 0) {
            map.put("Photo", new Gson().toJson(images));
        }
        commonGetData(mModel.submitInfo(map), mErrorHandler, t -> {
            mRootView.killMyself();
            EventBus.getDefault().post(new EventBusBean(EventBusConstant.COMMON_UPDATE), RouterHub.APP_ENTERPRISEISSUELISTACTIVITY);
        });
    }


    /**
     * 获取相机权限
     *
     * @param max
     */
    public void getPermission(int max) {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, new CommonPermissionUtil.PermissionLisenter() {
            @Override
            public void getPermissionSuccess() {
                mRootView.goToPhotoAlbum(max);
            }
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
}
