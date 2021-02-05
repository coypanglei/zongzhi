package com.weique.overhaul.v2.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.StandardAddressOneNewContract;
import com.weique.overhaul.v2.mvp.model.StandardAddressOneNewModel;
import com.weique.overhaul.v2.mvp.ui.adapter.StandardAddressOneAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/25/2020 13:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class StandardAddressOneNewModule {

    @Binds
    abstract StandardAddressOneNewContract.Model bindStandardAddressOneNewModel(StandardAddressOneNewModel model);

    @ActivityScope
    @Provides
    static StandardAddressOneAdapter provideStandardAddressOneAdapter() {
        return new StandardAddressOneAdapter(R.layout.standard_address_one_item, null);
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(StandardAddressOneNewContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.theme_background)
                .sizeResId(R.dimen.dp_10)
                .build();
    }
}