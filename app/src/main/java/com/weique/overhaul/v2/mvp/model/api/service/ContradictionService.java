package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionItemBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.WorkLineLatBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author GreatKing
 */
public interface ContradictionService {


    /**
     * 矛盾调解首页
     *
     * @param map map
     * @return ContradictionItemBean
     */
    @GET("APP/CAEventOrder/GetCAEventOrder")
    Observable<BaseBean<ContradictionItemBean>> getContradictionListData(@QueryMap Map<String, Object> map);

    /**
     * 提交矛盾调解信息
     *
     * @param map map
     * @return String
     */
    @FormUrlEncoded
    @POST("APP/CAEventOrder/CreateCAEventOrder")
    Observable<BaseBean<String>> setContradictionAddData(@FieldMap Map<String, Object> map);

    /**
     * 获取矛盾调解详情
     *
     * @param map map
     * @return ContradictionRecordBean
     */
    @GET("APP/CAEventOrder/GetCAEventOrderInfo")
    Observable<BaseBean<ContradictionRecordBean>> getContradictionRecord(@QueryMap Map<String, Object> map);


    @GET("APP/CAEventOrder/DelCAEventOrderByPhysical")
    Observable<BaseBean<String>> DeleteContradictionRecord(@QueryMap Map<String, Object> map);


    /**
     * InvalidContradictionRecord
     *
     * @param map map
     * @return String
     */
    @GET("APP/CAEventOrder/DelCAEventOrderByLogic")
    Observable<BaseBean<String>> InvalidContradictionRecord(@QueryMap Map<String, Object> map);


    /**
     * 修改调解信息
     *
     * @param map map
     * @return String
     */
    @FormUrlEncoded
    @POST("APP/CAEventOrder/EditCAEventOrder")
    Observable<BaseBean<String>> setResetContradiction(@FieldMap Map<String, Object> map);


    @GET("APP/CheckInOrder/WorkingTrack")
    Observable<BaseBean<List<WorkLineLatBean>>> getLatLng(@QueryMap Map<String, Object> map);
}
