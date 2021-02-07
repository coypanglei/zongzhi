package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ResourceAuditDetailModule;
import com.weique.overhaul.v2.mvp.contract.ResourceAuditDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.information.ResourceAuditDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/17/2020 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ResourceAuditDetailModule.class, dependencies = AppComponent.class)
public interface ResourceAuditDetailComponent {
    void inject(ResourceAuditDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ResourceAuditDetailComponent.Builder view(ResourceAuditDetailContract.View view);

        ResourceAuditDetailComponent.Builder appComponent(AppComponent appComponent);

        ResourceAuditDetailComponent build();
    }
}