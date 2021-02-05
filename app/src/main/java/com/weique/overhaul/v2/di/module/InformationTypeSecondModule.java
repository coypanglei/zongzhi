package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.InformationTypeSecondContract;
import com.weique.overhaul.v2.mvp.model.InformationTypeSecondModel;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationTypeSecondAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * ================================================
 */
@Module
public abstract class InformationTypeSecondModule {

    @Binds
    abstract InformationTypeSecondContract.Model bindInformationTypeSecondModel(InformationTypeSecondModel model);

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(InformationTypeSecondContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(InformationTypeSecondContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.theme_background)
                .sizeResId(R.dimen.dp_10)
                .build();
    }

    @ActivityScope
    @Provides
    static InformationTypeSecondAdapter provideInformationTypeSecondAdapter() {
        return new InformationTypeSecondAdapter();
    }
}