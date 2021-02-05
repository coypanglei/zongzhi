package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.InformationDynamicFormSelectModule;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormSelectContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationDynamicFormSelectActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/27/2019 16:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InformationDynamicFormSelectModule.class, dependencies = AppComponent.class)
public interface InformationDynamicFormSelectComponent {
    void inject(InformationDynamicFormSelectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InformationDynamicFormSelectComponent.Builder view(InformationDynamicFormSelectContract.View view);

        InformationDynamicFormSelectComponent.Builder appComponent(AppComponent appComponent);

        InformationDynamicFormSelectComponent build();
    }
}