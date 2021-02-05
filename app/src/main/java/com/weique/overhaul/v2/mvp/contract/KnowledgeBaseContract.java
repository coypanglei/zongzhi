package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBaseBean;
import com.weique.overhaul.v2.mvp.model.entity.KnowledgeBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2019 09:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface KnowledgeBaseContract {

    interface View extends IView {
        void setKnowledgeBaseListData(KnowledgeBaseBean data,boolean isLoadMore);
        void setTypeList(List<KnowledgeBean> knowledgeBean );
        void setLabelList(List<KnowledgeBean> knowledgeBean );

    }


    interface Model extends IModel {

        Observable<BaseBean<KnowledgeBaseBean>> getKnowledgeBaseListData(int pageSize,int ignoreNumber, String TId,String LId);//知识库列表
        Observable<BaseBean<List<KnowledgeBean>>> getLabelsListData();//标签
        Observable<BaseBean<List<KnowledgeBean>>> getTypeListData();//分类列表
    }
}
