package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.mvp.contract.MatterListContract;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2020 11:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MatterListPresenter extends ReworkBasePresenter<MatterListContract.Model, MatterListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MatterListPresenter(MatterListContract.Model model, MatterListContract.View rootView) {
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

    public void getList(boolean pullToRefresh, boolean isLoadMore, int flag, int orderStatus, int dataSource, String keyword) {
        if (flag == 1) {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getMyCooperListByReceiveUserId(pageSize, ignoreNumber, flag, orderStatus, dataSource, keyword), mErrorHandler, t -> {
                mRootView.setList(t.getList(), isLoadMore);
                handlePaginLoadMore((t == null || t.getList() == null) ? 0 : t.getList().size());
            });
        } else if (flag == 0) {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getMyProceedListByUserId(pageSize, ignoreNumber, flag, orderStatus, dataSource, keyword), mErrorHandler, t -> {
                mRootView.setList(t.getList(), isLoadMore);
                handlePaginLoadMore((t == null || t.getList() == null) ? 0 : t.getList().size());
            });
        } else {
            handlePaging(pullToRefresh, isLoadMore);
            commonGetData(mModel.getList(pageSize, ignoreNumber, flag, orderStatus, dataSource, keyword), mErrorHandler, t -> {
                mRootView.setList(t.getList(), isLoadMore);
                handlePaginLoadMore((t == null || t.getList() == null) ? 0 : t.getList().size());
            });
        }
    }

    /**
     * @param enumType enumType
     */
    public void getEnum(String enumType) {
//        List<CommonEnumBean> beans = new ArrayList<>();
//        beans.add(new CommonEnumBean("等待受理", 1));
//        beans.add(new CommonEnumBean("正在处置", 3));
//        mRootView.setEnum(beans);
        commonGetData(mModel.getEnum(enumType), mErrorHandler, o -> {
            mRootView.setEnum(o);
        });
    }
}
