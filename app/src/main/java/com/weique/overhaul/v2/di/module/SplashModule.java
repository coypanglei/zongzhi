package com.weique.overhaul.v2.di.module;

import androidx.fragment.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.mvp.contract.SplashContract;
import com.weique.overhaul.v2.mvp.model.SplashModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author  GK
 */
@Module
public abstract class SplashModule {

    @Binds
    abstract SplashContract.Model bindSplashModel(SplashModel model);

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(SplashContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @ActivityScope
    @Provides
    static ACache provideACache(SplashContract.View view) {
        return ACache.get(view.getActivity());
    }
}