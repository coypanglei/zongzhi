package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.EnterpriseIssueEditModule;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueEditContract;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.EnterpriseIssueEditActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/10/2020 15:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnterpriseIssueEditModule.class, dependencies = AppComponent.class)
public interface EnterpriseIssueEditComponent {
    void inject(EnterpriseIssueEditActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnterpriseIssueEditComponent.Builder view(EnterpriseIssueEditContract.View view);

        EnterpriseIssueEditComponent.Builder appComponent(AppComponent appComponent);

        EnterpriseIssueEditComponent build();
    }
}