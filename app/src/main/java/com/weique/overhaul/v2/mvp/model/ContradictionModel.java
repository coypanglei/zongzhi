package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.ContradictionContract;
import com.weique.overhaul.v2.mvp.model.api.service.ContradictionService;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionItemBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;

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
public class ContradictionModel extends BaseModel implements ContradictionContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ContradictionModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<ContradictionItemBean>> getContradictionListData(int pageSize,int ignoreNumber, String standardAddress,String status) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("StandardAddress", standardAddress);
        map.put("status", status);
        return mRepositoryManager
                .obtainRetrofitService(ContradictionService.class)
                .getContradictionListData(SignUtil.paramSign(map));
    }



    @Override
    public Observable<BaseBean<GridInformationBean>> getGetDepartment(String departmentId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(Constant.DEPARTMENT_ID, departmentId);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(map));
    }
}