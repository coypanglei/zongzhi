package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.MyInfoModule;
import com.weique.overhaul.v2.mvp.contract.MyInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.MyInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/15/2019 14:50
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyInfoModule.class, dependencies = AppComponent.class)
public interface MyInfoComponent {
    void inject(MyInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyInfoComponent.Builder view(MyInfoContract.View view);

        MyInfoComponent.Builder appComponent(AppComponent appComponent);

        MyInfoComponent build();
    }
}