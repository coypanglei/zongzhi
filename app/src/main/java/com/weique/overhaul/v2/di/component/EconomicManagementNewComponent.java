package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EconomicManagementNewModule;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementNewContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.EconomicManagementNewFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/22/2020 14:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = EconomicManagementNewModule.class, dependencies = AppComponent.class)
public interface EconomicManagementNewComponent {
    void inject(EconomicManagementNewFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EconomicManagementNewComponent.Builder view(EconomicManagementNewContract.View view);

        EconomicManagementNewComponent.Builder appComponent(AppComponent appComponent);

        EconomicManagementNewComponent build();
    }
}