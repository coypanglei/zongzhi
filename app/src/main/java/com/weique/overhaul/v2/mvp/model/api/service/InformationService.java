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

import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.PointsListBean;
import com.weique.overhaul.v2.mvp.model.entity.UserGideBean;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 信息采集 相关 接口
 * <p>
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * <p>
 * ================================================
 *
 * @author GK
 */
public interface InformationService {
    String path = "APP/CommunityElement/";


    /**
     * 获取信息类型分类
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetElementType")
    Observable<BaseBean<List<InformationTypeOneSecondBean.ElementTypeListBean>>> getElementType(@QueryMap Map<String, Object> map);

    /**
     * 获取信息类型分类 子集
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetGridResource")
    Observable<BaseBean<InformationTypeOneSecondBean>> getGridResource(@QueryMap Map<String, Object> map);

    /**
     * 获取信息类型分类 子集 - 集体详细信息
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getElementInfo")
    Observable<BaseBean<Object>> getElementInfo(@QueryMap Map<String, Object> map);

    /**
     * 根据 department ID 获取 网格 相关信息
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getDepartment")
    Observable<BaseBean<GridInformationBean>> getDepartment(@QueryMap Map<String, Object> map);

    /**
     * 新增 一个 人地事物相关信息
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "CreateElement")
    Observable<BaseBean<InformationTypeOneSecondBean.ElementListBean>> createElement(@FieldMap Map<String, Object> map);


    /**
     * 修改 一个 人地事物相关信息
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "EditElement")
    Observable<BaseBean<InformationTypeOneSecondBean.ElementListBean>> editElement(@FieldMap Map<String, Object> map);


    /**
     * APP端上传图片使用
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "DeleteElement")
    Observable<BaseBean<Object>> delete(@FieldMap Map<String, Object> map);

    /**
     * 修改 一个 人地事物相关信息
     *
     * @return Observable
     */
    @FormUrlEncoded
    @POST("APP/Common/getNewGuidForApp")
    Observable<BaseBean<String>> getNewGuidForApp(@FieldMap Map<String, Object> map);

    /**
     * getInfoCollectResource
     *
     * @param map map
     * @return Observable
     */
    @GET("APP/InspectionRecord/GetResource")
    Observable<BaseBean<InformationTypeOneSecondBean>> getInfoCollectResource(@QueryMap Map<String, Object> map);

    /**
     * getGridList
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET("APP/StandardAddress/GetGrid")
    Observable<BaseBean<UserGideBean>> getGridList(@QueryMap Map<String, Object> paramSign);


    /**
     * 获取点位详细信息
     * GetPointsInfo
     */
    @POST(path + "GetPointsInfo")
    Observable<BaseBean<List<PointsDetailBean>>> getPointsInfo(@QueryMap Map<String, Object> paramSign);
}
