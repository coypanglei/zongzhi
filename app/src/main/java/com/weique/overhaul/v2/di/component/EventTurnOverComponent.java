package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.EventTurnOverModule;
import com.weique.overhaul.v2.mvp.contract.EventTurnOverContract;
import com.weique.overhaul.v2.mvp.ui.activity.eventsreported.EventTurnOverActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author GK
 * @description:
 * @date :2021/1/26 15:53
 */
@ActivityScope
@Component(modules = EventTurnOverModule.class, dependencies = AppComponent.class)
public interface EventTurnOverComponent {
    void inject(EventTurnOverActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EventTurnOverComponent.Builder view(EventTurnOverContract.View view);

        EventTurnOverComponent.Builder appComponent(AppComponent appComponent);

        EventTurnOverComponent build();
    }
}
