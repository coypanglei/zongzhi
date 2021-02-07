package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.mvp.contract.HomeContract;
import com.weique.overhaul.v2.mvp.model.HomeModel;
import com.weique.overhaul.v2.mvp.ui.adapter.HomeMenuAdapter;

import java.util.ArrayList;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GK
 */
@Module
public abstract class HomeModule {

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);

    @FragmentScope
    @Provides
    static ACache provideACache(HomeContract.View view) {
        return ACache.get(view.getActivity());
    }


    @FragmentScope
    @Provides
    static HomeMenuAdapter provideHomeMenuGridAdapter() {
        return new HomeMenuAdapter(R.layout.home_grid_item, new ArrayList<>());
    }
}