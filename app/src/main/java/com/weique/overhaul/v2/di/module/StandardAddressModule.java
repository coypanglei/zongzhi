package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.mvp.contract.StandardAddressContract;
import com.weique.overhaul.v2.mvp.model.StandardAddressModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 16:45
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class StandardAddressModule {

    @Binds
    abstract StandardAddressContract.Model bindStandardAddressModel(StandardAddressModel model);
}