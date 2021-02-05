package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.PartyCenterStudyModule;
import com.weique.overhaul.v2.mvp.contract.PartyCenterStudyContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.party.PartyCenterStudyActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/11/2019 15:54
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PartyCenterStudyModule.class, dependencies = AppComponent.class)
public interface PartyCenterStudyComponent {
    void inject(PartyCenterStudyActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PartyCenterStudyComponent.Builder view(PartyCenterStudyContract.View view);

        PartyCenterStudyComponent.Builder appComponent(AppComponent appComponent);

        PartyCenterStudyComponent build();
    }
}