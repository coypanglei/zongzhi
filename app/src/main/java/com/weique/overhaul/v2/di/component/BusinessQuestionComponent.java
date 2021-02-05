package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.BusinessQuestionModule;
import com.weique.overhaul.v2.mvp.contract.BusinessQuestionContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.BusinessQuestionActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 10:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BusinessQuestionModule.class, dependencies = AppComponent.class)
public interface BusinessQuestionComponent {
    void inject(BusinessQuestionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BusinessQuestionComponent.Builder view(BusinessQuestionContract.View view);

        BusinessQuestionComponent.Builder appComponent(AppComponent appComponent);

        BusinessQuestionComponent build();
    }
}