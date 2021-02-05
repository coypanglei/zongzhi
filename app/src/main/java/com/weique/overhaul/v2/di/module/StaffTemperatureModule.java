package com.weique.overhaul.v2.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.StaffTemperatureContract;
import com.weique.overhaul.v2.mvp.model.StaffTemperatureModel;
import com.weique.overhaul.v2.mvp.ui.adapter.StaffTemperatureAdapter;
import com.weique.overhaul.v2.mvp.ui.adapter.StaffTemperatureHistoryAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/13/2020 21:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class StaffTemperatureModule {

    @Binds
    abstract StaffTemperatureContract.Model bindStaffTemperatureModel(StaffTemperatureModel model);


    @ActivityScope
    @Provides
    static StaffTemperatureAdapter provideStaffTemperatureAdapter() {
        return new StaffTemperatureAdapter();
    }

    @ActivityScope
    @Provides
    static StaffTemperatureHistoryAdapter provideStaffTemperatureHistoryAdapter() {
        return new StaffTemperatureHistoryAdapter();
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(StaffTemperatureContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.gray_eee)
                .margin(ArmsUtils.dip2px(view.getContext(), 16), ArmsUtils.dip2px(view.getContext(), 16))
                .sizeResId(R.dimen.dp_1)
                .build();
    }
}