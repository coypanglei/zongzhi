package com.weique.overhaul.v2.dynamic.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.blankj.utilcode.util.ObjectUtils;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.utils.NetworkUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.dynamic.BaseBinderAdapterBean;
import com.weique.overhaul.v2.dynamic.entity.CommonBackBean;
import com.weique.overhaul.v2.dynamic.mvp.contract.CommonRefreshContract;
import com.weique.overhaul.v2.mvp.model.api.service.MainService;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.CommonCollectBean;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/04/2021 14:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CommonRefreshPresenter extends ReworkBasePresenter<CommonRefreshContract.Model, CommonRefreshContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    IRepositoryManager iRepositoryManager;
    @Inject
    Gson gson;

    @Inject
    CommonRefreshPresenter(CommonRefreshContract.Model model, CommonRefreshContract.View rootView) {
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
     * 根据不同的接口获取数据 生成列表
     */
    public void getAllData(CommonCollectBean commonCollectBean, boolean pullToRefresh) {


        try {
             handlePaging(pullToRefresh, false);
            /*
             *  接口获取 需要传入 接口类型
             */
            Observable<BaseBean<CommonBackBean>> observable = iRepositoryManager
                    .obtainRetrofitService(MainService.class)
                    // commonCollectBean.getPath() 接口名称   commonCollectBean.getMap() 接口参数
                    .getCommonObject(commonCollectBean.getPath(), SignUtil.paramSign(commonCollectBean.getMap()));
            NetworkUtils.commonGetNetworkData(observable,
                    mErrorHandler, mRootView, beans -> {
                        try {
                            List<Object> list = beans.getList();
                            if (ObjectUtils.isNotEmpty(list) && list.size() > 0) {
                                /*
                                 *刷新 清空数据
                                 *
                                 */
                                if (pullToRefresh) {
                                    mRootView.getBinderAdapter().getData().clear();
                                }
                                /*
                                 *  加载数据
                                 */
                                for (Object object : list) {
                                    mRootView.getBinderAdapter().getData().add(gson.fromJson(gson.toJson(object), commonCollectBean.getClassName()));
                                }
                                mRootView.getBinderAdapter().setList(mRootView.getBinderAdapter().getData());
                            } else {
                                /*
                                 * 刷新过后如果没有数据 显示 空布局
                                 */
                                if (pullToRefresh) {
                                    Objects.requireNonNull(mRootView.getBinderAdapter().getEmptyLayout()).setVisibility(View.VISIBLE);
                                }
                            }
                            handlePaginLoadMore((beans == null || beans.getList() == null) ? 0 : beans.getList().size());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加布局
     *
     * @param commonCollectBean  公共采集参数
     */
    public void addItemBinders(CommonCollectBean commonCollectBean) {
        try {
            /*
               获取BindBeanList
             */
            List<BaseBinderAdapterBean> list = commonCollectBean.getBindBeanList();
            /*
              Adapter循环加入Binder
             */
            for (int i = 0; i < list.size(); i++) {
                mRootView.getBinderAdapter().addItemBinder(list.get(i).getaClass(), list.get(i).getBaseItemBinder(), list.get(i).getDiffItem());
            }
            mRootView.getBinderAdapter().getLoadMoreModule().setOnLoadMoreListener(() -> getAllData(commonCollectBean, false));
            mRootView.getBinderAdapter().setEmptyView(R.layout.null_content_layout);
            Objects.requireNonNull(mRootView.getBinderAdapter().getEmptyLayout()).setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
