package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyCenterModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 09:45
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PartyCenterModule.class, dependencies = AppComponent.class)
public interface PartyCenterComponent {
    void inject(PartyCenterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterComponent.Builder view(PartyCenterContract.View view);

        PartyCenterComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterComponent build();
    }
}