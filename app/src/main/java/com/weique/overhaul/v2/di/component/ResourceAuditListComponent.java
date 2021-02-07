package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ResourceAuditListModule;
import com.weique.overhaul.v2.mvp.contract.ResourceAuditListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.information.ResourceAuditListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/17/2020 15:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ResourceAuditListModule.class, dependencies = AppComponent.class)
public interface ResourceAuditListComponent {
    void inject(ResourceAuditListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ResourceAuditListComponent.Builder view(ResourceAuditListContract.View view);

        ResourceAuditListComponent.Builder appComponent(AppComponent appComponent);

        ResourceAuditListComponent build();
    }
}