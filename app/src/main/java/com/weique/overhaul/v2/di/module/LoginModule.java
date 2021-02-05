package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.app.utils.ACache;
import com.weique.overhaul.v2.mvp.contract.EnterpriseIssueEditContract;
import com.weique.overhaul.v2.mvp.contract.LoginContract;
import com.weique.overhaul.v2.mvp.model.LoginModel;
import com.weique.overhaul.v2.mvp.ui.popupwindow.IssueTypeListPopup;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 *
 * @author GreatKing
 */
@Module
public abstract class LoginModule {

    @Binds
    abstract LoginContract.Model bindLoginModel(LoginModel model);

    @ActivityScope
    @Provides
    static ACache provideACache(LoginContract.View view) {
        return ACache.get(view.getActivity());
    }

    @ActivityScope
    @Provides
    static IssueTypeListPopup provideIssueTypeListPopup(EnterpriseIssueEditContract.View view) {
        return new IssueTypeListPopup(view.getContext());
    }
}