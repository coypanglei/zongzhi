package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.InformationCollectionModule;
import com.weique.overhaul.v2.mvp.contract.InformationCollectionContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationCollectionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 09:39
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InformationCollectionModule.class, dependencies = AppComponent.class)
public interface InformationCollectionComponent {
    void inject(InformationCollectionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InformationCollectionComponent.Builder view(InformationCollectionContract.View view);

        InformationCollectionComponent.Builder appComponent(AppComponent appComponent);

        InformationCollectionComponent build();
    }
}