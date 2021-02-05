package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyCenterRecommendedModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterRecommendedContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.party.PartyCenterRecommendedFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/06/2019 14:39
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PartyCenterRecommendedModule.class, dependencies = AppComponent.class)
public interface PartyCenterRecommendedComponent {
    void inject(PartyCenterRecommendedFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterRecommendedComponent.Builder view(PartyCenterRecommendedContract.View view);

        PartyCenterRecommendedComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterRecommendedComponent build();
    }
}