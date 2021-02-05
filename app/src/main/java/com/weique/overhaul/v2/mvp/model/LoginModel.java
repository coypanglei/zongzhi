package com.weique.overhaul.v2.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.contract.LoginContract;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.GlobalUserInfoBean;
import com.weique.overhaul.v2.mvp.model.entity.User;

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
public class LoginModel extends BaseModel implements LoginContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseBean<User>> login(String acc, String pss) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("userName", acc);
        map.put("password", pss);
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getUser(map);
    }

    /**
     * 图文验证码 登录
     *
     * @return Observable
     */
    @Override
    public Observable<BaseBean<GlobalUserInfoBean>> getValidateCodeLogin(String phone, String password) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("loginName", phone);
        map.put("loginPsd", password);
        return mRepositoryManager
                .obtainRetrofitService(MainService.class)
                .getValidateCodeLogin(SignUtil.paramSign(map));
    }
}