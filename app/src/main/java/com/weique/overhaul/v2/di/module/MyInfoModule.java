package com.weique.overhaul.v2.di.module;

import com.weique.overhaul.v2.mvp.contract.MyInfoContract;
import com.weique.overhaul.v2.mvp.model.MyInfoModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/15/2019 14:50
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MyInfoModule {

    @Binds
    abstract MyInfoContract.Model bindMyInfoModel(MyInfoModel model);
}