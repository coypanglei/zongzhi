package com.weique.overhaul.v2.mvp.contract;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventProceedRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


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
         * 点击 办理 评论  归档 退回
         */
        void next();

        /**
         * 设置办理人列表
         *
         * @param eventProceedRecordBeans eventProceedRecordBeans
         */
        void setTransactList(List<EventProceedRecordBean> eventProceedRecordBeans);

        void gotoMapTv(String pointsInJson);


        /**
         * 去相册
         * @param max
         */
        void goToPhotoAlbum(int max);
    }

    interface Model extends IModel {

        /**
         * @param id     id
         * @param custId custId
         * @return Observable
         */
        Observable<BaseBean<Object>> getEventRecordInfo(String id, String custId);

        /**
         * 小流程节点处理
         *
         * @param content content
         * @param id  id
         * @param status  status
         * @param synergyStatus  synergyStatus
         * @return Observable
         */
        Observable<BaseBean<Object>> setEventNodeHandle(String content, String id, int status,int synergyStatus);

        /**
         * 评价
         *
         * @param evaluation evaluation
         * @param recordId   recordId
         * @return Observable
         */
        Observable<BaseBean<Object>> createEvaluation(String evaluation, String recordId,String images);


        /**
         * 归档
         *
         * @param recordId recordId
         * @return Observable
         */
        Observable<BaseBean<Object>> placeOnFile(String recordId);

        /**
         * 归档
         *
         * @param custId custId
         * @return Observable
         */
        Observable<BaseBean<List<EventProceedRecordBean>>> getEventProceedRecord(String custId,String eventRId);

        Observable<BaseBean<GridInformationBean>> getGetDepartment(String departmentId);

        Observable<BaseBean<Object>> invalid(String id);

    }
}
