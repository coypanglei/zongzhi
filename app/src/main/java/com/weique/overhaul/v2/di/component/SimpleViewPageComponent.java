package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.SimpleViewPageModule;
import com.weique.overhaul.v2.mvp.contract.SimpleViewPageContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.SimpleViewPageFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 16:35
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SimpleViewPageModule.class, dependencies = AppComponent.class)
public interface SimpleViewPageComponent {
    void inject(SimpleViewPageFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SimpleViewPageComponent.Builder view(SimpleViewPageContract.View view);

        SimpleViewPageComponent.Builder appComponent(AppComponent appComponent);

        SimpleViewPageComponent build();
    }
}