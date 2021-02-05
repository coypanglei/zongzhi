package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInformation;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInformationDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInterViewListBean;
import com.weique.overhaul.v2.mvp.model.entity.BusinessQuestion;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueListBean;
import com.weique.overhaul.v2.mvp.model.entity.InterviewDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.LeaderBean;
import com.weique.overhaul.v2.mvp.model.entity.StaffDetailBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 *
 */
public interface EnterpiseService {
    String path = "APP/EmployeeInfoRegisters/";

    /**
     * 获取我的包挂领导信息
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetMyEntLeaderInfo")
    Observable<BaseBean<LeaderBean>> getMyEntLeaderInfo(@QueryMap Map<String, Object> map);

    /**
     * 企业获取 问题里列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetAllMyEntTroubleOrders")
    Observable<BaseBean<EnterpriseIssueListBean>> getEnterpriseIssueList(@QueryMap Map<String, Object> map);

    /**
     * 企业上报问题时 获取  问题类型列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetTroubleType")
    Observable<BaseBean<List<String>>> getIssueTypeList(@QueryMap Map<String, Object> map);

    /**
     * 企业上报问题详情
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetMyTroubleOrderById")
    Observable<BaseBean<EnterpriseIssueDetailBean>> getIssueDetail(@QueryMap Map<String, Object> map);

    /**
     * @param map map
     * @return
     */
    @FormUrlEncoded
    @POST(path + "SubmitEntTroubleOrder")
    Observable<BaseBean<Object>> submitInfo(@FieldMap Map<String, Object> map);

    /**
     * 企业提交问题给包挂领导
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "")
    Observable<BaseBean<Object>> submitEntTroubleOrder(@FieldMap Map<String, Object> map);

    /**
     * 提交走访记录
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "SubmitEntVisitRecord")
    Observable<BaseBean<Object>> submitEntVisitRecord(@FieldMap Map<String, Object> map);

    /**
     * 包挂人回复
     *
     * @param paramSign map
     * @return Observable
     */
    @GET(path + "GetEntTroubleOrderReplyDetail")
    Observable<BaseBean<String>> getIssueReplyDetail(@QueryMap Map<String, Object> paramSign);

    /**
     * 企业信息
     *
     * @param paramSign
     * @return
     */
    @GET(path + "GetEnterpriseInfList")
    Observable<BaseBean<BusinessInformation>> getBusinessInformationListData(@QueryMap Map<String, Object> paramSign);

    /**
     * 企业信息详情
     *
     * @param paramSign
     * @return
     */
    @GET(path + "GetEnterpriseInfInfo")
    Observable<BaseBean<BusinessInformationDetailBean>> getBusinessInformationDetailListData(@QueryMap Map<String, Object> paramSign);


    /**
     * 企业走访列表
     *
     * @param paramSign
     * @return
     */
    @GET(path + "GetMyEntVisitRecords")
    Observable<BaseBean<BusinessInterViewListBean>> getBusinessInterviewListData(@QueryMap Map<String, Object> paramSign);


    /**
     * 企业问题
     *
     * @param paramSign
     * @return
     */
    @GET(path + "GetLeadersEntTroubleOrders")
    Observable<BaseBean<BusinessQuestion>> getBusinessQuestionData(@QueryMap Map<String, Object> paramSign);

    /**
     * 企业走访记录明细
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET(path + "GetEntVisitRecordDetail")
    Observable<BaseBean<InterviewDetailBean>> getInterviewDetail(@QueryMap Map<String, Object> paramSign);

    /**
     * 提交回复
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "EntTroubleOrderReply")
    Observable<BaseBean<Object>> submitFeedback(@FieldMap Map<String, Object> paramSign);

    /**
     * 获取员工信息
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET(path + "GetEmployeeInfo")
    Observable<BaseBean<StaffDetailBean>> getStaffData(@QueryMap Map<String, Object> paramSign);
}
