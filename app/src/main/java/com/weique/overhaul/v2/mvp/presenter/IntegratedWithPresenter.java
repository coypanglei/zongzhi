package com.weique.overhaul.v2.mvp.presenter;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.AppUtils;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.app.utils.PhoneInfoUtil;
import com.weique.overhaul.v2.mvp.contract.IntegratedWithContract;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 09:48
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class IntegratedWithPresenter extends BasePresenter<IntegratedWithContract.Model, IntegratedWithContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public IntegratedWithPresenter(IntegratedWithContract.Model model, IntegratedWithContract.View rootView) {
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
     * 获取 综合办理数据
     */
    public void getIntegratedWithInfo() {
//        List<IntegratedWithItemBean> itemBeans = new ArrayList<>();
//        IntegratedWithItemBean itemBean = null;
//        for (int i = 0; i < 3; i++) {
//            itemBean = new IntegratedWithItemBean();
//            itemBean.setLabel("标签组" + i);
//            List<HomeMenuItemBean> menuItemBeans = new ArrayList<>();
//            HomeMenuItemBean bean = null;
//            for (int j = 0; j < 6; j++) {
//                bean = new HomeMenuItemBean();
//                bean.setName("标签" + j);
//                menuItemBeans.add(bean);
//            }
//            itemBean.setHomeMenuItemBeans(menuItemBeans);
//            itemBeans.add(itemBean);
//        }
//        mRootView.updateListView(itemBeans);
    }

    /**
     * 获取相机权限
     */
    public void getPermissionCamera() {
        try {
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
                try {
                    ARouter.getInstance().build(RouterHub.APP_CAPTUREACTIVITY).navigation(mRootView.getActivity(), Constant.REQUEST_CODE_SCAN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, Manifest.permission.CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void getPermission() {
        try {
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, new CommonPermissionUtil.PermissionLisenter() {
                @Override
                public void getPermissionSuccess() {
                    try {
                        if (AppUtils.isLocationEnabled(mRootView.getActivity())) {
                            ARouter.getInstance().build(RouterHub.APP_SIGNINACTIVITY)
                                    .navigation();
                        } else {
                            new CommonDialog.Builder(mRootView.getActivity()).setTitle("提醒")
                                    .setContent("需要打开GPS 定位服务")
                                    .setPositiveButton("去设置", (v, commonDialog) -> {
                                        //跳转GPS设置界面
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        mRootView.getActivity().startActivity(intent);
                                    })
                                    .setNegativeButton("取消", null).create().show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, Constant.PERMISSIONS_LOCATION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
