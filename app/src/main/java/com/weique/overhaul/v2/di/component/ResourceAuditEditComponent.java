package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ResourceAuditEditModule;
import com.weique.overhaul.v2.mvp.contract.ResourceAuditEditContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.information.ResourceAuditEditActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/17/2020 16:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ResourceAuditEditModule.class, dependencies = AppComponent.class)
public interface ResourceAuditEditComponent {
    void inject(ResourceAuditEditActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ResourceAuditEditComponent.Builder view(ResourceAuditEditContract.View view);

        ResourceAuditEditComponent.Builder appComponent(AppComponent appComponent);

        ResourceAuditEditComponent build();
    }
}