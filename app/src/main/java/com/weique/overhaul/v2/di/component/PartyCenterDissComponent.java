package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.PartyCenterDissModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterDissContract;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterDissActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * ================================================
 */
@ActivityScope
@Component(modules = PartyCenterDissModule.class, dependencies = AppComponent.class)
public interface PartyCenterDissComponent {
    void inject(PartyCenterDissActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterDissComponent.Builder view(PartyCenterDissContract.View view);

        PartyCenterDissComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterDissComponent build();
    }
}