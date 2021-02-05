package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EconomicManagementMainModule;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementMainContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.economicmanagement.EconomicManagementMainActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/12/2020 10:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EconomicManagementMainModule.class, dependencies = AppComponent.class)
public interface EconomicManagementMainComponent {
    void inject(EconomicManagementMainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EconomicManagementMainComponent.Builder view(EconomicManagementMainContract.View view);

        EconomicManagementMainComponent.Builder appComponent(AppComponent appComponent);

        EconomicManagementMainComponent build();
    }
}