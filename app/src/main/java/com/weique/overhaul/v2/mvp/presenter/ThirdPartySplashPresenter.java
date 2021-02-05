package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.ThirdPartySplashContract;
import com.weique.overhaul.v2.mvp.ui.popupwindow.CommonDialog;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
public class ThirdPartySplashPresenter extends ReworkBasePresenter<ThirdPartySplashContract.Model, ThirdPartySplashContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    String uId = "";

    @Inject
    public ThirdPartySplashPresenter(ThirdPartySplashContract.Model model, ThirdPartySplashContract.View rootView) {
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
     * 验证第三方人员身份
     *
     * @param id id
     */
    public void verifyThirdPartyId(String id) {
        commonGetData(mModel.verifyThirdPartyId(id), mErrorHandler, thirdPartyVerify -> {
            //1.未绑定用户判断当前是否已登录 ->已登录就提示重新登录并绑定 ->未登录就直接去登陆页登录并绑定
            //2.已绑定用户判断当前是否登录 ->登录用户对比 判断退出还是直接去登录  不用再绑定
            try {
                boolean isExist = thirdPartyVerify.isIsExist();
                if (isExist) {
                    if (thirdPartyVerify.getUserInfo() == null) {
                        ArmsUtils.makeText("未获取到苏格通绑定的综治用户信息");
                        return;
                    }
                    uId = thirdPartyVerify.getUserInfo().getUid();
                    //已绑定
                    if (UserInfoUtil.getUserInfo() != null) {
                        //登录状态判断是否是已绑定的用户  登录的
                        if (UserInfoUtil.getUserInfo().getUid().equals(thirdPartyVerify.getUserInfo().getUid())) {
                            jumpPage(id, isExist, uId, RouterHub.APP_MAINACTIVITY);
                            mRootView.killMyself();
                            //去主页
                        } else {
                            new CommonDialog.Builder(mRootView.getContext())
                                    .setTitle("提示")
                                    .setContent("当前登录的用户，不是苏格通绑定账号，请重新登录")
                                    .setPositiveButton("重新登录", (view, diaoa) -> {
                                        //不是已绑定的三方用户  去重新登录
                                        jumpPage(id, isExist, uId, RouterHub.APP_LOGINACTIVITY);
                                        mRootView.killMyself();
                                    })
                                    .setNegativeButton("退出", (view, diaoa) -> {
                                        mRootView.killMyself();
                                    }).create().show();
                        }
                    } else {
                        //去登录页
                        //不用绑定用户
                        jumpPage(id, isExist, uId, RouterHub.APP_LOGINACTIVITY);
                        mRootView.killMyself();
                    }
                } else {
                    //未绑定
                    if (UserInfoUtil.getUserInfo() != null) {
                        //提示去首页绑定用户信息
                        jumpPage(id, isExist, uId, RouterHub.APP_MAINACTIVITY);
                    } else {
                        //直接去登录
                        jumpPage(id, isExist, uId, RouterHub.APP_LOGINACTIVITY);
                    }
                    mRootView.killMyself();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void jumpPage(String id, boolean isIsExist, String name, String appMainactivity) {
        ARouter.getInstance().build(appMainactivity)
                .withString(ARouerConstant.SOURCE, RouterHub.APP_THIRDPARTYSPLASHPRESENTER)
                .withString(ARouerConstant.ID, id)
                .withString(ARouerConstant.LOGIN_NAME, name)
                .withBoolean(ARouerConstant.IS_BINDING, isIsExist)
                .navigation();
    }
}
