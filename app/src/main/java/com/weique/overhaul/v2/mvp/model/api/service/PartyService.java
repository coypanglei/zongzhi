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

import com.weique.overhaul.v2.mvp.model.entity.IntegralBean;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyAnswerItemBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyCenterRecommendedBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.QuestionDetailAnswerItemBean;
import com.weique.overhaul.v2.mvp.model.entity.QuestionStudyItemBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * ================================================
 * 展示 {@link Retrofit#create(Class)} 中需要传入的 ApiService 的使用方式
 * 存放关于用户的一些 API
 * <p>
 * ================================================
 *
 * @author GK
 */
public interface PartyService {
    String path = "app/PartyBuilder/";

    /**
     * 党建首页推荐数据
     *
     * @return Observable
     */
    @GET(path + "index")
    Observable<BaseBean<PartyCenterRecommendedBean>> getPartyMain(@QueryMap Map<String, Object> map);

    /**
     * 党建分类页数据
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetType")
    Observable<BaseBean<PartyCenterRecommendedBean>> getPartyTable(@QueryMap Map<String, Object> map);

    /**
     * 党建分类页新闻中心(非推荐)列表数据
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetNews")
    Observable<BaseBean<PartyContentItemBean>> getPartyDataNews(@QueryMap Map<String, Object> map);


    /**
     * 党建分类页新闻中心(非推荐)详情数据
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetInfoById")
    Observable<BaseBean<PartyDetailBean>> getPartyDataNewsDetailById(@QueryMap Map<String, Object> map);

    /**
     * 党建分类页主题教育列表数据
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetSubjectEducation")
    Observable<BaseBean<PartyContentItemBean>> getPartyDataSubs(@QueryMap Map<String, Object> map);

    /**
     * 党建分类页根据Id获取主题教育详细信息
     *
     * @param map map
     * @return Observableg
     */
    @GET(path + "GetSubjectEducationById")
    Observable<BaseBean<PartyDetailBean>> getSubjectEducationById(@QueryMap Map<String, Object> map);

    /**
     * 党建分类页通知公告列表数据
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetNotification")
    Observable<BaseBean<PartyContentItemBean>> getPartyDataNotice(@QueryMap Map<String, Object> map);


    /**
     * 党建分类页根据Id获取通知公告详细信息
     *
     * @param map map
     * @return Observableg
     */
    @GET(path + "GetNotificationById")
    Observable<BaseBean<PartyDetailBean>> getNotificationById(@QueryMap Map<String, Object> map);

    /**
     * 党建分类页党建活动列表数据
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "getPartyMeeting")
    Observable<BaseBean<PartyContentItemBean>> getPartyMeeting(@QueryMap Map<String, Object> map);


    /**
     * 党建分类页根据Id获取通知公告详细信息
     *
     * @param map map
     * @return Observableg
     */
    @GET(path + "GetPartyMeetingById")
    Observable<BaseBean<PartyDetailBean>> getPartyMeetingById(@QueryMap Map<String, Object> map);

    /**
     * 文章添加收藏
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "Favorite")
    Observable<BaseBean<Integer>> getCollectStatus(@QueryMap Map<String, Object> map);

    /**
     * 文章移除收藏
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "CancelFavorite")
    Observable<BaseBean<Object>> removeCollectStatus(@QueryMap Map<String, Object> map);

    /**
     * 党建答题系统题目列表
     *
     * @param map map
     * @return PartyAnswerItemBean
     */
    @GET(path + "GetSubject")
    Observable<BaseBean<PartyAnswerItemBean>> getPartyAnswerListData(@QueryMap Map<String, Object> map);


    /**
     * 党建答题系统题目列表--题目详情
     *
     * @param map map
     * @return QuestionDetailAnswerItemBean
     */
    @GET(path + "GetSubjectDetail")
    Observable<BaseBean<List<QuestionDetailAnswerItemBean>>> getPartyAnswerDetailData(@QueryMap Map<String, Object> map);

    /**
     * 党建答题系统题目列表--学习内容
     *
     * @param map map
     * @return
     */
    @GET(path + "GetStudyPeriod")
    Observable<BaseBean<QuestionStudyItemBean>> getPartyAnswerStudyData(@QueryMap Map<String, Object> map);

    /**
     * 收藏列表
     *
     * @param map map
     * @return Observable
     */
    @GET(path + "GetFavorite")
    Observable<BaseBean<PartyContentItemBean>> getCollectList(@QueryMap Map<String, Object> map);

    /**
     * 党建--提交答案
     *
     * @param map map
     * @return IntegralBean
     */
    @GET(path + "ExamDetail")
    Observable<BaseBean<IntegralBean>> getPartyAnswer(@QueryMap Map<String, Object> map);
}
