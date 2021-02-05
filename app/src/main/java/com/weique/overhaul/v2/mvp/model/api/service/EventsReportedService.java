package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EventProceedRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.EventsReportedSortBean;
import com.weique.overhaul.v2.mvp.model.entity.HelperListItemBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterListBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 事件上报相关接口
 *
 * @author GK
 */
public interface EventsReportedService {
    String path = "APP/EventFormType/";

    /**
     * 获取事件类型列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getEventFormType")
    Observable<BaseBean<EventsReportedSortBean>> getEventFormType(@QueryMap Map<String, Object> map);

    /**
     * 获取事件列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getEventRecord")
    Observable<BaseBean<EventsReportedBean>> getEventRecord(@QueryMap Map<String, Object> map);

    /**
     * 获取事件列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getEventRecording")
    Observable<BaseBean<EventsReportedBean>> getEventRecording(@QueryMap Map<String, Object> map);


    /**
     * 获取事件列表 - 详情
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getEventRecordInfo")
    Observable<BaseBean<Object>> getEventRecordInfo(@QueryMap Map<String, Object> map);

    /**
     * createEventRecord
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "createEventRecord")
    Observable<BaseBean<Object>> createEventRecord(@FieldMap Map<String, Object> map);

    /**
     * 修改事件上报
     * <p>
     * editEventRecord
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "EditEventRecord")
    Observable<BaseBean<Object>> editEventRecord(@FieldMap Map<String, Object> map);

    /**
     * 事件节点流程处理
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "EventNodeHandle")
    Observable<BaseBean<Object>> setEventNodeHandle(@QueryMap Map<String, Object> map);

    /**
     * 删除事件
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "delEventRecord")
    Observable<BaseBean<Object>> delEventRecord(@QueryMap Map<String, Object> map);

    /**
     * 评价
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "createEvaluation")
    Observable<BaseBean<Object>> createEvaluation(@FieldMap Map<String, Object> map);

    /**
     * 归档
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "PlaceOnFile")
    Observable<BaseBean<Object>> placeOnFile(@QueryMap Map<String, Object> map);

    /**
     * 获取 办理人员 处理意见列表信息
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getEventProceedRecord")
    Observable<BaseBean<List<EventProceedRecordBean>>> getEventProceedRecord(@QueryMap Map<String, Object> map);

    /**
     * 修改提交
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "createEventRecordSnap")
    Observable<BaseBean<Object>> createEventRecordSnap(@FieldMap Map<String, Object> map);

    /**
     * 作废
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "Invalid")
    Observable<BaseBean<Object>> invalid(@QueryMap Map<String, Object> map);

    /**
     * 判断是否允许 修改定位地址
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET(path + "getIsInGrid")
    Observable<BaseBean<Boolean>> getIsInGrid(@QueryMap Map<String, Object> paramSign);

    /**
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("app/EventRecords/EventRecordList")
    Observable<BaseBean<MatterListBean>> getList(@QueryMap Map<String, Object> paramSign);

    /**
     * 受理
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("app/EventRecords/AcceptEvent")
    Observable<BaseBean<Object>> accept(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取用户信息
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("app/EventRecords/GetUserInfoForCoop")
    Observable<BaseBean<List<NameAndIdBean>>> getUserInfoForCoop(@QueryMap Map<String, Object> paramSign);

    @POST("app/EventRecords/SetCoopInfo")
    Observable<BaseBean<Object>> requestSynergy(@QueryMap Map<String, Object> paramSign);

    @POST("app/EventRecords/EventRecordCoopReceivingOrder")
    Observable<BaseBean<Object>> consentHelp(@QueryMap Map<String, Object> paramSign);

    @POST("app/EventRecords/GetMyCooperListByReceiveUserId")
    Observable<BaseBean<MatterListBean>> getMyCooperListByReceiveUserId(@QueryMap Map<String, Object> paramSign);

    @GET("app/EventRecords/GetCoopsInfoByEventRecordId")
    Observable<BaseBean<List<HelperListItemBean>>> getHelperList(@QueryMap Map<String, Object> paramSign);

    @GET("app/EventRecords/GetMyProceedListByUserId")
    Observable<BaseBean<MatterListBean>> getMyProceedListByUserId(@QueryMap Map<String, Object> paramSign);

    @GET("app/EventRecords/ProceedingOrder")
    Observable<BaseBean<Object>> proceedingOrder(@QueryMap Map<String, Object> paramSign);
}