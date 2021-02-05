package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.InspectionDetailListModule;
import com.weique.overhaul.v2.mvp.contract.InspectionDetailListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.visit.InspectionDetailListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2019 16:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InspectionDetailListModule.class, dependencies = AppComponent.class)
public interface InspectionDetailListComponent {
    void inject(InspectionDetailListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InspectionDetailListComponent.Builder view(InspectionDetailListContract.View view);

        InspectionDetailListComponent.Builder appComponent(AppComponent appComponent);

        InspectionDetailListComponent build();
    }
}