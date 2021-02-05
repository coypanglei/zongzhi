package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EnforceLawLawDetailModule;
import com.weique.overhaul.v2.mvp.contract.EnforceLawLawDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.enforcelaw.EnforceLawLawDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnforceLawLawDetailModule.class, dependencies = AppComponent.class)
public interface EnforceLawLawDetailComponent {
    void inject(EnforceLawLawDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnforceLawLawDetailComponent.Builder view(EnforceLawLawDetailContract.View view);

        EnforceLawLawDetailComponent.Builder appComponent(AppComponent appComponent);

        EnforceLawLawDetailComponent build();
    }
}