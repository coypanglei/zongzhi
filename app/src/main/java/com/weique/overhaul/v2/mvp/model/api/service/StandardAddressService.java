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
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressAreaBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressUpItemBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * 标准地址  相关 接口
 * <p>
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * <p>
 * ================================================
 *
 * @author GK
 */
public interface StandardAddressService {
    String path = "app/StandardAddress/";

    /**
     * 根据用户id 获取其上下级信息
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getDepartment")
    Observable<BaseBean<StandardAddressStairBean>> getUpDownRankInfo(@QueryMap Map<String, Object> map);

    /**
     * 根据用户id 获取其上下级信息
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getDepartment")
    Observable<BaseBean<StandardAddressAreaBean>> getDepartment(@QueryMap Map<String, Object> map);

    /**
     * 获取网格没  4或5个信息
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetStandardAddress")
    Observable<BaseBean<StandardAddressStairBean>> getGridingFourRace(@QueryMap Map<String, Object> map);

    /**
     * 获取标准地址详情
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "EditStandardAddress")
    Observable<BaseBean<StandardAddressBean>> getStandardAddressDetail(@QueryMap Map<String, Object> map);

    /**
     * 修改标准地址
     *
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "EditStandardAddress")
    Observable<BaseBean<StandardAddressStairBean.StandardAddressStairBaseBean>> alertStandardAddressDetail(@FieldMap Map<String, Object> map);


    /**
     * 新建标准地址
     *
     * @param mapn mapn
     * @return Observable
     */
    @FormUrlEncoded
    @POST(path + "CreateStandardAddress")
    Observable<BaseBean<StandardAddressStairBean.StandardAddressStairBaseBean>> addStandardAddressDetail(@FieldMap Map<String, Object> mapn);

    /**
     * 获取上级地址列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetParentsAddress")
    Observable<BaseBean<StandardAddressUpItemBean>> GetParentsStandardAddressList(@QueryMap Map<String, Object> map);

    /**
     * 获取上级地址列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "DelStandardAddress")
    Observable<BaseBean<Object>> DelStandardAddress(@QueryMap Map<String, Object> map);

    /**
     * 标准地址详情中获取  相关采集信息列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getElementByStandardAddress")
    Observable<BaseBean<List<InformationTypeOneSecondBean.ElementListBean>>> getInformationListByStarters(@QueryMap Map<String, Object> map);


    /**
     * 获取 动态表单 根据元素id
     * type {@link com.weique.overhaul.v2.app.common.EnumConstant #DynamicBeanByType}
     *
     * @param paramSign paramSign
     * @return Observable
     */
    @GET(path + "getDataStructureInJson")
    Observable<BaseBean<String>> getDataStructureInJson(@QueryMap Map<String, Object> paramSign);



}
