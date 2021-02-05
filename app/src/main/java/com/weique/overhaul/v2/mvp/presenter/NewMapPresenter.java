package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.NewMapContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description: 新地图 界面
 * ================================================
 *
 * @author GreatKing
 */
@ActivityScope
public class NewMapPresenter extends ReworkBasePresenter<NewMapContract.Model, NewMapContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public NewMapPresenter(NewMapContract.Model model, NewMapContract.View rootView) {
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
     * 根据departmentId 获取 其区域信息
     *
     * @param departmentId departmentId
     */
    public void getDepartmentArea(String departmentId) {
        commonGetData(mModel.getAreaMapByDepartmentId(departmentId), mErrorHandler,
                gridInformationBean -> {
                    if (gridInformationBean == null || StringUtil.isNullString(gridInformationBean.getPointsInJSON())) {
                        ArmsUtils.makeText("获取用户区域信息失败");
                        return;
                    }
                    mRootView.showArea(gridInformationBean.getPointsInJSON());
                });
    }

    public void getLocationPermission() {
        CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
            try {
                if (AppUtils.isLocationEnabled(mRootView.getActivity())) {
                    mRootView.startDrawMap();
                } else {
                    mRootView.showOpenGpsDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Constant.PERMISSIONS_LOCATION);
    }

}
