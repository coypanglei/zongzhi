package com.weique.overhaul.v2.di.module;

import dagger.Binds;
import dagger.Module;

import com.weique.overhaul.v2.mvp.contract.SignStaffListContract;
import com.weique.overhaul.v2.mvp.model.SginStaffListModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/21/2020 14:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SginStaffListModule {

    @Binds
    abstract SignStaffListContract.Model bindSginStaffListModel(SginStaffListModel model);
}