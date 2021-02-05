package com.weique.overhaul.v2.dynamic.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.dynamic.mvp.contract.CommonRefreshContract;
import com.weique.overhaul.v2.dynamic.mvp.model.CommonRefreshModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/04/2021 14:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CommonRefreshModule {

    @Binds
    abstract CommonRefreshContract.Model bindCommonRefreshModel(CommonRefreshModel model);
}