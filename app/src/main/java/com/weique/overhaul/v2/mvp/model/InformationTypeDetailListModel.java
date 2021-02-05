package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.InformationTypeFirstContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationCollectionActivity;

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
public class InformationTypeDetailListModel extends BaseModel implements InformationTypeFirstContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public InformationTypeDetailListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;


        this.mApplication = null;
    }


    /**
     * 获取 树形  信息采集类型
     *
     * @return Observable
     */
    @Override
    public Observable<BaseBean<InformationTypeOneSecondBean>> getElementTypes(String id) {
        Map<String, Object> map = new HashMap<>(8);
        if (InformationCollectionActivity.mType >= InformationCollectionActivity.INTERVIEW) {
            map.put("ElementId", id);
            map.put("type", InformationCollectionActivity.mType);
        } else {
            map.put("ElementId", id);
        }
        return mRepositoryManager.
                obtainRetrofitService(InformationService.class).
                getInfoCollectResource(SignUtil.paramSign(map));
    }

}