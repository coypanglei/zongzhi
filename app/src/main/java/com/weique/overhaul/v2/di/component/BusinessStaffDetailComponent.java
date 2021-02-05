package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.BusinessStaffDetailModule;
import com.weique.overhaul.v2.mvp.contract.BusinessStaffDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.BusinessStaffDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/12/2020 18:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = BusinessStaffDetailModule.class, dependencies = AppComponent.class)
public interface BusinessStaffDetailComponent {
    void inject(BusinessStaffDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BusinessStaffDetailComponent.Builder view(BusinessStaffDetailContract.View view);

        BusinessStaffDetailComponent.Builder appComponent(AppComponent appComponent);

        BusinessStaffDetailComponent build();
    }
}