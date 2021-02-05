package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.AddressBookItemBean;
import com.weique.overhaul.v2.mvp.model.entity.AddressBookListBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionAddSearchItemBean;
import com.weique.overhaul.v2.mvp.model.entity.MySignListBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsListBean;
import com.weique.overhaul.v2.mvp.model.entity.SigninStatusBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface AddressLookService {

    /**
     * getDepartmentInfoListData
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Employees/GetMyDepartmentInfo")
    Observable<BaseBean<AddressBookItemBean>> getDepartmentInfoListData(@QueryMap Map<String, Object> map);

    /**
     * getAddressBookListData
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Employees/GetEmployeeInfoByDepartmentId")
    Observable<BaseBean<AddressBookListBean>> getAddressBookListData(@QueryMap Map<String, Object> map);

    /**
     * getAllAddressBookListData
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Employees/GetEmployeeInfo")
    Observable<BaseBean<AddressBookListBean>> getAllAddressBookListData(@QueryMap Map<String, Object> map);

    /**
     * 获取 下属 信息列表 签到
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/Employees/getEmployee")
    Observable<BaseBean<AddressBookListBean>> getEmployee(@QueryMap Map<String, Object> map);

    /**
     * 签到
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/CheckInOrder/CreateMyCheckInOrder")
    Observable<BaseBean<String>> setSign(@QueryMap Map<String, Object> map);

    /**
     * MainActivity签到记录
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/CheckInOrder/automaticCheckInOrder")
    Observable<BaseBean<String>> automaticCheckInOrder(@QueryMap Map<String, Object> map);

    /**
     * 个人签到列表
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/CheckInOrder/GetMyCheckInOrders")
    Observable<BaseBean<MySignListBean>> getMySignRecordListData(@QueryMap Map<String, Object> map);


    @GET("APP/CheckInOrder/GetCheckInOrdersByKeyword")
    Observable<BaseBean<MySignListBean>> getSignRecordListData(@QueryMap Map<String, Object> map);

    @GET("APP/CheckInOrder/GetCheckInOrder")
    Observable<BaseBean<List<MySignListBean.ListBean>>> GetCheckInOrder(@QueryMap Map<String, Object> map);


    @GET("APP/CheckInOrder/getIsMyCheck")
    Observable<BaseBean<SigninStatusBean>> getIsMyCheck(@QueryMap Map<String, Object> map);

    /**
     * 网格内标准地址列表
     *
     * @param map map
     * @return ContradictionAddSearchItemBean
     */
    @GET("APP/CAEventOrder/GetStandardAddress")
    Observable<BaseBean<ContradictionAddSearchItemBean>> getContradictionAddListData(@QueryMap Map<String, Object> map);


    /**
     * 将需要发送聊天人员传送到服务器
     *
     * @param paramSign
     * @return
     */
    @GET("APP/Employees/SendVideoConference")
    Observable<BaseBean<String>> setChatList(@QueryMap Map<String, Object> paramSign);


    /**
     *  获取坐标点
     */
    @GET("app/CommunityElement/GetPoints")
    Observable<BaseBean<PointsListBean>> getPoints(@QueryMap Map<String,Object> paramSign);



    /**
     *  获取网格信息
     */
    @GET("app/CommunityElement/GetDepartmentsPoints")
    Observable<BaseBean<List<PointsBean>>> getDepartmentsPoints(@QueryMap Map<String,Object> paramSign);

}
