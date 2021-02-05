package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.BusinessInformationDetailModule;
import com.weique.overhaul.v2.mvp.contract.BusinessInformationDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.BusinessInformationDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 17:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BusinessInformationDetailModule.class, dependencies = AppComponent.class)
public interface BusinessInformationDetailComponent {
    void inject(BusinessInformationDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BusinessInformationDetailComponent.Builder view(BusinessInformationDetailContract.View view);

        BusinessInformationDetailComponent.Builder appComponent(AppComponent appComponent);

        BusinessInformationDetailComponent build();
    }
}