package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.HomeModule;
import com.weique.overhaul.v2.mvp.contract.HomeContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.HomeFragment;
import com.weique.overhaul.v2.mvp.ui.fragment.AppFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/12/2019 16:48
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);
    void inject(AppFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeComponent.Builder view(HomeContract.View view);

        HomeComponent.Builder appComponent(AppComponent appComponent);

        HomeComponent build();
    }
}