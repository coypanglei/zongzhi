package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.contract.EventsReportedSortContract;
import com.weique.overhaul.v2.mvp.contract.LawWorksOrderContract;
import com.weique.overhaul.v2.mvp.model.LawWorksOrderModel;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedSortAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.LawWorksOrderAdapter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 * @author GreatKing
 */
@Module
public abstract class LawWorksOrderModule {

    @Binds
    abstract LawWorksOrderContract.Model bindLawWorksOrderModel(LawWorksOrderModel model);

    @FragmentScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(LawWorksOrderContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @FragmentScope
    @Provides
    static LawWorksOrderAdapter provideLawWorksOrderAdapter() {
        return new LawWorksOrderAdapter();
    }
}