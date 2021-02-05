package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.KnowledgeBaseContract;
import com.weique.overhaul.v2.mvp.model.api.service.KnowledgeService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBaseBean;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2019 09:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class KnowledgeBaseModel extends BaseModel implements KnowledgeBaseContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public KnowledgeBaseModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<KnowledgeBaseBean>> getKnowledgeBaseListData(int pageSize,int ignoreNumber, String TId, String LId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        if (StringUtil.isNotNullString(TId)){
            map.put("TId", TId);
        }
        if (StringUtil.isNotNullString(LId)){
            map.put("LId", LId);
        }
        return mRepositoryManager
                .obtainRetrofitService(KnowledgeService.class)
                .getKnowledgeBaseListData(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<List<KnowledgeBean>>> getLabelsListData() {
        return mRepositoryManager.obtainRetrofitService(KnowledgeService.class).getLabelsListData(SignUtil.paramSign(null));

    }

    @Override
    public Observable<BaseBean<List<KnowledgeBean>>> getTypeListData() {
        return mRepositoryManager.obtainRetrofitService(KnowledgeService.class).getTypeListData(SignUtil.paramSign(null));
    }
}