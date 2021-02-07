package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EventsReportedFModule;
import com.weique.overhaul.v2.mvp.contract.EventsReportedFContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.eventreport.EventsReportedFFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/21/2021 14:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = EventsReportedFModule.class, dependencies = AppComponent.class)
public interface EventsReportedFComponent {
    void inject(EventsReportedFFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EventsReportedFComponent.Builder view(EventsReportedFContract.View view);

        EventsReportedFComponent.Builder appComponent(AppComponent appComponent);

        EventsReportedFComponent build();
    }
}