package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.EventsReportedSortContract;
import com.weique.overhaul.v2.mvp.model.EventsReportedSortModel;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedSortAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

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
public abstract class EventsReportedSortModule {

    @Binds
    abstract EventsReportedSortContract.Model bindEventsReportedSortModel(EventsReportedSortModel model);

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(EventsReportedSortContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    static EventsReportedSortAdapter provideEventsReportedSortAdapter() {
        return new EventsReportedSortAdapter();
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(EventsReportedSortContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.gray_eee)
                .sizeResId(R.dimen.dp_1)
                .build();
    }
}