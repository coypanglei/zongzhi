package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.InformationListForAddressDetailModule;
import com.weique.overhaul.v2.mvp.contract.InformationListForAddressDetailContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.standardaddress.InformationListForAddressDetailFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/28/2020 16:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = InformationListForAddressDetailModule.class, dependencies = AppComponent.class)
public interface InformationListForAddressDetailComponent {
    void inject(InformationListForAddressDetailFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        InformationListForAddressDetailComponent.Builder view(InformationListForAddressDetailContract.View view);

        InformationListForAddressDetailComponent.Builder appComponent(AppComponent appComponent);

        InformationListForAddressDetailComponent build();
    }
}