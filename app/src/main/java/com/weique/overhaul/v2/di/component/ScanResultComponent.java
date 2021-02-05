package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.ScanResultModule;
import com.weique.overhaul.v2.mvp.contract.ScanResultContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.scan.ScanResultActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/09/2020 13:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ScanResultModule.class, dependencies = AppComponent.class)
public interface ScanResultComponent {
    void inject(ScanResultActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ScanResultComponent.Builder view(ScanResultContract.View view);

        ScanResultComponent.Builder appComponent(AppComponent appComponent);

        ScanResultComponent build();
    }
}