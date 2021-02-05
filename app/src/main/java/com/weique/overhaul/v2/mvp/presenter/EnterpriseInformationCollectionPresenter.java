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
import com.weique.overhaul.v2.mvp.contract.EnterpriseInformationCollectionContract;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 09:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class EnterpriseInformationCollectionPresenter extends ReworkBasePresenter<EnterpriseInformationCollectionContract.Model, EnterpriseInformationCollectionContract.View> {
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
    public EnterpriseInformationCollectionPresenter(EnterpriseInformationCollectionContract.Model model, EnterpriseInformationCollectionContract.View rootView) {
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


    public void getPermission() {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, () -> mRootView.goToPhotoAlbum(), Constant.READ_WRITE_CAMERA);
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
                            mRootView.updatePicture(uploadFileRsponseBean.getUrl());
                        } else {
                            ArmsUtils.makeText("上传失败");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建企业采集数据
     */
    public void createData(Map map) {
        commonGetData(mModel.createEntInfo(map), mErrorHandler, bean -> {
            try {
                mRootView.submit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    /**
     * 获取下拉列表
     */
    public void getList() {
        commonGetData(mModel.getList(), mErrorHandler, bean -> {
            try {
                 mRootView.setListData(bean);


            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    /**
     * 获取行业列表
     */
    public void getEnterpriseTypes() {
        commonGetData(mModel.getEnterpriseType(), mErrorHandler, bean -> {
            try {
                mRootView.getEnterpriseType(bean);


            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }



}
