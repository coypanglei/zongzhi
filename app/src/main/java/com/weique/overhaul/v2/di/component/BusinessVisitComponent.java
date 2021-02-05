package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.BusinessVisitModule;
import com.weique.overhaul.v2.mvp.contract.BusinessVisitContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.BusinessAddVisitActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 14:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BusinessVisitModule.class, dependencies = AppComponent.class)
public interface BusinessVisitComponent {
    void inject(BusinessAddVisitActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BusinessVisitComponent.Builder view(BusinessVisitContract.View view);

        BusinessVisitComponent.Builder appComponent(AppComponent appComponent);

        BusinessVisitComponent build();
    }
}