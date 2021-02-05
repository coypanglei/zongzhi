package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.StaffListModule;
import com.weique.overhaul.v2.mvp.contract.StaffListContract;
import com.weique.overhaul.v2.mvp.ui.activity.epidemic.StaffListActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 19:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StaffListModule.class, dependencies = AppComponent.class)
public interface StaffListComponent {
    void inject(StaffListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StaffListComponent.Builder view(StaffListContract.View view);

        StaffListComponent.Builder appComponent(AppComponent appComponent);

        StaffListComponent build();
    }
}