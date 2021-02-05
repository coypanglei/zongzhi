/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.dynamic.entity.CommonBackBean;
import com.weique.overhaul.v2.mvp.model.entity.AnnouncementListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EconomicManageMianBean;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.HomeMenuItemBean;
import com.weique.overhaul.v2.mvp.model.entity.IntergralBean;
import com.weique.overhaul.v2.mvp.model.entity.IntergralDetailsBean;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.MessageListBean;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.StaffListBean;
import com.weique.overhaul.v2.mvp.model.entity.StaffTemperatureBean;
import com.weique.overhaul.v2.mvp.model.entity.TaskListBean;
import com.weique.overhaul.v2.mvp.model.entity.ThirdPartyVerifyBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;
import com.weique.overhaul.v2.mvp.model.entity.User;
import com.weique.overhaul.v2.mvp.model.entity.WorkLogBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 主界面相关 接口
 * <p>
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * <p>
 * ================================================
 *
 * @author GK
 */
public interface MainService {

    /**
     * 获取用户信息
     *
     * @param map map
     * @return Observable
     */
    @GET("/users")
    Observable<BaseBean<User>> getUsers(@QueryMap Map<String, Object> map);

    /**
     * 获取用户信息
     *
     * @param map map
     * @return Observable
     */
    @GET("/users")
    Observable<BaseBean<User>> getUser(@QueryMap Map<String, Object> map);

    /**
     * 图文验证码 登录
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST("app/login/login")
    Observable<BaseBean<GlobalUserInfoBean>> getValidateCodeLogin(@FieldMap Map<String, Object> map);


    /**
     * 获取主页数据
     *
     * @param map map
     * @return Observable
     */
    @GET("app/login/GetNavForMobile")
    Observable<BaseBean<ArrayList<HomeMenuItemBean>>> getHomeModuleLabel(@QueryMap Map<String, Object> map);

    /**
     * APP端上传图片使用
     *
     * @param multipartBody multipartBody
     * @return Observable
     */
    @POST("APP/Common/UploadFileForApp")
    Observable<BaseBean<List<UploadFileRsponseBean>>> UploadFileForApp(@Body MultipartBody multipartBody);

    /**
     * 获取用户详情
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Login/getUserInfo")
    Observable<BaseBean<GlobalUserInfoBean>> getUserInfoDetail(@QueryMap Map<String, Object> map);

    /**
     * 获取消息列表
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Login/getMessage")
    Observable<BaseBean<MessageListBean>> getMessage(@QueryMap Map<String, Object> map);

    /**
     * 消息已读
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Login/MessageRead")
    Observable<BaseBean<Object>> setMessageIsReading(@QueryMap Map<String, Object> map);

    /**
     * 获取 版本信息
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/VersionControl/getVersionControl")
    Observable<BaseBean<NewVersionInfoBean>> getAppVersionInfo(@QueryMap Map<String, Object> map);

    /**
     * 获取 版本信息
     *
     * @return Observable
     */
    @GET("APP/Login/ToBeRead")
    Observable<BaseBean<Boolean>> getMessageStatus(@QueryMap Map<String, Object> map);

