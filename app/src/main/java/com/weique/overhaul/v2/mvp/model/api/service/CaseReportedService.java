package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.CaseReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonEnumBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.EconomicManageMianBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoItemBean;
import com.weique.overhaul.v2.mvp.model.entity.EntInfoItemListBean;
import com.weique.overhaul.v2.mvp.model.entity.LawDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterDetailsFlowBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.PartiesBean;
import com.weique.overhaul.v2.mvp.model.entity.ProjectInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.collectBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface CaseReportedService {
    String path = "app/";


    /**
     * 获取枚举数据列表
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET(path + "Common/GetEnums")
    Observable<BaseBean<List<CommonTitleBean>>> getTitles(@QueryMap Map<String, Object> paramSign);


    @GET(path + "ComprehensiveLawEnforcementCases/IndexList")
    Observable<BaseBean<List<CaseReportedBean>>> getCaseList(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取事项列表
     *
     * @param paramSign Sign paramSign
     * @return Observable
     */
    @GET(path + "ComprehensiveLawEnforcementMattersTypes/IndexList")
    Observable<BaseBean<List<MatterBean>>> getMatterList(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取事项列表
     *
     * @param paramSign Sign paramSign
     * @return Observable
     */
    @GET(path + "ComprehensiveLawEnforcementMatters/IndexList")
    Observable<BaseBean<List<MatterSecondBean>>> getMatterSecondList(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取案件分类
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET(path + "ComprehensiveLawEnforcementCaseTypes/IndexList")
    Observable<BaseBean<List<NameAndIdBean>>> getLawSort(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取执法部门
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET(path + "ComprehensiveLawEnforcementOfficers/GetDeparts")
    Observable<BaseBean<List<NameAndIdBean>>> getLegalOperation(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取当事人列表
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET(path + "ComprehensiveLawEnforcementTargets/IndexList")
    Observable<BaseBean<List<PartiesBean>>> getPartiesList(@QueryMap Map<String, Object> paramSign);

    /**
     * 提交 当事人信息
     *
     * @param paramSign paramSign
     * @return
     */
    @FormUrlEncoded
    @POST(path + "ComprehensiveLawEnforcementTargets/Create")
    Observable<BaseBean<String>> submitPartiesInfo(@FieldMap Map<String, Object> paramSign);

    /**
     * getAuditorList
     *
     * @param paramSign paramSign
     * @return
     */
    @GET(path + "ComprehensiveLawEnforcementOfficers/IndexList")
    Observable<BaseBean<List<NameAndIdBean>>> getAuditorList(@QueryMap Map<String, Object> paramSign);

    /**
     * 提交
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "ComprehensiveLawEnforcementCases/Create")
    Observable<BaseBean<Object>> submitLaw(@FieldMap Map<String, Object> paramSign);

    /**
     * 获取案件详情
     *
     * @param paramSign
     * @return
     */
    @GET(path + "ComprehensiveLawEnforcementCases/Detail")
    Observable<BaseBean<LawDetailBean>> getLawDetailById(@QueryMap Map<String, Object> paramSign);

    /**
     * 提交办理记录
     *
     * @param paramSign
     * @return
     */
    @FormUrlEncoded
    @POST(path + "ComprehensiveLawEnforcementCaseProcesses/Create")
    Observable<BaseBean<Object>> submitManageInfo(@FieldMap Map<String, Object> paramSign);

    /**
     * 获取首页数据
     *
     * @param map
     * @return
     */
    @GET("/API/EcoDev/GetSummaryInfoes")
    Observable<BaseBean<EconomicManageMianBean>> getMainInfo(@QueryMap Map<String, Object> map);


    /**
     * 创建 企业信息采集
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/API/EcoDev/CreateEntInfo")
    Observable<BaseBean<EconomicManageMianBean>> createEntInfo(@FieldMap Map<String, Object> map);


    /**
     * 创建 项目信息采集
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("/api/EcoDev/CreateMProject")
    Observable<BaseBean<EconomicManageMianBean>> createProjectInfo(@FieldMap Map<String, Object> map);

    /**
     * 获取 企业信息采集
     *
     * @param map
     * @return
     */
    @GET("/API/EcoDev/GetEntInfoModelMetaData")
    Observable<BaseBean<List<collectBean>>> getList(@QueryMap Map<String, Object> map);


    /**
     * 获取  行业类型
     *
     * @param map
     * @return
     */
    @GET("/API/EcoDev/GetEntInfoTypes")
    Observable<BaseBean<List<NameAndIdBean>>> getEnterpriseType(@QueryMap Map<String, Object> map);

    /**
     * 项目列表
     *
     * @param map map
     * @return Observable
     */
    @GET("/api/EcoDev/QueryMProjects")
    Observable<BaseBean<List<NameAndIdBean>>> searchEntInfos(@QueryMap Map<String, Object> map);

    /**
     * 企业列表
     *
     * @param map map
     * @return Observable
     */
    @GET("/api/EcoDev/QueryEntInfoes")
    Observable<BaseBean<List<NameAndIdBean>>> getEnterpriseList(@QueryMap Map<String, Object> map);


    /**
     * 获取企业详情
     */
    @GET("/api/EcoDev/GetEntInfo")
    Observable<BaseBean<EntInfoBean>> getEntInfo(@QueryMap Map<String, Object> map);


    /**
     * 获取事项详情
     */
    @GET("/app/EventRecords/EventRecordDetail")
    Observable<BaseBean<MatterDetailsBean>> getMatterInfo(@QueryMap Map<String, Object> map);

    /**
     * 获取数据集
     *
     * @param map
     * @return
     */
    @GET("/api/EcoDev/GetEntInfoItem")
    Observable<BaseBean<EntInfoItemListBean>> getEntInfoItem(@QueryMap Map<String, Object> map);


    /**
     * 修改包挂领导
     */
    @GET("/api/EcoDev/EditCharger")
    Observable<BaseBean<Object>> createCharger(@QueryMap Map<String, Object> map);


    /**
     * 获取数据集
     *
     * @param map
     * @return
     */
    @GET("/api/EcoDev/GetMProjectsItem")
    Observable<BaseBean<List<EntInfoItemBean>>> getProjectItem(@QueryMap Map<String, Object> map);

    /**
     * 获取项目详情
     */
    @GET("/api/EcoDev/GetMProjectInfo")
    Observable<BaseBean<ProjectInfoBean>> getProjectInfo(@QueryMap Map<String, Object> map);

    /**
     * 删除项目信息
     *
     * @param map
     * @return
     */
    @GET("/api/EcoDev/DelMProjects")
    Observable<BaseBean<Object>> deleteProjectInfo(@QueryMap Map<String, Object> map);

    @GET(path + "Common/GetEnums")
    Observable<BaseBean<List<CommonEnumBean>>> getCommonEnumBean(@QueryMap Map<String, Object> paramSign);

    /**
     * 删除企业信息
     *
     * @param map
     * @return
     */
    @GET("/api/EcoDev/DelEntInfo")
    Observable<BaseBean<Object>> deleteEnterpriseInfo(@QueryMap Map<String, Object> map);

    /**
     * 获取事项基本信息
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("app/EventRecords/EventRecordDetail")
    Observable<BaseBean<MatterDetailsBean>> getDetailById(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取事项进度
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("app/EventRecords/EventRecordDetail")
    Observable<BaseBean<List<MatterDetailsFlowBean>>> getProgress(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取事项催办信息
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("app/EventRecords/EventRecordDetail")
    Observable<BaseBean<Object>> getCuiBan(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取事项督办信息
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("app/EventRecords/EventRecordDetail")
    Observable<BaseBean<Object>> getDuBan(@QueryMap Map<String, Object> paramSign);
}
