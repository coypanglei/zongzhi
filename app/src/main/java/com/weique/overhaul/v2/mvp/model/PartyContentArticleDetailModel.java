package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.PartyContentArticleDetailContract;
import com.weique.overhaul.v2.mvp.model.api.service.PartyService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyDetailBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@ActivityScope
public class PartyContentArticleDetailModel extends BaseModel implements PartyContentArticleDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PartyContentArticleDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 获取文章详情
     *
     * @param id id
     * @return Observable
     */
    @Override
    public Observable<BaseBean<PartyDetailBean>> getGetNewsDetail(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        return mRepositoryManager.obtainRetrofitService(PartyService.class)
                .getPartyDataNewsDetailById(SignUtil.paramSign(map));
    }

    /**
     * 主题教育详情
     *
     * @param id id
     * @return Observable
     */
    @Override
    public Observable<BaseBean<PartyDetailBean>> getGetSubeDetail(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        return mRepositoryManager.obtainRetrofitService(PartyService.class)
                .getSubjectEducationById(SignUtil.paramSign(map));
    }

    /**
     * 通知公告详情
     *
     * @param id id
     * @return Observable
     */
    @Override
    public Observable<BaseBean<PartyDetailBean>> getGetNotificationDetail(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        return mRepositoryManager.obtainRetrofitService(PartyService.class).getNotificationById(SignUtil.paramSign(map));
    }

    /**
     * 通知党建活动
     *
     * @param id id
     * @return Observable
     */
    @Override
    public Observable<BaseBean<PartyDetailBean>> GetPartyMeetingById(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        return mRepositoryManager.obtainRetrofitService(PartyService.class).getPartyMeetingById(SignUtil.paramSign(map));
    }

    /**
     * 文章是否收藏接口
     *
     * @return Observable
     */
    @Override
    public Observable<BaseBean<Integer>> getCollectStatus(String infoId, boolean status) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("InfoId", infoId);
        map.put("State", status);
        return mRepositoryManager.obtainRetrofitService(PartyService.class).getCollectStatus(SignUtil.paramSign(map));
    }

    /**
     * 文章取消收藏
     *
     * @return Observable
     */
    @Override
    public Observable<BaseBean<Object>> removeCollectStatus(String infoId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("InfoId", infoId);
        return mRepositoryManager.obtainRetrofitService(PartyService.class).removeCollectStatus(SignUtil.paramSign(map));
    }
}