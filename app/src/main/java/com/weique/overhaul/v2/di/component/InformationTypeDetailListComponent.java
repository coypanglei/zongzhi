package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.InformationTypeDetailListModule;
import com.weique.overhaul.v2.mvp.contract.InformationTypeFirstContract;
import com.weique.overhaul.v2.mvp.ui.activity.information.InformationTypeFirstActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/27/2019 13:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InformationTypeDetailListModule.class, dependencies = AppComponent.class)
public interface InformationTypeDetailListComponent {
    void inject(InformationTypeFirstActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InformationTypeDetailListComponent.Builder view(InformationTypeFirstContract.View view);

        InformationTypeDetailListComponent.Builder appComponent(AppComponent appComponent);

        InformationTypeDetailListComponent build();
    }
}