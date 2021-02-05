package com.weique.overhaul.v2.app.utils;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.weique.overhaul.v2.BuildConfig;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * @author GK
 * @description:
 * @date :2020/8/16 10:23
 */
public class NetworkUtils {
    public static <T> void commonGetData(Observable<BaseBean<T>> observable,
                                         RxErrorHandler mErrorHandler,
                                         IView mRootView,
                                         @Nullable ReworkBasePresenter.ProgressMonitorHandle<T> handle) {
        try {
            observable.subscribeOn(Schedulers.io())
                    //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                    .retryWhen(new RetryWithDelay(0, 2))
                    .doOnSubscribe(disposable -> mRootView.showLoading())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate(mRootView::hideLoading)
                    //使用Rxlifecycle,使Disposable和Activity一起销毁
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseBean<T>>(mErrorHandler) {
                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                            mRootView.hideLoading();
                            mRootView.LoadingMore(true);
                        }

                        @Override
                        public void onNext(BaseBean<T> bean) {
                            if (bean != null) {
                                //成功逻辑
                                if (bean.getCode() >= Constant.HTTP200 && bean.getCode() < Constant.HTTP300) {
                                    if (bean.getCode() == Constant.HTTP201 && StringUtil.isNotNullString(bean.getMessage())) {
                                        ArmsUtils.makeText(bean.getMessage());
                                    }
                                    if (handle != null) {
                                        handle.getBodyFromObject(bean.getData());
                                    }
                                } else if (bean.getCode() >= Constant.HTTP400 && bean.getCode() < Constant.HTTP500) {
                                    if (bean.getCode() == Constant.HTTP401 && StringUtil.isNotNullString(bean.getMessage())) {
                                        ArmsUtils.makeText(bean.getMessage());
                                    } else if (bean.getCode() == Constant.HTTP403) {
                                        ArmsUtils.makeText("您的账号在其它设备上登录，请重新登录");
                                        AppUtils.logout();
                                    } else if (bean.getCode() == Constant.HTTP405) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            ArmsUtils.makeText("签名验证失败");
                                        } else {
                                            ArmsUtils.makeText("请求异常");
                                        }
                                    } else if (bean.getCode() == Constant.HTTP406) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            ArmsUtils.makeText("请求已超时");
                                        } else {
                                            ArmsUtils.makeText("请求异常");
                                        }
                                    } else {
                                        ArmsUtils.makeText("请求异常 - 请联系服务人员");
                                    }
                                } else {
                                    ArmsUtils.makeText("请求异常 - 请联系服务人员");
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static <T> void commonGetDataNoProgress(Observable<BaseBean<T>> observable,
                                                   RxErrorHandler mErrorHandler,
                                                   IView mRootView,
                                                   @Nullable ReworkBasePresenter.ProgressMonitorHandle<T> handle) {
        try {
            observable.subscribeOn(Schedulers.io())
                    //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                    .retryWhen(new RetryWithDelay(0, 2))
                    .doOnSubscribe(disposable -> {/*mRootView.showLoading()*/})
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate(null/*mRootView::hideLoading*/)
                    //使用Rxlifecycle,使Disposable和Activity一起销毁
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseBean<T>>(mErrorHandler) {
                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
//                            mRootView.hideLoading();
//                            mRootView.LoadingMore(true);
                        }

                        @Override
                        public void onNext(BaseBean<T> bean) {
                            if (bean != null) {
                                //成功逻辑
                                if (bean.getCode() >= Constant.HTTP200 && bean.getCode() < Constant.HTTP300) {
                                    if (bean.getCode() == Constant.HTTP201 && StringUtil.isNotNullString(bean.getMessage())) {
                                        ArmsUtils.makeText(bean.getMessage());
                                    }
                                    if (handle != null) {
                                        handle.getBodyFromObject(bean.getData());
                                    }
                                } else if (bean.getCode() >= Constant.HTTP400 && bean.getCode() < Constant.HTTP500) {
                                    if (bean.getCode() == Constant.HTTP401 && StringUtil.isNotNullString(bean.getMessage())) {
                                        ArmsUtils.makeText(bean.getMessage());
                                    } else if (bean.getCode() == Constant.HTTP403) {
                                        ArmsUtils.makeText("您的账号在其它设备上登录，请重新登录");
                                        AppUtils.logout();
                                    } else if (bean.getCode() == Constant.HTTP405) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            ArmsUtils.makeText("签名验证失败");
                                        } else {
                                            ArmsUtils.makeText("请求异常");
                                        }
                                    } else if (bean.getCode() == Constant.HTTP406) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            ArmsUtils.makeText("请求已超时");
                                        } else {
                                            ArmsUtils.makeText("请求异常");
                                        }
                                    } else {
                                        ArmsUtils.makeText("请求异常 - 请联系服务人员");
                                    }
                                } else {
                                    ArmsUtils.makeText("请求异常 - 请联系服务人员");
                                }
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用网络请求  没有分页
     *
     * @param observable observable
     * @param handle     handle
     * @param <T>        <T>
     */
    public static <T> void commonGetNetworkData(@NotNull Observable<BaseBean<T>> observable,
                                                RxErrorHandler mErrorHandler,
                                                IView mRootView,
                                                @Nullable ReworkBasePresenter.ProgressMonitorHandle<T> handle) {
        try {
            observable.subscribeOn(Schedulers.io())
                    //遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                    .retryWhen(new RetryWithDelay(0, 10))
                    .doOnSubscribe(disposable -> mRootView.showLoading())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate(mRootView::hideLoading)
                    //使用Rxlifecycle,使Disposable和Activity一起销毁
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new ErrorHandleSubscriber<BaseBean<T>>(mErrorHandler) {
                        @Override
                        public void onError(Throwable t) {
                            super.onError(t);
                            mRootView.hideLoading();
                        }

                        @Override
                        public void onNext(BaseBean<T> bean) {
                            try {
                                mRootView.hideLoading();
                                if (bean.getCode() >= Constant.HTTP200 && bean.getCode() < Constant.HTTP300) {
                                    if (bean.getCode() == Constant.HTTP201 && StringUtil.isNotNullString(bean.getMessage())) {
                                        ArmsUtils.makeText(bean.getMessage());
                                    }
                                    if (handle != null) {
                                        handle.getBodyFromObject(bean.getData());
                                    }
                                } else if (bean.getCode() >= Constant.HTTP400 && bean.getCode() < Constant.HTTP500) {
                                    if (bean.getCode() == Constant.HTTP401 && StringUtil.isNotNullString(bean.getMessage())) {
                                        ArmsUtils.makeText(bean.getMessage());
                                    } else if (bean.getCode() == Constant.HTTP403) {
                                        ArmsUtils.makeText("您的账号在其它设备上登录，请重新登录");
                                        AppUtils.logout();
                                    } else if (bean.getCode() == Constant.HTTP405) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            ArmsUtils.makeText("签名验证失败");
                                        } else {
                                            ArmsUtils.makeText("请求异常");
                                        }
                                    } else if (bean.getCode() == Constant.HTTP406) {
                                        if (BuildConfig.LOG_DEBUG) {
                                            ArmsUtils.makeText("请求已超时");
                                        } else {
                                            ArmsUtils.makeText("请求异常");
                                        }
                                    } else {
                                        ArmsUtils.makeText("请求异常 - 请联系服务人员");
                                    }
                                } else {
                                    ArmsUtils.makeText("请求异常 - 请联系服务人员");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
