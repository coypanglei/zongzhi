package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EnforceLawEventModule;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.enforcelaw.EnforceLawEventActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2020 16:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnforceLawEventModule.class, dependencies = AppComponent.class)
public interface EnforceLawEventComponent {
    void inject(EnforceLawEventActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnforceLawEventComponent.Builder view(EnforceLawEventContract.View view);

        EnforceLawEventComponent.Builder appComponent(AppComponent appComponent);

        EnforceLawEventComponent build();
    }
}