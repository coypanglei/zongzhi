package com.weique.overhaul.v2.mvp.model.api.service;

import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author GK
 * @description:
 * @date :2020/8/25 10:32
 */
public interface CommonRequestServer {

    /**
     * 公共请求
     *
     * @param url url
     * @param map map
     * @return Observable
     */
    @FormUrlEncoded
    @POST("{path}")
    Observable<BaseBean<NameAndIdBean>> commonRequestGet(@Path(value = "path",encoded = true) String url, @FieldMap Map<String, Object> map);
}
