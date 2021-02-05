package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.User;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
public interface LoginContract {

    interface View extends IView {
        /**
         * @return Activity Activity
         */
        Activity getActivity();

        /**
         * 倒计时
         *
         * @param second 倒计时信息
         */
        void setAuthCode(String second);

        /**
         * 设置背景
         *
         * @param drawableId drawableId
         * @param color      color
         * @param b          是否可点击
         */
        void setAuthCodeViewBackground(int drawableId, int color, boolean b);

        /**
         * 图文码 更新
         */
        void initAuthCode();
    }

    interface Model extends IModel {
        /**
         * @param acc
         * @param pss
         * @return
         */
        Observable<BaseBean<User>> login(String acc, String pss);

        /**
         * 图文验证登录
         *
         * @param phone
         * @param password
         * @return Observable
         */
        Observable<BaseBean<GlobalUserInfoBean>> getValidateCodeLogin(String phone, String password);
    }
}
