package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementTableContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 13:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class EconomicManagementTablePresenter extends ReworkBasePresenter<EconomicManagementTableContract.Model, EconomicManagementTableContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EconomicManagementTablePresenter(EconomicManagementTableContract.Model model, EconomicManagementTableContract.View rootView) {
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

    /**
     * 获取表格数据
     *
     * @param id   公司id
     * @param type 表格类型
     */
    public void getTabledata(String id, int type) {
        if (type > 4) {
            commonGetData(mModel.getProjectItems(id, type + ""), mErrorHandler, bean -> {
                try {
                    mRootView.setProjectTable(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            commonGetData(mModel.getEntInfoItem(id, type + ""), mErrorHandler, bean -> {
                try {
                    mRootView.setTable(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }


    /**
     * 修改包挂领导
     *
     * @param id
     * @param name
     */
    public void createCharger(String id, String name) {
        commonGetData(mModel.createCharger(id, name), mErrorHandler, bean -> {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
