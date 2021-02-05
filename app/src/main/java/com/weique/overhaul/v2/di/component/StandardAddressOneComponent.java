package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.StandardAddressOneModule;
import com.weique.overhaul.v2.mvp.contract.StandardAddressOneContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.StandardAddressOneActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/22/2019 09:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StandardAddressOneModule.class, dependencies = AppComponent.class)
public interface StandardAddressOneComponent {
    void inject(StandardAddressOneActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StandardAddressOneComponent.Builder view(StandardAddressOneContract.View view);

        StandardAddressOneComponent.Builder appComponent(AppComponent appComponent);

        StandardAddressOneComponent build();
    }
}