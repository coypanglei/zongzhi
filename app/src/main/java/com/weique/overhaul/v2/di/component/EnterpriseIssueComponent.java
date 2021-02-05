package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.EnterpriseIssueModule;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueContract;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.EnterpriseIssueListActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/09/2020 14:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnterpriseIssueModule.class, dependencies = AppComponent.class)
public interface EnterpriseIssueComponent {
    void inject(EnterpriseIssueListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnterpriseIssueComponent.Builder view(EnterpriseIssueContract.View view);

        EnterpriseIssueComponent.Builder appComponent(AppComponent appComponent);

        EnterpriseIssueComponent build();
    }
}