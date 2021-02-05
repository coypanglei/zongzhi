package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.InformationDynamicFormAAModule;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormACrudContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationDynamicFormCrudActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/28/2019 16:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InformationDynamicFormAAModule.class, dependencies = AppComponent.class)
public interface InformationDynamicFormAAComponent {
    void inject(InformationDynamicFormCrudActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InformationDynamicFormAAComponent.Builder view(InformationDynamicFormACrudContract.View view);

        InformationDynamicFormAAComponent.Builder appComponent(AppComponent appComponent);

        InformationDynamicFormAAComponent build();
    }
}