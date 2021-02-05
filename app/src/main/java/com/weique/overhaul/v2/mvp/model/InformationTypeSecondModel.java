package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.blankj.utilcode.util.ObjectUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.contract.InformationTypeSecondContract;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.InformationTypeOneSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;
import com.weique.overhaul.v2.mvp.model.entity.UserGideBean;

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
public class InformationTypeSecondModel extends BaseModel implements InformationTypeSecondContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public InformationTypeSecondModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<InformationTypeOneSecondBean>> getGridResource(String id, String query, String departmentId, String standardAddressId, int pageSize, int ignoreNumber, Map addMap) {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("ElementId", id);
            map.put("DepartmentId", departmentId);
            map.put("StandardAddressId", standardAddressId);
            map.put("Name", query);
            map.put("PageSize", pageSize);
            map.put("IgnoreNumber", ignoreNumber);
            if (ObjectUtils.isNotEmpty(addMap)) {
                map.putAll(addMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mRepositoryManager
                .obtainRetrofitService(InformationService.class)
                .getGridResource(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> getGetNewGuidForApp() {
        return mRepositoryManager
                .obtainRetrofitService(InformationService.class)
                .getNewGuidForApp(SignUtil.paramSign(null));
    }

    @Override
    public Observable<BaseBean<UserGideBean>> getGridList(int pageSize, int ignoreNumber, String keyword) {
        Map<String, Object> map = new HashMap<>();
        map.put("Keyword", keyword);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getGridList(SignUtil.paramSign(map));
    }

    /**
     * @param pageSize
     * @param ignoreNumber
     * @param departmentId
     * @param keyWord
     * @return
     */
    @Override
    public Observable<BaseBean<StandardAddressStairBean>> getStandardAddressByGridId(int pageSize, int ignoreNumber, String departmentId, String keyWord) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("GridId", departmentId);
        map.put("Name", keyWord);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(StandardAddressService.class)
                .getGridingFourRace(SignUtil.paramSign(map));
    }
}