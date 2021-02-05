package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.EnterpriseInformationCollectionModule;
import com.weique.overhaul.v2.mvp.contract.EnterpriseInformationCollectionContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.economicmanagement.EnterpriseInformationCollectionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2020 09:32
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnterpriseInformationCollectionModule.class, dependencies = AppComponent.class)
public interface EnterpriseInformationCollectionComponent {
    void inject(EnterpriseInformationCollectionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnterpriseInformationCollectionComponent.Builder view(EnterpriseInformationCollectionContract.View view);

        EnterpriseInformationCollectionComponent.Builder appComponent(AppComponent appComponent);

        EnterpriseInformationCollectionComponent build();
    }
}