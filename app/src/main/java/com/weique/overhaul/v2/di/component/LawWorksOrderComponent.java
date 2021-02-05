package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.LawWorksOrderModule;
import com.weique.overhaul.v2.mvp.contract.LawWorksOrderContract;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.mvp.ui.fragment.lawworks.LawWorksOrderFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/22/2020 15:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = LawWorksOrderModule.class, dependencies = AppComponent.class)
public interface LawWorksOrderComponent {
    void inject(LawWorksOrderFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        LawWorksOrderComponent.Builder view(LawWorksOrderContract.View view);

        LawWorksOrderComponent.Builder appComponent(AppComponent appComponent);

        LawWorksOrderComponent build();
    }
}