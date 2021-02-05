package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyCenterIntegralModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterIntegralContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterIntegralActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/05/2019 15:00
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PartyCenterIntegralModule.class, dependencies = AppComponent.class)
public interface PartyCenterIntegralComponent {
    void inject(PartyCenterIntegralActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterIntegralComponent.Builder view(PartyCenterIntegralContract.View view);

        PartyCenterIntegralComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterIntegralComponent build();
    }
}