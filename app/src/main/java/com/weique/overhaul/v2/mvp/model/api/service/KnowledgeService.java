package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.AreaDetailsListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBaseBean;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBean;
import com.weique.overhaul.v2.mvp.model.entity.PieChartBean;
import com.weique.overhaul.v2.mvp.model.entity.ScanResultBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface KnowledgeService {


    /**
     * getKnowledgeBaseListData
     *
     * @param map map
     * @return KnowledgeBaseBean
     */
    @GET("App/Knowledges/Search")
    Observable<BaseBean<KnowledgeBaseBean>> getKnowledgeBaseListData(@QueryMap Map<String, Object> map);

    /**
     * getLabelsListData
     *
     * @param map map
     * @return KnowledgeBean
     */
    @GET("App/Knowledges/GetKnowledgeLabels")
    Observable<BaseBean<List<KnowledgeBean>>> getLabelsListData(@QueryMap Map<String, Object> map);

    /**
     * getTypeListData
     *
     * @param map map
     * @return KnowledgeBeano
     */
    @GET("App/Knowledges/GetKnowledgeTypes")
    Observable<BaseBean<List<KnowledgeBean>>> getTypeListData(@QueryMap Map<String, Object> map);


    /**
     * @param paramSign
     * @return
     */
    @GET("App/CommunityElement/GetElementsByStandardAddressId")
    Observable<BaseBean<ScanResultBean>> getScanResultListData(@QueryMap Map<String, Object> paramSign);

    /**
     * 统计
     *
     * @param paramSign
     * @return
     */
    @GET("App/CommunityElement/MixSummary")
    Observable<BaseBean<PieChartBean>> getPieChartData(@QueryMap Map<String, Object> paramSign);

    /**
     * @param paramSign
     * @return
     */
    @GET("APP/DataReport/getDepartment")
    Observable<BaseBean<List<AreaDetailsListBean>>> getAreaDetailsListData(@QueryMap Map<String, Object> paramSign);


    /**
     * @param paramSign
     * @return
     */
    @GET("APP/DataReport/GetDepartmentReport")
    Observable<BaseBean<List<AreaDetailsListBean>>> getAreaDetailsSecondListData(@QueryMap Map<String, Object> paramSign);

}
