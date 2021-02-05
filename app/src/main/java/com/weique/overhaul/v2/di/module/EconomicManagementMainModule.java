package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.EconomicManagementMainContract;
import com.weique.overhaul.v2.mvp.contract.EnforceLawEventContract;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormACrudContract;
import com.weique.overhaul.v2.mvp.contract.SummaryStatisticsContract;
import com.weique.overhaul.v2.mvp.model.EconomicManagementMainModel;
import com.weique.overhaul.v2.mvp.ui.adapter.EconomicManagementTitleAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationDataStatisticsAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/12/2020 10:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class EconomicManagementMainModule {

    @Binds
    abstract EconomicManagementMainContract.Model bindEconomicManagementMainModel(EconomicManagementMainModel model);



    @ActivityScope
    @Provides
    static GridLayoutManager provideGridLayoutManager(EconomicManagementMainContract.View view) {
        return new GridLayoutManager(view.getContext(), 3, RecyclerView.VERTICAL, false);
    }

    @ActivityScope
    @Provides
    static EconomicManagementTitleAdapter provideInformationCollectionAdapter() {
        return new EconomicManagementTitleAdapter();
    }


    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(EconomicManagementMainContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.gray_eee)
                .margin(ArmsUtils.dip2px(view.getContext(), 16),
                        ArmsUtils.dip2px(view.getContext(), 16))
                .sizeResId(R.dimen.dp_1)
                .build();
    }

}