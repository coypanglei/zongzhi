package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EnforceLawEventAddModule;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventAddContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.enforcelaw.EnforceLawEventAddActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2020 16:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnforceLawEventAddModule.class, dependencies = AppComponent.class)
public interface EnforceLawEventAddComponent {
    void inject(EnforceLawEventAddActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnforceLawEventAddComponent.Builder view(EnforceLawEventAddContract.View view);

        EnforceLawEventAddComponent.Builder appComponent(AppComponent appComponent);

        EnforceLawEventAddComponent build();
    }
}