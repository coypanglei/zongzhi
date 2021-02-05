package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.StaffTemperatureModule;
import com.weique.overhaul.v2.mvp.contract.StaffTemperatureContract;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.StaffTemperatureActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 21:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StaffTemperatureModule.class, dependencies = AppComponent.class)
public interface StaffTemperatureComponent {
    void inject(StaffTemperatureActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StaffTemperatureComponent.Builder view(StaffTemperatureContract.View view);

        StaffTemperatureComponent.Builder appComponent(AppComponent appComponent);

        StaffTemperatureComponent build();
    }
}