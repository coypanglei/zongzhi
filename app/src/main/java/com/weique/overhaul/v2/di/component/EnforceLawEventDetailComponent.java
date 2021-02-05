package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EnforceLawEventDetailModule;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.enforcelaw.EnforceLawEventDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnforceLawEventDetailModule.class, dependencies = AppComponent.class)
public interface EnforceLawEventDetailComponent {
    void inject(EnforceLawEventDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnforceLawEventDetailComponent.Builder view(EnforceLawEventDetailContract.View view);

        EnforceLawEventDetailComponent.Builder appComponent(AppComponent appComponent);

        EnforceLawEventDetailComponent build();
    }
}