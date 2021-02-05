package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.contract.InformationTypeFirstContract;
import com.weique.overhaul.v2.mvp.model.InformationTypeDetailListModel;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationTypeListAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * ================================================
 */
@Module
public abstract class InformationTypeDetailListModule {

    @Binds
    abstract InformationTypeFirstContract.Model bindInformationTypeDetailModel(InformationTypeDetailListModel model);

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(InformationTypeFirstContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    static InformationTypeListAdapter provideInformationTypeListAdapter() {
        return new InformationTypeListAdapter();
    }
}