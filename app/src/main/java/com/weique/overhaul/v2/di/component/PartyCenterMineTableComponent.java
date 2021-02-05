package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyCenterMineTableModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterMineTableContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.party.PartyCenterMineTableFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 13:32
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PartyCenterMineTableModule.class, dependencies = AppComponent.class)
public interface PartyCenterMineTableComponent {
    void inject(PartyCenterMineTableFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterMineTableComponent.Builder view(PartyCenterMineTableContract.View view);

        PartyCenterMineTableComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterMineTableComponent build();
    }
}