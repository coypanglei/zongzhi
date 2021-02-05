package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.ObjectToMapUtils;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementBaseInfoContract;
import com.weique.overhaul.v2.mvp.model.entity.BasicInformationBean;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;


/**
 * ================================================
 * ================================================
 *
 * @author Administrator
 */
@FragmentScope
public class EconomicManagementBaseInfoPresenter extends ReworkBasePresenter<EconomicManagementBaseInfoContract.Model, EconomicManagementBaseInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EconomicManagementBaseInfoPresenter(EconomicManagementBaseInfoContract.Model model, EconomicManagementBaseInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getInfoById(String id) {
        commonGetData(mModel.getProjectInfo(id), mErrorHandler, bean -> {
            try {
                setData(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getEntinfoById(String id) {
        commonGetData(mModel.getEntInfo(id), mErrorHandler, bean -> {
            try {
                setData(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getMatterById(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("type", "0");
        commonGetData(mModel.getMatterInfo(map), mErrorHandler, bean -> {
            try {
                setData(bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void setData(Object object){
        try {
            Map<String, String> map = ObjectToMapUtils.str2Map(object);
            for (BasicInformationBean.RecordsBean bean : mRootView.getAdapter().getData()) {
                if (map.containsKey(bean.getName())) {
                    Timber.e(bean.getName());
                    Timber.e(map.get(bean.getName()));
                    bean.setValue(map.get(bean.getName()));
                }
            }
           mRootView.getAdapter().setNewData(mRootView.getAdapter().getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
