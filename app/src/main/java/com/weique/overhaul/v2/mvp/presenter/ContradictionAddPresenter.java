package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.ContradictionAddContract;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionAddBean;

import java.io.File;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2020 10:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ContradictionAddPresenter extends ReworkBasePresenter<ContradictionAddContract.Model, ContradictionAddContract.View> {
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
    public ContradictionAddPresenter(ContradictionAddContract.Model model, ContradictionAddContract.View rootView) {
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


    public void setContradiction(ContradictionAddBean contradictionAddBean) {

        commonGetData(mModel.setContradiction(contradictionAddBean), mErrorHandler, itemBean -> {
            mRootView.setContradictionData(itemBean);
        });
    }

    public void getContradictionRecord(String id) {
        commonGetData(mModel.getContradictionRecord(id), mErrorHandler, contradictionRecordBean -> {
            mRootView.getContradictionRecord(contradictionRecordBean);
        });
    }

    public void DeleteContradictionRecord(String resetId) {
        commonGetData(mModel.DeleteContradictionRecord(resetId), mErrorHandler, contradictionRecordBean -> {
            mRootView.isContradictionRecordDelete(contradictionRecordBean);
        });
    }

    public void InvalidContradictionRecord(String resetId) {
        commonGetData(mModel.InvalidContradictionRecord(resetId), mErrorHandler, contradictionRecordBean -> {
            mRootView.isInvalidContradictionRecord(contradictionRecordBean);
        });
    }

    public void setResetContradiction(ContradictionAddBean stagingBean) {
        commonGetData(mModel.setResetContradiction(stagingBean), mErrorHandler, contradictionRecordBean -> {
            mRootView.stagingReset(contradictionRecordBean);
        });
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
}
