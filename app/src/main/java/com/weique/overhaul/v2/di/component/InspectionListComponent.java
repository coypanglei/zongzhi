package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.InspectionListModule;
import com.weique.overhaul.v2.mvp.contract.InspectionListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 11:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InspectionListModule.class, dependencies = AppComponent.class)
public interface InspectionListComponent {
    void inject(InspectionListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InspectionListComponent.Builder view(InspectionListContract.View view);

        InspectionListComponent.Builder appComponent(AppComponent appComponent);

        InspectionListComponent build();
    }
}