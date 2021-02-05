package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface SplashContract {
    interface View extends IView {
        /**
         * 更新倒计时
         *
         * @param second 秒数
         */
        void updateTime(Long second);

        /**
         * @return 获取activity 对象
         */
        Activity getActivity();

        /**
         * 获取权限
         *
         * @return
         */
        RxPermissions getRxPermissions();

        /**
         * 跳界面
         */
        void jumpPage();
    }

    interface Model extends IModel {
        /**
         * 倒计时
         * @param l  l
         * @return Observable
         */
        Observable<Long> downTime(Long l);
    }
}
