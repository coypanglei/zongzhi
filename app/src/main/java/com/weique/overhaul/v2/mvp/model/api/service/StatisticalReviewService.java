package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.DataReportInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.GridDataBean;
import com.weique.overhaul.v2.mvp.model.entity.IncidentReportingBean;
import com.weique.overhaul.v2.mvp.model.entity.StatisticalReviewLineBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 *
 */
public interface StatisticalReviewService {
    String path = "APP/DataReport/";

    /**
     * 获取我的签到信息
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getCheckOrder")
    Observable<BaseBean<StatisticalReviewLineBean>> getSignInCount(@QueryMap Map<String, Object> map);

    /**
     * 获取矛盾调节
     *
     * @param map
     * @return
     */
    @GET(path + "getCAEventOrder")
    Observable<BaseBean<StatisticalReviewLineBean>> getContradictionAdjustmentCount(@QueryMap Map<String, Object> map);


    /**
     * 获取网格走访统计
     *
     * @param map
     * @return
     */
    @GET(path + "getVisit")
    Observable<BaseBean<List<GridDataBean>>> getVisitList(@QueryMap Map<String, Object> map);


    /**
     * 获取巡检统计
     *
     * @param map
     * @return
     */
    @GET(path + "getInspection")
    Observable<BaseBean<List<GridDataBean>>> getPatrolInspectionList(@QueryMap Map<String, Object> map);


    /**
     * 获取事件上报统计
     *
     * @param map
     * @retrurn
     */
    @GET(path + "GetEventFormType")
    Observable<BaseBean<IncidentReportingBean>> getIncidentReportingList(@QueryMap Map<String, Object> map);


    /**
     * 获取数据统计区域详情
     *
     * @param map
     * @retrurn
     */
    @GET(path + "getDataReportInfo")
    Observable<BaseBean<DataReportInfoBean>> getdataReportList(@QueryMap Map<String, Object> map);

    /**
     * 获取信息采集统计数据
     */
    @GET(path +"getInfoCollection")
    Observable<BaseBean<List<GridDataBean>>> getDataStatistics(@QueryMap Map<String, Object> map);

}
