package com.weique.overhaul.v2.mvp.model;

import android.annotation.SuppressLint;
import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.weique.overhaul.v2.mvp.contract.SplashContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/09/2019 17:47
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SplashModel extends BaseModel implements SplashContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SplashModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    /**
     * 倒计时
     */
    @SuppressLint("CheckResult")
    @Override
    public Observable<Long> downTime(Long startTime) {
        return Observable.create(emitter -> {
            try {
                Flowable.intervalRange(0, startTime, 0, 1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(aLong -> emitter.onNext(startTime - 1 - aLong))
                        .doOnComplete(() -> {
                            //倒计时完毕置为可点击状态
//                                    emitter.onNext(0L);
                        }).subscribe();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    /*@SuppressLint("CheckResult")
    @Override
    public Observable<Long> downTime(Long startTime) {
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                try {
                    CountDownTimer timer =  new CountDownTimer(startTime * 1000, 1000) {
                        @SuppressLint("StringFormatMatches")
                        @Override
                        public void onTick(long millisUntilFinished) {
                            emitter.onNext(millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            emitter.onNext(0l);
                        }
                    }.start();
                } catch (
                        Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
}