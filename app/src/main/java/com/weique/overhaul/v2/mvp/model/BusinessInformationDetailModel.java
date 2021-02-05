package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.BusinessInformationDetailContract;
import com.weique.overhaul.v2.mvp.model.api.service.EnterpiseService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.BusinessInformationDetailBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 17:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class BusinessInformationDetailModel extends BaseModel implements BusinessInformationDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BusinessInformationDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<BusinessInformationDetailBean>> getBusinessInformationDetailListData(int pageSize, int ignoreNumber, String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("id", id);
        return mRepositoryManager
                .obtainRetrofitService(EnterpiseService.class)
                .getBusinessInformationDetailListData(SignUtil.paramSign(map));
    }
}