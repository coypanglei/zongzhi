package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyCenterCollectsModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterCollectsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterCollectsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/11/2019 10:00
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PartyCenterCollectsModule.class, dependencies = AppComponent.class)
public interface PartyCenterCollectsComponent {
    void inject(PartyCenterCollectsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterCollectsComponent.Builder view(PartyCenterCollectsContract.View view);

        PartyCenterCollectsComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterCollectsComponent build();
    }
}