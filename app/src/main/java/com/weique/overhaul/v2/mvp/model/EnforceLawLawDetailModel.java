package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawDetailContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.LawDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class EnforceLawLawDetailModel extends BaseModel implements EnforceLawLawDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EnforceLawLawDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<LawDetailBean>> getLawDetailById(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", id);

        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .getLawDetailById(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getLegalOperation(int ignoreNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class).getLegalOperation(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<NameAndIdBean>>> getAuditorList(String departId, int ignoreNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("DepartId", departId);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("PageSize", pageSize);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .getAuditorList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<Object>> submitManageInfo(Map<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .submitManageInfo(SignUtil.paramSign(map));
    }
}