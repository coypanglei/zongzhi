package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.BusinessInformationModule;
import com.weique.overhaul.v2.mvp.contract.BusinessInformationContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.BusinessInformationActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BusinessInformationModule.class, dependencies = AppComponent.class)
public interface BusinessInformationComponent {
    void inject(BusinessInformationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BusinessInformationComponent.Builder view(BusinessInformationContract.View view);

        BusinessInformationComponent.Builder appComponent(AppComponent appComponent);

        BusinessInformationComponent build();
    }
}