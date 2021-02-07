package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventProceedRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationItemPictureBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
public interface EventsReportedLookContract {
    interface View extends IView {

        /**
         * 获取context
         *
         * @return Context
         */
        Context getActivity();

        /**
         * 设置详情数据
         *
         * @param json json
         */
        void setData(Object json);


        /**
         * 设置办理人列表
         *
         * @param eventProceedRecordBeans eventProceedRecordBeans
         */
        void setTransactList(List<EventProceedRecordBean> eventProceedRecordBeans);

        void gotoMapTv(String pointsInJson);


        /**
         * 去相册
         *
         * @param max
         */
        void goToPhotoAlbum(int max);

        void setUpLoadItem(List<InformationItemPictureBean> list);

    }

    interface Model extends IModel {

        /**
         * @param id     id
         * @param custId custId
         * @return Observable
         */
        Observable<BaseBean<Object>> getEventRecordInfo(String id, String custId);

        /**
         * 评价
         *
         * @param evaluation evaluation
         * @param recordId   recordId
         * @return Observable
         */
        Observable<BaseBean<Object>> createEvaluation(String evaluation, String recordId, String images);

        /**
         * 归档
         *
         * @param custId custId
         * @return Observable
         */
        Observable<BaseBean<List<EventProceedRecordBean>>> getEventProceedRecord(String custId, String eventRId);

        Observable<BaseBean<GridInformationBean>> getGetDepartment(String departmentId);

        Observable<BaseBean<Object>> invalid(String id);

    }
}
