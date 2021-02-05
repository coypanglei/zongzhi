package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.SginStaffListModule;
import com.weique.overhaul.v2.mvp.contract.SignStaffListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.sign.SignStaffListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/21/2020 14:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SginStaffListModule.class, dependencies = AppComponent.class)
public interface SginStaffListComponent {
    void inject(SignStaffListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SginStaffListComponent.Builder view(SignStaffListContract.View view);

        SginStaffListComponent.Builder appComponent(AppComponent appComponent);

        SginStaffListComponent build();
    }
}