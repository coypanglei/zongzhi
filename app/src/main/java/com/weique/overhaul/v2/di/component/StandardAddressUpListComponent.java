package com.weique.overhaul.v2.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.di.module.StandardAddressUpListModule;
import com.weique.overhaul.v2.mvp.contract.StandardAddressUpListContract;
import com.weique.overhaul.v2.mvp.ui.activity.standardaddress.StandardAddressUpListActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/23/2019 12:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = StandardAddressUpListModule.class, dependencies = AppComponent.class)
public interface StandardAddressUpListComponent {
    void inject(StandardAddressUpListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        StandardAddressUpListComponent.Builder view(StandardAddressUpListContract.View view);

        StandardAddressUpListComponent.Builder appComponent(AppComponent appComponent);

        StandardAddressUpListComponent build();
    }
}