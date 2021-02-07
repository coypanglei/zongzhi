package com.weique.overhaul.v2.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.weique.overhaul.v2.di.module.SpeechSettingModule;
import com.weique.overhaul.v2.mvp.contract.SpeechSettingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.mvp.ui.activity.SpeechSettingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2021 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SpeechSettingModule.class, dependencies = AppComponent.class)
public interface SpeechSettingComponent {
    void inject(SpeechSettingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SpeechSettingComponent.Builder view(SpeechSettingContract.View view);

        SpeechSettingComponent.Builder appComponent(AppComponent appComponent);

        SpeechSettingComponent build();
    }
}