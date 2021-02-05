package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EventsReportedSortModule;
import com.weique.overhaul.v2.mvp.contract.EventsReportedSortContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedSortActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/16/2019 14:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EventsReportedSortModule.class, dependencies = AppComponent.class)
public interface EventsReportedSortComponent {
    void inject(EventsReportedSortActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EventsReportedSortComponent.Builder view(EventsReportedSortContract.View view);

        EventsReportedSortComponent.Builder appComponent(AppComponent appComponent);

        EventsReportedSortComponent build();
    }
}