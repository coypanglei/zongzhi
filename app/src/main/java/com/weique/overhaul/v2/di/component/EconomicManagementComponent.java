package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EconomicManagementModule;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementTableContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.economicmanagement.EconomicManagementTableFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 13:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = EconomicManagementModule.class, dependencies = AppComponent.class)
public interface EconomicManagementComponent {
    void inject(EconomicManagementTableFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EconomicManagementComponent.Builder view(EconomicManagementTableContract.View view);

        EconomicManagementComponent.Builder appComponent(AppComponent appComponent);

        EconomicManagementComponent build();
    }
}