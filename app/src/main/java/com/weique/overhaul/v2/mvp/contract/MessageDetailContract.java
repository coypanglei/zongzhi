package com.weique.overhaul.v2.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/08/2020 18:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MessageDetailContract {

    interface View extends IView {

        /**
         * 设置reading
         */
        void setIsReadingSuccess();

        Activity getActivity();

    }


    interface Model extends IModel {

        /**
         * 设置消息已读
         *
         * @param id id
         * @return return
         */
        Observable<BaseBean<Object>> setMessageIsReading(String id);

        /**
         * replyMessage
         * @param map map
         * @return Observable
         */
        Observable<BaseBean<Object>> replyMessage(Map<String, Object> map);
    }
}
