package com.weique.overhaul.v2.di.module;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.mvp.contract.InformationDynamicFormACrudContract;
import com.weique.overhaul.v2.mvp.model.InformationDynamicFormAAModel;
import com.weique.overhaul.v2.mvp.ui.adapter.DynamicFormAdapter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/28/2019 16:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class InformationDynamicFormAAModule {

    @Binds
    abstract InformationDynamicFormACrudContract.Model bindInformationDynamicFormAAModel(InformationDynamicFormAAModel model);

    @ActivityScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(InformationDynamicFormACrudContract.View view) {
        return new LinearLayoutManager(view.getContext());
    }

    @ActivityScope
    @Provides
    static HorizontalDividerItemDecoration provideHorizontalDividerItemDecoration(InformationDynamicFormACrudContract.View view) {
        return new HorizontalDividerItemDecoration.Builder(view.getContext())
                .colorResId(R.color.gray_eee)
                .margin(ArmsUtils.dip2px(view.getContext(), 16),
                        ArmsUtils.dip2px(view.getContext(), 16))
                .sizeResId(R.dimen.dp_1)
                .build();
    }

    @ActivityScope
    @Provides
    static DynamicFormAdapter provideInformationDynamicFormAAAdapter() {
        return new DynamicFormAdapter(null);
    }

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(InformationDynamicFormACrudContract.View view) {
        return new RxPermissions((FragmentActivity) view.getContext());
    }
}