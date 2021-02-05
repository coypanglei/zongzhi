package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueContract;
import com.weique.overhaul.v2.mvp.model.api.service.EnterpiseService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.EnterpriseIssueListBean;
import com.weique.overhaul.v2.mvp.model.entity.LeaderBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/09/2020 14:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class EnterpriseIssueModel extends BaseModel implements EnterpriseIssueContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EnterpriseIssueModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 企业端 获取 企业问题 列表
     *
     * @param ignoreNumber ignoreNumber
     * @param pageSize     pageSize
     * @return Observable
     */
    @Override
    public Observable<BaseBean<EnterpriseIssueListBean>> getEnterpriseIssueList(int ignoreNumber, int pageSize) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager.obtainRetrofitService(EnterpiseService.class).getEnterpriseIssueList(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<LeaderBean>> getMyEntLeaderInfo() {
        return mRepositoryManager.obtainRetrofitService(EnterpiseService.class).getMyEntLeaderInfo(SignUtil.paramSign(null));
    }
}