package com.weique.overhaul.v2.di.module;

import com.weique.overhaul.v2.mvp.contract.SignInContract;
import com.weique.overhaul.v2.mvp.model.SignInModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@Module
public abstract class SignInModule {

    @Binds
    abstract SignInContract.Model bindSignInModel(SignInModel model);
}