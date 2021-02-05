package com.weique.overhaul.v2.dynamic.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.dynamic.di.module.CommonRefreshModule;
import com.weique.overhaul.v2.dynamic.mvp.contract.CommonRefreshContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.dynamic.mvp.ui.activity.CommonRefreshActivity;   


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/04/2021 14:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommonRefreshModule.class, dependencies = AppComponent.class)
public interface CommonRefreshComponent {
    void inject(CommonRefreshActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        CommonRefreshComponent.Builder view(CommonRefreshContract.View view);
        CommonRefreshComponent.Builder appComponent(AppComponent appComponent);
        CommonRefreshComponent build();
    }
}