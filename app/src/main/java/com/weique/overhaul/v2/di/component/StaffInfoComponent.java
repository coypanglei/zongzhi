package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.StaffInfoModule;
import com.weique.overhaul.v2.mvp.contract.StaffInfoContract;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.StaffInfoActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 17:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StaffInfoModule.class, dependencies = AppComponent.class)
public interface StaffInfoComponent {
    void inject(StaffInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StaffInfoComponent.Builder view(StaffInfoContract.View view);

        StaffInfoComponent.Builder appComponent(AppComponent appComponent);

        StaffInfoComponent build();
    }
}