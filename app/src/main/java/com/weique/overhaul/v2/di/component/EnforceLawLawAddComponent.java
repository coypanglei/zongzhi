package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EnforceLawLawAddModule;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawAddContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.enforcelaw.EnforceLawLawAddActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2020 16:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnforceLawLawAddModule.class, dependencies = AppComponent.class)
public interface EnforceLawLawAddComponent {
    void inject(EnforceLawLawAddActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnforceLawLawAddComponent.Builder view(EnforceLawLawAddContract.View view);

        EnforceLawLawAddComponent.Builder appComponent(AppComponent appComponent);

        EnforceLawLawAddComponent build();
    }
}