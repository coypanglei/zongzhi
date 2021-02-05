package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.IntegratedWithContract;
import com.weique.overhaul.v2.mvp.model.IntegratedWithModel;
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
public abstract class IntegratedWithModule {

    @Binds
    abstract IntegratedWithContract.Model bindIntegratedWithModel(IntegratedWithModel model);

    /**
     * @param view
     * @return
     */
    @ActivityScope
    @Provides
    static HomeMenuGridAdapter providesHomeMenuGridAdapter(IntegratedWithContract.View view) {
        return new HomeMenuGridAdapter(R.layout.home_grid_item, new ArrayList<>());
    }

    /**
     * @param view
     * @return
     */
    @ActivityScope
    @Provides
    static GridLayoutManager provideGridLayoutManager(IntegratedWithContract.View view) {
        return new GridLayoutManager(view.getActivity(), 5, RecyclerView.VERTICAL, false);
    }

}