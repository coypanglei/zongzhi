package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.FirmManageModule;
import com.weique.overhaul.v2.mvp.contract.FirmManageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.economicmanagement.FirmManageActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 11:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FirmManageModule.class, dependencies = AppComponent.class)
public interface FirmManageComponent {
    void inject(FirmManageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FirmManageComponent.Builder view(FirmManageContract.View view);

        FirmManageComponent.Builder appComponent(AppComponent appComponent);

        FirmManageComponent build();
    }
}