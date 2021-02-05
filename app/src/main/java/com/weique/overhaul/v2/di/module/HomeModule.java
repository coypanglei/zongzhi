package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.mvp.contract.HomeContract;
import com.weique.overhaul.v2.mvp.model.HomeModel;
import com.weique.overhaul.v2.mvp.ui.adapter.HomeGridIndicatorAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.HomeMenuGridAdapter;

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
    static HomeMenuGridAdapter provideHomeMenuGridAdapter() {
        return new HomeMenuGridAdapter(R.layout.home_grid_item, new ArrayList<>());
    }
}