package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface LawWorksContract {

    interface View extends IView {

    }


    interface Model extends IModel {

        /**
         * 获取订单流程
         *
         * @return Observable
         */
        Observable<BaseBean<Object>> getCourtOrderStatusList();
    }
}
