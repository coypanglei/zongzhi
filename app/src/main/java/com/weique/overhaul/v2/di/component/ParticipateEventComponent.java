package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ParticipateEventModule;
import com.weique.overhaul.v2.mvp.contract.ParticipateEventContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.ParticipateEventActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/17/2019 15:55
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ParticipateEventModule.class, dependencies = AppComponent.class)
public interface ParticipateEventComponent {
    void inject(ParticipateEventActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ParticipateEventComponent.Builder view(ParticipateEventContract.View view);

        ParticipateEventComponent.Builder appComponent(AppComponent appComponent);

        ParticipateEventComponent build();
    }
}