package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawListContract;
import com.weique.overhaul.v2.mvp.model.api.service.CaseReportedService;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.CaseReportedBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonTitleBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class EnforceLawLawListModel extends BaseModel implements EnforceLawLawListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EnforceLawLawListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseBean<List<CaseReportedBean>>> getCaseList(String keyword, String type, String sortType, int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("keyword", keyword);
        map.put("EnumCaseSource", type);
        map.put("EnumComprehensiveLawEnforcementCaseStatus", sortType);
        map.put("IgnoreNumber", ignoreNumber);
        map.put("PageSize", pageSize);
        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .getCaseList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<CommonTitleBean>>> getCommonEnums(String type) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("type", type);

        return mRepositoryManager.obtainRetrofitService(CaseReportedService.class)
                .getTitles(SignUtil.paramSign(map));
    }
}