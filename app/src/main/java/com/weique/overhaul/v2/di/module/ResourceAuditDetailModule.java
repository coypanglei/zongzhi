package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.mvp.contract.ResourceAuditDetailContract;
import com.weique.overhaul.v2.mvp.model.ResourceAuditDetailModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/17/2020 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ResourceAuditDetailModule {

    @Binds
    abstract ResourceAuditDetailContract.Model bindResourceAuditDetailModel(ResourceAuditDetailModel model);
}