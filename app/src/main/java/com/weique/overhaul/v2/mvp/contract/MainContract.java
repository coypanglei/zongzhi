package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface MainContract {

    interface View extends IView {
        Activity getActivity();

        void setSignData(String data);

        /**
         * 设置是否有新消息
         *
         * @param o b
         */
        void setHasNewMessage(Boolean o);

        /**
         * 显示版本log
         *
         * @param newVersionInfoBean newVersionInfoBean
         */
        void showVersionDialog(NewVersionInfoBean newVersionInfoBean);

        /**
         * 开始下载
         *
         * @param newVersionInfoBean newVersionInfoBean
         */
        void showStartDownLoad(NewVersionInfoBean newVersionInfoBean);

        /**
         * getMessageStatus
         */
        void getMessageStatus();

        void showOpenGpsDialog();

        void registerReceiver();
    }


    interface Model extends IModel {
        Observable<BaseBean<String>> automaticCheckInOrder(String PointsInJson, String Address);

        /**
         * 获取版本信息
         *
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<NewVersionInfoBean>> getAppVersionInfo(Map<String, Object> map);

        /**
         * 获取消息状态  是否有新消息
         *
         * @return Observable
         */
        Observable<BaseBean<Boolean>> getMessageStatus();

        /**
         * 苏格通 要转 综治绑定
         *
         * @param id id
         * @return Observable
         */
        Observable<BaseBean<Object>> sgtToZzBinding(String id);
    }
}
