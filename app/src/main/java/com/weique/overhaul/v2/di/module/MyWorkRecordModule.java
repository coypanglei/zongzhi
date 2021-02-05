package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.mvp.contract.MyWorkRecordContract;
import com.weique.overhaul.v2.mvp.model.MyWorkRecordModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/16/2019 13:31
 *

 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MyWorkRecordModule {

    @Binds
    abstract MyWorkRecordContract.Model bindMyWorkRecordModel(MyWorkRecordModel model);
}