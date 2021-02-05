package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.LawWorksModule;
import com.weique.overhaul.v2.mvp.contract.LawWorksContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.lawworks.LawWorksActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/22/2020 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LawWorksModule.class, dependencies = AppComponent.class)
public interface LawWorksComponent {
    void inject(LawWorksActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LawWorksComponent.Builder view(LawWorksContract.View view);

        LawWorksComponent.Builder appComponent(AppComponent appComponent);

        LawWorksComponent build();
    }
}