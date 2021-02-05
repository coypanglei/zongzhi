package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.SignSearchModule;
import com.weique.overhaul.v2.mvp.contract.SignSearchContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.sign.SignSearchActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/24/2019 17:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SignSearchModule.class, dependencies = AppComponent.class)
public interface SignSearchComponent {
    void inject(SignSearchActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SignSearchComponent.Builder view(SignSearchContract.View view);

        SignSearchComponent.Builder appComponent(AppComponent appComponent);

        SignSearchComponent build();
    }
}