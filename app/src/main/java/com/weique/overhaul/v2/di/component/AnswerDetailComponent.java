package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.AnswerDetailModule;
import com.weique.overhaul.v2.mvp.contract.AnswerDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.party.AnswerDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/08/2019 14:07
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AnswerDetailModule.class, dependencies = AppComponent.class)
public interface AnswerDetailComponent {
    void inject(AnswerDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AnswerDetailComponent.Builder view(AnswerDetailContract.View view);

        AnswerDetailComponent.Builder appComponent(AppComponent appComponent);

        AnswerDetailComponent build();
    }
}