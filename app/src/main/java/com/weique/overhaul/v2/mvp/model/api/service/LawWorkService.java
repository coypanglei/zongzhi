package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksInformBean;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderListBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 法务servi
 */
public interface LawWorkService {
    String path = "App/CourtNotification/";
    String path_ = "app/CourtOrders/";

    /**
     * 获取 法务订单列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path_ + "IndexList")
    Observable<BaseBean<List<LawWorksOrderListBean>>> getOrderList(@QueryMap Map<String, Object> map);

    /**
     * 获取 法务 通知list
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET(path + "CourtNotifList")
    Observable<BaseBean<List<LawWorksInformBean>>> getInformList(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取通知详情
     *
     * @param paramSign
     * @return
     */
    @GET(path + "CourtNotifDetail")
    Observable<BaseBean<LawWorksInformBean>> getInformDetail(@QueryMap Map<String, Object> paramSign);

    /**
     * 获取通知详情
     *
     * @param paramSign
     * @return
     */
    @GET(path_ + "GetCourtOrderStatusList")
    Observable<BaseBean<Object>> getCourtOrderStatusList(@QueryMap Map<String, Object> paramSign);
}
