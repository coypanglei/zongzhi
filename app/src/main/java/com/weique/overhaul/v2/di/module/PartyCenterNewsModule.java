package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.mvp.contract.PartyCenterNewsContract;
import com.weique.overhaul.v2.mvp.model.PartyCenterNewsModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/06/2019 14:33
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PartyCenterNewsModule {

    @Binds
    abstract PartyCenterNewsContract.Model bindPartyCenterNewsModel(PartyCenterNewsModel model);
}