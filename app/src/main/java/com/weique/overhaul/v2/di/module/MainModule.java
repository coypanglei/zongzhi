package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.mvp.contract.MainContract;
import com.weique.overhaul.v2.mvp.model.MainModel;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@Module
public abstract class MainModule {

    @Binds
    abstract MainContract.Model bindMainModel(MainModel model);

    @ActivityScope
    @Provides
    static ACache provideACache(MainContract.View view) {
        return ACache.get(view.getActivity());
    }
}