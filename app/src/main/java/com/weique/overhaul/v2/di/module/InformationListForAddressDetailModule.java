package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.InformationListForAddressDetailContract;
import com.weique.overhaul.v2.mvp.contract.StandardAddressLookContract;
import com.weique.overhaul.v2.mvp.model.InformationListForAddressDetailModel;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationFormStandardDetailAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationTypeSecondAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/28/2020 16:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class InformationListForAddressDetailModule {

    @Binds
    abstract InformationListForAddressDetailContract.Model bindInformationListForAddressDetailModel(InformationListForAddressDetailModel model);

    @FragmentScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(InformationListForAddressDetailContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @FragmentScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(InformationListForAddressDetailContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.theme_background)
                .sizeResId(R.dimen.dp_10)
                .build();
    }

    @FragmentScope
    @Provides
    static InformationFormStandardDetailAdapter provideInformationFormStandardDetailAdapter() {
        return new InformationFormStandardDetailAdapter();
    }
}