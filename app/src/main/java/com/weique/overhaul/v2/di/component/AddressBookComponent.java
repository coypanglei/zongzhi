package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.AddressBookModule;
import com.weique.overhaul.v2.mvp.contract.AddressBookContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.addressbook.AddressBookActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2019 09:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddressBookModule.class, dependencies = AppComponent.class)
public interface AddressBookComponent {
    void inject(AddressBookActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddressBookComponent.Builder view(AddressBookContract.View view);

        AddressBookComponent.Builder appComponent(AppComponent appComponent);

        AddressBookComponent build();
    }
}