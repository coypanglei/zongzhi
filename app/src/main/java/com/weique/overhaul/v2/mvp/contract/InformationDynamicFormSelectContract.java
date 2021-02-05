package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import androidx.annotation.Nullable;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/27/2019 16:20
 * ================================================
 */
public interface InformationDynamicFormSelectContract {

    interface View extends IView {

        Context getContext();

        /**
         * 跟新界面数据
         *
         * @param o
         */
        void setData(Object o);

        /**
         * 删除成功
         */
        void deleteSuccess();

        void gotoMapTv(GridInformationBean gridInformationBean);
    }


    interface Model extends IModel {

        /**
         * 具体元素信息
         *
         * @param elementId     elementId
         * @param elementTypeId elementTypeId
         * @return Observable
         */
        Observable<BaseBean<Object>> getElementInfo(String elementId, String elementTypeId);

        /**
         * 删除元素
         *
         * @param elementId     elementId
         * @param elementTypeId elementTypeId
         * @return Observable
         */
        Observable<BaseBean<Object>> delete(String elementId, String elementTypeId);

        Observable<BaseBean<GridInformationBean>> getGetDepartment(@Nullable String departmentId);
    }
}
