package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.SearchCollectionContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2020 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SearchCollectionPresenter extends ReworkBasePresenter<SearchCollectionContract.Model, SearchCollectionContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SearchCollectionPresenter(SearchCollectionContract.Model model, SearchCollectionContract.View rootView) {
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
     * @param name   名称
     */
    public void getElementTypes(String name, boolean needAdd) {
        handlePaging(false, needAdd);
        commonGetData(mModel.getElementTypesByName(name), mErrorHandler, informationTypeOneSecondBean -> {
            try {
                if (informationTypeOneSecondBean == null ||
                        informationTypeOneSecondBean.getElementTypeList() == null ||
                        informationTypeOneSecondBean.getElementTypeList().size() <= 0) {
                    mRootView.setDataTree(informationTypeOneSecondBean.getElementTypeList(), needAdd);
                    return;
                }

                if("".equals(name)){
                    mRootView.setAllDataTree(informationTypeOneSecondBean.getElementTypeList());
                }else {
                    mRootView.setDataTree(informationTypeOneSecondBean.getElementTypeList(), needAdd);
                }
                handlePaginLoadMore((informationTypeOneSecondBean == null ||
                        informationTypeOneSecondBean.getElementTypeList() == null) ? 0 :
                        informationTypeOneSecondBean.getElementTypeList().size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}
