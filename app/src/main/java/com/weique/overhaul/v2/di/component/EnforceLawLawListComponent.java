package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EnforceLawLawListModule;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.enforcelaw.EnforceLawLawListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = EnforceLawLawListModule.class, dependencies = AppComponent.class)
public interface EnforceLawLawListComponent {
    void inject(EnforceLawLawListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnforceLawLawListComponent.Builder view(EnforceLawLawListContract.View view);

        EnforceLawLawListComponent.Builder appComponent(AppComponent appComponent);

        EnforceLawLawListComponent build();
    }
}