package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EconomicManagementBaseInfoModule;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementBaseInfoContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.economicmanagement.EconomicManagementBaseInfoFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 11:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = EconomicManagementBaseInfoModule.class, dependencies = AppComponent.class)
public interface EconomicManagementBaseInfoComponent {
    void inject(EconomicManagementBaseInfoFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EconomicManagementBaseInfoComponent.Builder view(EconomicManagementBaseInfoContract.View view);

        EconomicManagementBaseInfoComponent.Builder appComponent(AppComponent appComponent);

        EconomicManagementBaseInfoComponent build();
    }
}