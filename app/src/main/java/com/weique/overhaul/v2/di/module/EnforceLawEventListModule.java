package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventListContract;
import com.weique.overhaul.v2.mvp.contract.EventsReportedContract;
import com.weique.overhaul.v2.mvp.model.EnforceLawEventListModel;
import com.weique.overhaul.v2.mvp.ui.adapter.EventsReportedAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class EnforceLawEventListModule {

    @Binds
    abstract EnforceLawEventListContract.Model bindEnforceLawEventListModel(EnforceLawEventListModel model);
    @FragmentScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(EnforceLawEventListContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @FragmentScope
    @Provides
    static EventsReportedAdapter provideEventsReportedAdapter() {
        return new EventsReportedAdapter();
    }

    @FragmentScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(EnforceLawEventListContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.theme_background)
                .sizeResId(R.dimen.dp_10)
                .build();
    }

}