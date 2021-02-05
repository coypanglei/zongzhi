package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;
import android.os.CountDownTimer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.LoginContract;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.User;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@ActivityScope
public class LoginPresenter extends ReworkBasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    ACache aCache;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 登录
     *
     * @param loginName loginName
     * @param password  password
     */
    public void login(String loginName, String password) {
        try {
            if (StringUtil.isNullString(loginName)) {
                ArmsUtils.makeText(ArmsUtils.getString(mRootView.getActivity(), R.string.input_phone));
                return;
            }
            if (StringUtil.isNullString(password)) {
                ArmsUtils.makeText(ArmsUtils.getString(mRootView.getActivity(), R.string.auth_code_not_null));
                return;
            }
            if (true) {
                mRootView.launchActivityByRouter(RouterHub.APP_MAINACTIVITY);
                mRootView.killMyself();
                return;
            }
            commonGetData(mModel.login(loginName, password), mErrorHandler, new ProgressMonitorHandle<User>() {
                @Override
                public void getBodyFromObject(User t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * 开始获取验证码
     */
    public void startGetAuthCode(String phone) {
        try {
            if (StringUtil.isNullString(phone)) {
                ArmsUtils.makeText(ArmsUtils.getString(mRootView.getActivity(), R.string.input_phone));
                return;
            }
            mRootView.setAuthCodeViewBackground(R.drawable.shape_b_ebebeb_c_4, R.color.gray_999, false);
            new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mRootView.setAuthCode(String.format(mApplication.getString(R.string.residue), millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    mRootView.setAuthCode(ArmsUtils.getString(mRootView.getActivity(), R.string.get_auth_code));
                    mRootView.setAuthCodeViewBackground(R.drawable.shape_b_2483f5_c_4, R.color.white, true);
                    cancel();
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图文验证码登录
     */
    public void getValidateCodeLogin(String phone, String password, String zongzhiId, String source, boolean isIsExist, String id) {
        commonGetData(mModel.getValidateCodeLogin(phone, password), mErrorHandler, new ProgressMonitorHandle<GlobalUserInfoBean>() {
            /**
             * 从 响应中 获取body 返还界面
             *
             * @param globalUserInfoBean body
             */
            @Override
            public void getBodyFromObject(GlobalUserInfoBean globalUserInfoBean) {
                UserInfoUtil.saveUserInfo(globalUserInfoBean);
                //三方登录来的
                if (RouterHub.APP_THIRDPARTYSPLASHPRESENTER.equals(source)) {
                    if (isIsExist) {
                        if (!globalUserInfoBean.getUid().equals(zongzhiId)) {
                            ArmsUtils.makeText("您登录的账户不是，当前苏格通绑定的综治用户");
                            return;
                        }
                    }
                    ARouter.getInstance().build(RouterHub.APP_MAINACTIVITY)
                            .withString(ARouerConstant.SOURCE, source)
                            .withString(ARouerConstant.ID, id)
                            .withBoolean(ARouerConstant.IS_BINDING, isIsExist)
                            .navigation();
                } else {
                    mRootView.launchActivityByRouter(RouterHub.APP_MAINACTIVITY);
                }
                mRootView.killMyself();
            }
        });
    }
}
