package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;
import android.net.Uri;
import android.os.Build;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.PictureSelectorUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.ResourceAuditEditContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/17/2020 16:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ResourceAuditEditPresenter extends ReworkBasePresenter<ResourceAuditEditContract.Model, ResourceAuditEditContract.View> {
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
    public ResourceAuditEditPresenter(ResourceAuditEditContract.Model model, ResourceAuditEditContract.View rootView) {
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

    public void getPermissionAndUpLoad(int max) {
        CommonPermissionUtil.getPermission(mRootView.getContext(), mErrorHandler, () -> {
            PictureSelectorUtils.gotoPhoto(mRootView.getContext(), PictureMimeType.ofImage(),
                    max, false, false, new OnResultCallbackListener<LocalMedia>() {
                        @Override
                        public void onResult(List<LocalMedia> result) {
                            try {
                                List<String> strings = new ArrayList<>();
                                for (LocalMedia media : result) {
                                    if (StringUtil.isNotNullString(media.getCompressPath())) {
                                        strings.add(media.getCompressPath());
                                    } else {
                                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                                            strings.add(PictureFileUtils.getPath(mRootView.getContext(), Uri.parse(media.getPath())));
                                        } else {
                                            if (StringUtil.isNotNullString(media.getPath()) && new File(media.getPath()).exists()) {
                                                strings.add(media.getPath());
                                            }
                                        }
                                    }
                                }
                                CommonNetworkRequest.upLoadFile("", strings,
                                        mErrorHandler, mRootView, iRepositoryManager,
                                        uploadFileRsponseBeans -> {
                                            mRootView.updatePictureResponse(uploadFileRsponseBeans);
                                        });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }, Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
    }

    public void resourceAudit(Map<String, Object> map) {
        Observable<BaseBean<Object>> baseBeanObservable = iRepositoryManager
                .obtainRetrofitService(InformationService.class)
                .resourceAudit(SignUtil.paramSign(map));
        commonGetData(baseBeanObservable, mErrorHandler, o -> {
            mRootView.killMyself();
        });
    }
}
