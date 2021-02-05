package com.weique.overhaul.v2.di.module;

import android.graphics.Typeface;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.customview.linetu.XYMarkerView;
import com.weique.overhaul.v2.mvp.contract.SummaryStatisticsContract;
import com.weique.overhaul.v2.mvp.model.SummaryStatisticsModel;
import com.weique.overhaul.v2.mvp.ui.adapter.InformationDataStatisticsAdapter;

import java.util.ArrayList;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/02/2020 09:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SummaryStatisticsModule {

    @Binds
    abstract SummaryStatisticsContract.Model bindSummaryStatisticsModel(SummaryStatisticsModel model);

    //Fragment
    @FragmentScope
    @Provides
    static Typeface provideTypeface(SummaryStatisticsContract.View view) {
        return Typeface.createFromAsset(view.getContext().getAssets(), "OpenSans-Regular.ttf");
    }

    /**
     * 自定义折线图点击视图
     *
     * @param view
     * @return
     */
    @FragmentScope
    @Provides
    static XYMarkerView provideXYMakerView(SummaryStatisticsContract.View view) {
        return new XYMarkerView(view.getContext(),"打卡");
    }


    @FragmentScope
    @Provides
    static GridLayoutManager provideGridLayoutManager(SummaryStatisticsContract.View view) {
        return new GridLayoutManager(view.getContext(), 2, RecyclerView.VERTICAL, false);
    }

    @FragmentScope
    @Provides
    static InformationDataStatisticsAdapter provideInformationCollectionAdapter() {
        return new InformationDataStatisticsAdapter(R.layout.activity_information_data_statistics_item, new ArrayList<>());
    }

}