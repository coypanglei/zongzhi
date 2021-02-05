package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.EventsReportedContract;
import com.weique.overhaul.v2.mvp.model.EventsReportedModel;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedAdapter;
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
public abstract class EventsReportedModule {

    @Binds
    abstract EventsReportedContract.Model bindEventsReportedModel(EventsReportedModel model);

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(EventsReportedContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    static EventsReportedAdapter provideEventsReportedAdapter() {
        return new EventsReportedAdapter();
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(EventsReportedContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.theme_background)
                .sizeResId(R.dimen.dp_10)
                .build();
    }
}