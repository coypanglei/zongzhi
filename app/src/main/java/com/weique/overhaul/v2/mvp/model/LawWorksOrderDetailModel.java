package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.LawWorksOrderDetailContract;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.LawWorksOrderDetailBean;
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
 * Created by MVPArmsTemplate on 04/23/2020 13:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LawWorksOrderDetailModel extends BaseModel implements LawWorksOrderDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LawWorksOrderDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<LawWorksOrderDetailBean>> getLawDetailListData(String id) {

        Map<String, Object> map = new HashMap<>(8);

        if (StringUtil.isNotNullString(id)) {
            map.put("Id", id);
        }
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getLawDetailListData(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> getOrderData(String id) {
        Map<String, Object> map = new HashMap<>(8);

        if (StringUtil.isNotNullString(id)) {
            map.put("Id", id);
        }
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getOrderData(SignUtil.paramSign(map));
    }

    @Override
    public Observable<BaseBean<String>> getOrderIntoData(String courtOrderID, String content, String imgsInJson) {
        Map<String, Object> map = new HashMap<>(8);

        if (StringUtil.isNotNullString(courtOrderID)) {
            map.put("CourtOrderID", courtOrderID);
        }

        if (StringUtil.isNotNullString(content)) {
            map.put("Content", content);
        }

        if (StringUtil.isNotNullString(imgsInJson)) {
            map.put("ImgsInJson", imgsInJson);
        }
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getOrderIntoData(SignUtil.paramSign(map));

    }
}