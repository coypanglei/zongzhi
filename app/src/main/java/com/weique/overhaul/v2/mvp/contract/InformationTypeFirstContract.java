package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
public interface InformationTypeFirstContract {

    interface View extends IView {
        /**
         * 获取 Context
         *
         * @return Context
         */
        Context getContext();


        /**
         * 树形数据
         *
         * @param dataTrees dataTrees
         */
        void setDataTree(List<InformationTypeOneSecondBean.ElementTypeListBean> dataTrees, boolean needAdd);
    }


    interface Model extends IModel {


        /**
         * 获取 树形  信息采集类型
         *
         * @param id id
         * @return Observable
         */
        Observable<BaseBean<InformationTypeOneSecondBean>> getElementTypes(String id);
    }
}
