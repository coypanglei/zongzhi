package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InspectionLatLng;
import com.weique.overhaul.v2.mvp.model.entity.InspectionListItemBean;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsBean;
import com.weique.overhaul.v2.mvp.model.entity.PatrolsDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchDetailItemBean;
import com.weique.overhaul.v2.mvp.model.entity.ResourceSearchItemBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ResouceSearchService {
    String path = "app/InspectionRecord/";

    /**
     * 走访数据列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetInterViewRecord")
    Observable<BaseBean<ResourceSearchItemBean>> getResourceSearchListData(@QueryMap Map<String, Object> map);

    /**
     * getResourceSearchDetailListData
     *
     * @param map map
     * @return ResourceSearchDetailItemBean
     */
    @GET(path + "GetInterViewRecordById")
    Observable<BaseBean<ResourceSearchDetailItemBean>> getResourceSearchDetailListData(@QueryMap Map<String, Object> map);

    /**
     * setTourVisitData
     *
     * @param map map
     * @return String
     */
    @FormUrlEncoded
    @POST(path + "CreateVisitRecord")
    Observable<BaseBean<String>> setTourVisitData(@FieldMap Map<String, Object> map);


    /**
     * 巡检数据列表
     *
     * @return Observable
     */
    @GET(path + "GetInspectionRecord")
    Observable<BaseBean<InspectionListItemBean>> getInspectionListData(@QueryMap Map<String, Object> map);

    @GET(path + "GetInspectionRecordById")
    Observable<BaseBean<ResourceSearchDetailItemBean>> getInspectionDetailListData(@QueryMap Map<String, Object> map);

    @GET(path + "GetInspectionRecordInfo")
    Observable<BaseBean<InspectionLatLng>> getLatLng(@QueryMap Map<String, Object> map);

    /**
     * 新增已有巡检点记录
     *
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "CreateInspectionPoint")
    Observable<BaseBean<String>> setInspectionData(@FieldMap Map<String, Object> map);

    /**
     * 新增新的巡检点记录
     *
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "CreateInspectionRecord")
    Observable<BaseBean<String>> InspectionOneAddBean(@FieldMap Map<String, Object> map);


    /**
     * 代办巡检，走访任务
     *
     * @param map map
     * @return
     */
    @GET("APP/InspectionTask/GetMyTasks")
    Observable<BaseBean<PatrolsBean>> getPatrolsListData(@QueryMap Map<String, Object> map);


    @GET("APP/InspectionTask/GetTaskInfo")
    Observable<BaseBean<PatrolsDetailItemBean>> getPatrolsDetailListData(@QueryMap Map<String, Object> map);


    @GET("APP/InspectionRecord/CompleteInspectionRecord")
    Observable<BaseBean<String>> setIsInspectionComplete(@QueryMap Map<String, Object> map);
}
