package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.MapUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.ContradictionAddContract;
import com.weique.overhaul.v2.mvp.model.api.service.ContradictionService;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionAddBean;
import com.weique.overhaul.v2.mvp.model.entity.ContradictionRecordBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2020 10:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ContradictionAddModel extends BaseModel implements ContradictionAddContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ContradictionAddModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseBean<String>> setContradiction(ContradictionAddBean contradictionAddBean) {
        Map<String, Object> map = MapUtils.entityToMap(contradictionAddBean);
        return mRepositoryManager
                .obtainRetrofitService(ContradictionService.class)
                .setContradictionAddData(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<ContradictionRecordBean>> getContradictionRecord(String id) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", id);
        return mRepositoryManager
                .obtainRetrofitService(ContradictionService.class)
                .getContradictionRecord(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> DeleteContradictionRecord(String resetId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", resetId);
        return mRepositoryManager.obtainRetrofitService(ContradictionService.class).DeleteContradictionRecord(SignUtil.paramSign(map));

    }

    @Override
    public Observable<BaseBean<String>> InvalidContradictionRecord(String resetId) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("id", resetId);
        return mRepositoryManager.obtainRetrofitService(ContradictionService.class).InvalidContradictionRecord(SignUtil.paramSign(map));

    }

    @Override
    public Observable<BaseBean<String>> setResetContradiction(ContradictionAddBean stagingBean) {
        Map<String, Object> map = MapUtils.entityToMap(stagingBean);
        return mRepositoryManager.obtainRetrofitService(ContradictionService.class).setResetContradiction(SignUtil.paramSign(map));

    }


}