package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.PartContentContract;
import com.weique.overhaul.v2.mvp.model.api.service.PartyService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;

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
@FragmentScope
public class PartContentModel extends BaseModel implements PartContentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PartContentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<PartyContentItemBean>> getPartyDataNews(String typeId,
                                                                       int pageSize,
                                                                       int ignoreNumber) {

        Map<String, Object> map = new HashMap<>(8);
        map.put("InfoTypeId", typeId);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(PartyService.class)
                .getPartyDataNews(SignUtil.paramSign(map));
    }

    /**
     * 获取 主题教育列表
     *
     * @param typeId typeId
     * @return Observable
     */
    @Override
    public Observable<BaseBean<PartyContentItemBean>> getPartyDataSubs(String typeId,
                                                                       int pageSize,
                                                                       int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("SubjectEducationTypeId", typeId);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(PartyService.class)
                .getPartyDataSubs(SignUtil.paramSign(map));
    }

    /**
     * 获取 通知消息列表
     *
     * @param typeId typeId
     * @return Observable
     */
    @Override
    public Observable<BaseBean<PartyContentItemBean>> getPartyDataNotice(String typeId,
                                                                         int pageSize,
                                                                         int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("NotificationTypeId", typeId);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(PartyService.class)
                .getPartyDataNotice(SignUtil.paramSign(map));
    }

    /**
     * 获取 党建活动
     *
     * @param typeId typeId
     * @return Observable
     */
    @Override
    public Observable<BaseBean<PartyContentItemBean>> getPartyActivity(String typeId,
                                                                       int pageSize,
                                                                       int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PartyMeetingTypeId", typeId);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(PartyService.class)
                .getPartyMeeting(SignUtil.paramSign(map));
    }
}