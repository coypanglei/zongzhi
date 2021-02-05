package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.SearchEconoicManageMentContract;
import com.weique.overhaul.v2.mvp.model.SearchEconoicManageMentModel;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.SearchEconoicAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2020 10:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SearchEconoicManageMentModule {

    @Binds
    abstract SearchEconoicManageMentContract.Model bindSearchEconoicManageMentModel(SearchEconoicManageMentModel model);


    @ActivityScope
    @Provides
    static SearchEconoicAdapter<NameAndIdBean> provideCommonRecyclerPopupAdapter() {
        return new SearchEconoicAdapter<>();
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(SearchEconoicManageMentContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.theme_background)
                .sizeResId(R.dimen.dp_1)
                .build();
    }

}