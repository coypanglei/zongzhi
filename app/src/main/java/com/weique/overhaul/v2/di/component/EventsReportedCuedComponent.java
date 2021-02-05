package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.EventsReportedCrudModule;
import com.weique.overhaul.v2.mvp.contract.EventsReportedCrudContract;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventsReportedCrudActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 09:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EventsReportedCrudModule.class, dependencies = AppComponent.class)
public interface EventsReportedCuedComponent {
    void inject(EventsReportedCrudActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EventsReportedCuedComponent.Builder view(EventsReportedCrudContract.View view);

        EventsReportedCuedComponent.Builder appComponent(AppComponent appComponent);

        EventsReportedCuedComponent build();
    }
}