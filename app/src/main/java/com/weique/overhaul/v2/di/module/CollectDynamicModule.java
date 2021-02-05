package com.weique.overhaul.v2.di.module;

import com.weique.overhaul.v2.mvp.contract.CollectDynamicContract;
import com.weique.overhaul.v2.mvp.model.CollectDynamicModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/29/2019 10:53
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CollectDynamicModule {

    @Binds
    abstract CollectDynamicContract.Model bindCollectDynamicModel(CollectDynamicModel model);
}