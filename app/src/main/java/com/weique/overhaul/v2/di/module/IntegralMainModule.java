package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.InformationTypeSecondContract;
import com.weique.overhaul.v2.mvp.contract.IntegralMainContract;
import com.weique.overhaul.v2.mvp.model.IntegralMainModel;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationTypeSecondAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.IntegralAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/19/2020 11:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class IntegralMainModule {

    @Binds
    abstract IntegralMainContract.Model bindIntegralMainModel(IntegralMainModel model);

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(IntegralMainContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(IntegralMainContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.w_fefefe)
                .sizeResId(R.dimen.dp_1)
                .build();
    }

    @ActivityScope
    @Provides
    static IntegralAdapter provideInformationTypeSecondAdapter() {
        return new IntegralAdapter();
    }
}