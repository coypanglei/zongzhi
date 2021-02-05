package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormSelectContract;
import com.weique.overhaul.v2.mvp.model.InformationDynamicFormSelectModel;
import com.weique.overhaul.v2.mvp.ui.adapter.DynamicFormSelectAdapter;

import java.util.ArrayList;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * ================================================
 */
@Module
public abstract class InformationDynamicFormSelectModule {

    @Binds
    abstract InformationDynamicFormSelectContract.Model bindInformationDynamicFormSelectModel(InformationDynamicFormSelectModel model);

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(InformationDynamicFormSelectContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    static DynamicFormSelectAdapter provideInformationDynamicFormSelectAdapter() {
        return new DynamicFormSelectAdapter(new ArrayList<>());
    }
}