    /**
     * 获取 系统公告
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Login/getNotice")
    Observable<BaseBean<AnnouncementListBean>> getNotice(@QueryMap Map<String, Object> map);

    /**
     * 获取 系统公告 详情
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Login/getNoticeInfo")
    Observable<BaseBean<AnnouncementListBean.ListBean>> getNoticeInfo(@QueryMap Map<String, Object> map);

    /**
     * 修改用户头像
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST("APP/Login/EditUserInfo")
    Observable<BaseBean<Object>> updateUserPicture(@FieldMap Map<String, Object> map);

    /**
     * 获取任务列表
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Mission/GetMyMission")
    Observable<BaseBean<TaskListBean>> getTaskList(@QueryMap Map<String, Object> map);

    /**
     * 获取企业员工列表
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/EmployeeInfoRegisters/GetEmployeeInfoRegisters")
    Observable<BaseBean<StaffListBean>> getStaffList(@QueryMap Map<String, Object> map);

    /**
     * 上报 员工体温
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/EmployeeInfoRegisters/GetEmployeeInfoRegisters")
    Observable<BaseBean<Object>> sendStaffHeat(@QueryMap Map<String, Object> map);

    /**
     * 上报 员工体温
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST("APP/ReportEmpEverydays/CreateReportEmpRecord")
    Observable<BaseBean<Object>> getStaffTemperatureList(@FieldMap Map<String, Object> map);

    /**
     * 按照日期查询员工健康上报记录
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("APP/ReportEmpEverydays/GetReportEmpRecordHistory")
    Observable<BaseBean<List<StaffTemperatureBean>>> GetReportEmpRecordHistory(@QueryMap Map<String, Object> paramSign);

    /**
     * 动态表单 关系映射
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("/Admin/ElementTypeFieldsMaps/GetElementTypeMapByIdentity")
    Observable<BaseBean<String>> getDynamicFormOrm(@QueryMap Map<String, Object> paramSign);

    /**
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("/app/Login/CreateOMessageReply")
    Observable<BaseBean<Object>> replyMessage(@QueryMap Map<String, Object> paramSign);


    /**
     * 法务详情
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("/app/CourtOrders/CourtOrderDetail")
    Observable<BaseBean<LawWorksOrderDetailBean>> getLawDetailListData(@QueryMap Map<String, Object> paramSign);


    /**
     * 接单
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("/app/CourtOrders/ReceiveOrder")
    Observable<BaseBean<String>> getOrderData(@QueryMap Map<String, Object> paramSign);


    /**
     * 接单反馈
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @FormUrlEncoded
    @POST("/app/CourtOrders/FeedbackOrder")
    Observable<BaseBean<String>> getOrderIntoData(@FieldMap Map<String, Object> paramSign);

    /**
     * 修改个人信息
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @FormUrlEncoded
    @POST("/APP/Login/EditEmployee")
    Observable<BaseBean<Object>> EditEmployee(@FieldMap Map<String, Object> paramSign);

    /**
     * 获取采集表单列表 根据 检索
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("/App/CommunityElement/SearchFromElementByField")
    Observable<BaseBean<String>> getFormInfoBySearching(@QueryMap Map<String, Object> paramSign);

    /**
     * 三方登录 验证用户
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("AppGate/GeSGTtUserInfo")
    Observable<BaseBean<ThirdPartyVerifyBean>> verifyThirdPartyId(@QueryMap Map<String, Object> paramSign);

    /**
     * 绑定苏格通 至 综治用户
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("AppGate/BindSGTUser")
    Observable<BaseBean<Object>> sgtToZzBinding(@QueryMap Map<String, Object> paramSign);


    /**
     * 获取用户信息
     *
     * @param map map
     * @return Observable
     */
    @GET("/users")
    Observable<BaseBean<EconomicManageMianBean>> getMainInfo(@QueryMap Map<String, Object> map);


    /**
     * 获取用户积分和
     * http://192.168.20.129:86/App/AssessmentScore/GetAssessmentSumScore?userId=445D674B-9450-46A5-87FE-98643BE068D6
     */
    @GET("/App/AssessmentScore/GetAssessmentSumScore")
    Observable<BaseBean<IntergralBean>> getIntegral(@QueryMap Map<String, Object> map);


    /**
     * 获取用户积分详情
     * http://192.168.20.129:86/App/AssessmentScore/GetUserAssessmentDetails?userId=445D674B-9450-46A5-87FE-98643BE068D6
     */
    @GET("/App/AssessmentScore/GetUserAssessmentDetails")
    Observable<BaseBean<IntergralDetailsBean>> GetIntegralDetails(@QueryMap Map<String, Object> map);

    /**
     * 获取积分规则
     * http://47.102.147.120:2020/App/AssessmentScore/GetAssessmentConfig
     */
    @GET("/App/AssessmentScore/GetAssessmentConfig")
    Observable<BaseBean<IntergralDetailsBean>> GetAssessmentConfig(@QueryMap Map<String, Object> map);


    /**
     * 根据用户id 获取相同列表返回数据
     *
     * @param map map
     * @return Observable
     */
    @GET("{path}")
    Observable<BaseBean<CommonBackBean>> getCommonObject(@Path(value = "path", encoded = true) String url, @QueryMap Map<String, Object> map);

    /**
     * 个人工作清单
     */
    public static final String PERSONAL_WORK_STATISTICS = "App/PersonalWorkStatistics/GetWorkStatisticsList";

    @GET("/App/PersonalWorkStatistics/GetWorkSummaryData")
    Observable<BaseBean<WorkLogBean>> GetWorkLog(@QueryMap Map<String, Object> map);

}
