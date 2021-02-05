package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.EnterpriseReworkModule;
import com.weique.overhaul.v2.mvp.contract.EnterpriseReworkContract;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.EnterpriseReworkActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 17:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EnterpriseReworkModule.class, dependencies = AppComponent.class)
public interface EnterpriseReworkComponent {
    void inject(EnterpriseReworkActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EnterpriseReworkComponent.Builder view(EnterpriseReworkContract.View view);

        EnterpriseReworkComponent.Builder appComponent(AppComponent appComponent);

        EnterpriseReworkComponent build();
    }
}