package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.IntegralRuleModule;
import com.weique.overhaul.v2.mvp.contract.IntegralRuleContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.IntegralRuleActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/19/2020 16:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = IntegralRuleModule.class, dependencies = AppComponent.class)
public interface IntegralRuleComponent {
    void inject(IntegralRuleActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        IntegralRuleComponent.Builder view(IntegralRuleContract.View view);

        IntegralRuleComponent.Builder appComponent(AppComponent appComponent);

        IntegralRuleComponent build();
    }
}