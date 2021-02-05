package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.ARouerConstant;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.common.RouterHub;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.SearchEconoicManageMentContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.StandardAddressService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressAreaBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 10:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SearchEconoicManageMentModel extends BaseModel implements SearchEconoicManageMentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SearchEconoicManageMentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getProjectList(String dId, String name, int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("Keyword", name);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put(Constant.DEPARTMENT_ID, dId);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).searchEntInfos(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getEnterpriseList(String dId, String name, int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>();
        map.put("Keyword", name);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        map.put(Constant.DEPARTMENT_ID, dId);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getEnterpriseList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<StandardAddressAreaBean>> getAreaList(String departmentId) {
        Map<String, Object> map = new HashMap<>();
        map.put(Constant.DEPARTMENT_ID, departmentId);
        return mRepositoryManager
                .obtainRetrofitService(StandardAddressService.class)
                .getDepartment(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> deleteInfo(String id,int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("Id", id);
        if (type == 1) {

            return mRepositoryManager
                    .obtainRetrofitService(CaseReportedService.class)
                    .deleteEnterpriseInfo(SignUtil.paramSign(map));
        } else {
            return mRepositoryManager
                    .obtainRetrofitService(CaseReportedService.class)
                    .deleteProjectInfo(SignUtil.paramSign(map));
        }


    }
}