package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.QuestionDetailModule;
import com.weique.overhaul.v2.mvp.contract.QuestionDetailContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.party.QuestionDetailFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/08/2019 14:42
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = QuestionDetailModule.class, dependencies = AppComponent.class)
public interface QuestionDetailComponent {
    void inject(QuestionDetailFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        QuestionDetailComponent.Builder view(QuestionDetailContract.View view);

        QuestionDetailComponent.Builder appComponent(AppComponent appComponent);

        QuestionDetailComponent build();
    }
}