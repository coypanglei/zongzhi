package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyCenterTableModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterTableContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.party.PartyCenterTableFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 10:23
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PartyCenterTableModule.class, dependencies = AppComponent.class)
public interface PartyCenterTableComponent {
    void inject(PartyCenterTableFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterTableComponent.Builder view(PartyCenterTableContract.View view);

        PartyCenterTableComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterTableComponent build();
    }
}