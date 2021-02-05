package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.MyInfoContract;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

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
 * Created by MVPArmsTemplate on 10/15/2019 14:50
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MyInfoPresenter extends ReworkBasePresenter<MyInfoContract.Model, MyInfoContract.View> {
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
    public MyInfoPresenter(MyInfoContract.Model model, MyInfoContract.View rootView) {
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

    public void getUserInfoDetail() {
        commonGetData(mModel.getUserInfoDetail(), mErrorHandler, myInfoBean -> {
            if (myInfoBean != null) {
                //获取个人信息中没有 token  这里设置一下
                myInfoBean.setToken(UserInfoUtil.getUserInfo().getToken());
                mRootView.setUserInfo(myInfoBean);
            } else {
                ArmsUtils.makeText("");
            }
        });
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
                        if (uploadFileRsponseBeans != null && uploadFileRsponseBeans.size() > 0) {
                            UploadFileRsponseBean uploadFileRsponseBean = uploadFileRsponseBeans.get(0);
                            if (StringUtil.isNullString(uploadFileRsponseBean.getUrl())) {
                                ArmsUtils.makeText("头像地址为空");
                                return;
                            }
                            updateUserPicture(uploadFileRsponseBean.getUrl());
                        } else {
                            ArmsUtils.makeText("上传失败");
                        }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserPicture(String url) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("HeadUrl", url);
            commonGetData(mModel.updateUserPicture(map), mErrorHandler, o -> {
                mRootView.updatePicture(url);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPermission() {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, () -> mRootView.goToPhotoAlbum(), Constant.READ_WRITE_CAMERA);
    }

    public void saveUserInfo(GlobalUserInfoBean mMyInfoBeanChanged) {
        commonGetData(mModel.saveUserInfo(mMyInfoBeanChanged), mErrorHandler, o -> {
            mRootView.setSaveSuccess();
        });
    }
}
