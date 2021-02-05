package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.SignInModule;
import com.weique.overhaul.v2.mvp.contract.SignInContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.sign.SignInActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2019 15:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SignInModule.class, dependencies = AppComponent.class)
public interface SignInComponent {
    void inject(SignInActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SignInComponent.Builder view(SignInContract.View view);

        SignInComponent.Builder appComponent(AppComponent appComponent);

        SignInComponent build();
    }
}