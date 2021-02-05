package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.EventsReportedLookModule;
import com.weique.overhaul.v2.mvp.contract.EventsReportedLookContract;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedLookActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/13/2019 15:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EventsReportedLookModule.class, dependencies = AppComponent.class)
public interface EventsReportedLookComponent {
    void inject(EventsReportedLookActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EventsReportedLookComponent.Builder view(EventsReportedLookContract.View view);

        EventsReportedLookComponent.Builder appComponent(AppComponent appComponent);

        EventsReportedLookComponent build();
    }
}