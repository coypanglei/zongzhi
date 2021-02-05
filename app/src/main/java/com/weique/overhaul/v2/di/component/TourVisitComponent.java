package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.TourVisitModule;
import com.weique.overhaul.v2.mvp.contract.TourVisitContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/03/2019 16:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TourVisitModule.class, dependencies = AppComponent.class)
public interface TourVisitComponent {
    void inject(TourVisitActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TourVisitComponent.Builder view(TourVisitContract.View view);

        TourVisitComponent.Builder appComponent(AppComponent appComponent);

        TourVisitComponent build();
    }
}