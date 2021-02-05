package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.StandardAddressOneNewModule;
import com.weique.overhaul.v2.mvp.contract.StandardAddressOneNewContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.newactivity.StandardAddressOneNewActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/25/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StandardAddressOneNewModule.class, dependencies = AppComponent.class)
public interface StandardAddressOneNewComponent {
    void inject(StandardAddressOneNewActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StandardAddressOneNewComponent.Builder view(StandardAddressOneNewContract.View view);

        StandardAddressOneNewComponent.Builder appComponent(AppComponent appComponent);

        StandardAddressOneNewComponent build();
    }
}