package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.InspectionMapModule;
import com.weique.overhaul.v2.mvp.contract.InspectionMapContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionMapActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InspectionMapModule.class, dependencies = AppComponent.class)
public interface InspectionMapComponent {
    void inject(InspectionMapActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InspectionMapComponent.Builder view(InspectionMapContract.View view);

        InspectionMapComponent.Builder appComponent(AppComponent appComponent);

        InspectionMapComponent build();
    }
}