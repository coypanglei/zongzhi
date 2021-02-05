package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.MainContract;
import com.weique.overhaul.v2.mvp.model.api.service.AddressLookService;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.NewVersionInfoBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<String>> automaticCheckInOrder(String pointsInJson, String address) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PointsInJson", pointsInJson);
        map.put("Address", address);
        return mRepositoryManager
                .obtainRetrofitService(AddressLookService.class)
                .automaticCheckInOrder(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<NewVersionInfoBean>> getAppVersionInfo(Map<String, Object> map) {
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getAppVersionInfo(SignUtil.paramSign(map));
    }

    /**
     * 获取消息状态  是否有新消息
     *
     * @return Observable
     */
    @Override
    public Observable<BaseBean<Boolean>> getMessageStatus() {
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getMessageStatus(SignUtil.paramSign(null));
    }

    @Override
    public Observable<BaseBean<Object>> sgtToZzBinding(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("SGTId", id);
        map.put("UserId", UserInfoUtil.getUserInfo().getUid());
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .sgtToZzBinding(SignUtil.paramSign(map));

    }
}