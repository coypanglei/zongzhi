package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyCenterNewsModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterNewsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.party.PartyCenterNewsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/06/2019 14:33
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PartyCenterNewsModule.class, dependencies = AppComponent.class)
public interface PartyCenterNewsComponent {
    void inject(PartyCenterNewsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterNewsComponent.Builder view(PartyCenterNewsContract.View view);

        PartyCenterNewsComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterNewsComponent build();
    }
}