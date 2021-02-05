package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.MessageDetailContract;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
public class MessageDetailModel extends BaseModel implements MessageDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 设置消息已读
     *
     * @param id id
     * @return return
     */
    @Override
    public Observable<BaseBean<Object>> setMessageIsReading(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("Id", id);
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .setMessageIsReading(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> replyMessage(Map<String, Object> map) {
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .replyMessage(SignUtil.paramSign(map));
    }
}