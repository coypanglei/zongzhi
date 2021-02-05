package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.PartySearchContract;
import com.weique.overhaul.v2.mvp.model.api.service.PartyService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.PartyContentItemBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/14/2019 10:54
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PartySearchModel extends BaseModel implements PartySearchContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PartySearchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 获取新闻列表
     *
     * @param typeId       typeId
     * @param keyWord      keyWord
     * @param pageSize     pageSize
     * @param ignoreNumber ignoreNumber
     * @return Observable
     */
    @Override
    public Observable<BaseBean<PartyContentItemBean>> getListByKeyword(String typeId, String keyWord, int pageSize, int ignoreNumber) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("InfoTypeId", typeId);
        map.put("Title", keyWord);
        map.put("PageSize", pageSize);
        map.put("IgnoreNumber", ignoreNumber);
        return mRepositoryManager
                .obtainRetrofitService(PartyService.class)
                .getPartyDataNews(SignUtil.paramSign(map));
    }
}