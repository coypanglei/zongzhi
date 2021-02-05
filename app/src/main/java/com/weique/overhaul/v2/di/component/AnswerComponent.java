package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.AnswerModule;
import com.weique.overhaul.v2.mvp.contract.AnswerContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.party.AnswerActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/08/2019 09:58
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AnswerModule.class, dependencies = AppComponent.class)
public interface AnswerComponent {
    void inject(AnswerActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AnswerComponent.Builder view(AnswerContract.View view);

        AnswerComponent.Builder appComponent(AppComponent appComponent);

        AnswerComponent build();
    }
}