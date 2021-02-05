package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.BusinessQuestionDetailModule;
import com.weique.overhaul.v2.mvp.contract.BusinessQuestionDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.BusinessQuestionDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 10:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BusinessQuestionDetailModule.class, dependencies = AppComponent.class)
public interface BusinessQuestionDetailComponent {
    void inject(BusinessQuestionDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BusinessQuestionDetailComponent.Builder view(BusinessQuestionDetailContract.View view);

        BusinessQuestionDetailComponent.Builder appComponent(AppComponent appComponent);

        BusinessQuestionDetailComponent build();
    }
}