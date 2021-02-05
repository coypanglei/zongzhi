package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EnterpriseIssueSeeModule;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueSeeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.EnterpriseIssueSeeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 16:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnterpriseIssueSeeModule.class, dependencies = AppComponent.class)
public interface EnterpriseIssueSeeComponent {
    void inject(EnterpriseIssueSeeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnterpriseIssueSeeComponent.Builder view(EnterpriseIssueSeeContract.View view);

        EnterpriseIssueSeeComponent.Builder appComponent(AppComponent appComponent);

        EnterpriseIssueSeeComponent build();
    }
}