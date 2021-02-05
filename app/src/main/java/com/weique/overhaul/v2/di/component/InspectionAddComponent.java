package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.InspectionAddModule;
import com.weique.overhaul.v2.mvp.contract.InspectionAddContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionAddActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/10/2019 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InspectionAddModule.class, dependencies = AppComponent.class)
public interface InspectionAddComponent {
    void inject(InspectionAddActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InspectionAddComponent.Builder view(InspectionAddContract.View view);

        InspectionAddComponent.Builder appComponent(AppComponent appComponent);

        InspectionAddComponent build();
    }
}