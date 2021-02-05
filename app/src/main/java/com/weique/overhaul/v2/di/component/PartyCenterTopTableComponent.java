package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyCenterTopTableModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterTopTableContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.party.PartyCenterTopTableFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/04/2019 14:17
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PartyCenterTopTableModule.class, dependencies = AppComponent.class)
public interface PartyCenterTopTableComponent {
    void inject(PartyCenterTopTableFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterTopTableComponent.Builder view(PartyCenterTopTableContract.View view);

        PartyCenterTopTableComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterTopTableComponent build();
    }
}