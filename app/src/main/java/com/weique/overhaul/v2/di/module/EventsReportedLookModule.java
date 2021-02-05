package com.weique.overhaul.v2.di.module;


import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.EventsReportedLookContract;
import com.weique.overhaul.v2.mvp.model.EventsReportedLookModel;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedAgentAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedLookAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedLookFlowAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Module
public abstract class EventsReportedLookModule {

    @Binds
    abstract EventsReportedLookContract.Model bindEventsReportedLookModel(EventsReportedLookModel model);


    @ActivityScope
    @Provides
    static EventsReportedLookFlowAdapter provideEventsReportedLookFlowAdapter() {
        return new EventsReportedLookFlowAdapter();
    }

    @ActivityScope
    @Provides
    static EventsReportedLookAdapter provideEventsReportedLookAdapter() {
        return new EventsReportedLookAdapter(null);
    }

    @ActivityScope
    @Provides
    static EventsReportedAgentAdapter provideEventsReportedAgentAdapter() {
        return new EventsReportedAgentAdapter();
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(EventsReportedLookContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getActivity())
                .colorResId(R.color.gray_eee)
                .margin(ArmsUtils.dip2px(view.getActivity(), 16), ArmsUtils.dip2px(view.getActivity(), 16))
                .sizeResId(R.dimen.dp_1)
                .build();
    }
}