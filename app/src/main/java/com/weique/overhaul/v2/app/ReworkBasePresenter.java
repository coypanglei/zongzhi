package com.weique.overhaul.v2.app;

import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.app.utils.NetworkUtils;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

/**
 * http相关请求再这里统一处理
 *
 * @author GK
 */
public class ReworkBasePresenter<M extends IModel, V extends IView> extends BasePresenter {


    protected M mModel;
    protected V mRootView;


    public static final int pageSize = 20;
    protected int ignoreNumber = 0;

    @Inject
    AppManager mAppManager;

    /**
     * 进度监控处理  回调
     *
     * @param <T>
     */
    public interface ProgressMonitorHandle<T> {
        /**
         * 从 响应中 获取body 返还界面
         *
         * @param t body
         */
        void getBodyFromObject(T t);

    }

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param rootView
     */
    public ReworkBasePresenter(M model, V rootView) {
        super(model, rootView);
        this.mModel = model;
        this.mRootView = rootView;
    }

    public static final String TAG = "接口log";

    /**
     * 通用获取
     *
     * @param observable    observable
     * @param mErrorHandler mErrorHandler
     * @param <T>           <T>
     */
    protected <T> void commonGetData(Observable<BaseBean<T>> observable,
                                     RxErrorHandler mErrorHandler,
                                     @Nullable ProgressMonitorHandle<T> handle) {
        try {
            NetworkUtils.commonGetData(observable, mErrorHandler, mRootView, handle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 protected <T> void commonGetDataNoProgress(Observable<BaseBean<T>> observable,
                                     RxErrorHandler mErrorHandler,
                                     @Nullable ProgressMonitorHandle<T> handle) {
        try {
            NetworkUtils.commonGetDataNoProgress(observable, mErrorHandler, mRootView, handle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理分页数据
     *
     * @param pullToRefresh pullToRefresh
     * @param isLoadMore    isLoadMore
     */
    protected void handlePaging(boolean pullToRefresh, boolean isLoadMore) {
        if (pullToRefresh && isLoadMore) {
            Timber.w("不可以下拉刷新和上拉加载同时进行");
            return;
        }
        if (pullToRefresh) {
            ignoreNumber = 0;
        }
        if (isLoadMore) {
            ignoreNumber += 1;
        }
    }

    /**
     * 处理分页数据
     *
     * @param pullToRefresh pullToRefresh 是否是下拉刷新(即：第一次加载列表)  false 是加载更多
     */
    public void handlePaging(boolean pullToRefresh) {
        if (pullToRefresh) {
            ignoreNumber = 0;
        }else {
            ignoreNumber += 1;
        }
    }


    /**
     * 判断处理  加载更多 的显示个隐藏
     * 因为后端页码是从0开始 所以减一
     *
     * @param dataListSize 返回数据的 多少
     */
    protected void handlePaginLoadMore(int dataListSize) {
        if (pageSize > dataListSize) {
            mRootView.LoadingMore(true);
        } else {
            mRootView.LoadingMore(false);
        }
    }
}
