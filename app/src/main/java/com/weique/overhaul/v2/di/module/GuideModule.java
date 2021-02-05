package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.mvp.contract.GuideContract;
import com.weique.overhaul.v2.mvp.model.GuideModel;

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
public abstract class GuideModule {

    @Binds
    abstract GuideContract.Model bindGuideModel(GuideModel model);

    @ActivityScope
    @Provides
    static ACache provideACache(GuideContract.View view) {
        return ACache.get(view.getActivity());
    }
}