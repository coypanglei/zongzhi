package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.MapUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawAddContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.InformationService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;
import com.weique.overhaul.v2.mvp.model.entity.GridInformationBean;
import com.weique.overhaul.v2.mvp.model.entity.MatterSecondBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.PartiesBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
public class EnforceLawLawAddModel extends BaseModel implements EnforceLawLawAddContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EnforceLawLawAddModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<List<MatterSecondBean>>> getMatterList(int ignoreNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getMatterList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<MatterSecondBean>>> getMatterSecondList(String elementId) {
        Map<String, Object> map = new HashMap<>();
        map.put("ComprehensiveLawEnforcementMattersTypeId", elementId);
        map.put("IgnoreNumber", 0);
        map.put("PageSize", 200);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getMatterSecondList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getLawSort() {

        Map<String, Object> map = new HashMap<>();
        map.put("IgnoreNumber", 0);
        map.put("PageSize", 200);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getLawSort(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getLegalOperation(int ignoreNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("PageSize", 200);
        map.put("IgnoreNumber", 0);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getLegalOperation(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<CommonTitleBean>>> getCommonEnums(String type) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("type", type);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .getTitles(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<PartiesBean>>> getPartiesList(int ignoreNumber, int pageSize, String text) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("PageSize", pageSize);
        map.put("keyword", text);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .getPartiesList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> submitPartiesInfo(PartiesBean partiesBean) {
        Map<String, Object> map = MapUtils.entityToMap(partiesBean);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .submitPartiesInfo(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getAuditorList(String departId, int ignoreNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("DepartId", departId);
        map.put("IgnoreNumber", 0);
        map.put("PageSize", 200);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .getAuditorList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> submitLaw(Map<String, Object> allMap) {
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .submitLaw(SignUtil.paramSign(allMap));
    }

    @Override
    public Observable<BaseBean<GridInformationBean>> getAreaMapByDepartmentId(String departmentId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put(Constant.DEPARTMENT_ID, departmentId);
        return mRepositoryManager.obtainRetrofitService(InformationService.class).getDepartment(SignUtil.paramSign(map));
    }
}