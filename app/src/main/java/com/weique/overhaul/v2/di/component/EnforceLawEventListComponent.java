package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EnforceLawEventListModule;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.enforcelaw.EnforceLawEventListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = EnforceLawEventListModule.class, dependencies = AppComponent.class)
public interface EnforceLawEventListComponent {
    void inject(EnforceLawEventListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnforceLawEventListComponent.Builder view(EnforceLawEventListContract.View view);

        EnforceLawEventListComponent.Builder appComponent(AppComponent appComponent);

        EnforceLawEventListComponent build();
    }
}