package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.EventsReportedModule;
import com.weique.overhaul.v2.mvp.contract.EventsReportedContract;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/06/2019 16:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EventsReportedModule.class, dependencies = AppComponent.class)
public interface EventsReportedComponent {
    void inject(EventsReportedActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EventsReportedComponent.Builder view(EventsReportedContract.View view);

        EventsReportedComponent.Builder appComponent(AppComponent appComponent);

        EventsReportedComponent build();
    }
}