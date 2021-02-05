package com.weique.overhaul.v2.di.module;

import com.weique.overhaul.v2.mvp.contract.SettingsContract;
import com.weique.overhaul.v2.mvp.model.SettingsModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/15/2019 16:51
 *
 *
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SettingsModule {

    @Binds
    abstract SettingsContract.Model bindSettingsModel(SettingsModel model);

}