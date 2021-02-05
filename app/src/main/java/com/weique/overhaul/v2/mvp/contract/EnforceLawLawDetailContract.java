package com.weique.overhaul.v2.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.weique.overhaul.v2.mvp.model.entity.BaseBean;
import com.weique.overhaul.v2.mvp.model.entity.LawDetailBean;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.model.entity.UploadFileRsponseBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2020 09:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface EnforceLawLawDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        /**
         * 设置案件详情数据
         *
         * @param o o
         */
        void setLawDetail(LawDetailBean o);

        void setLegalOperation(List<NameAndIdBean> o, boolean isLoadMore);

        void setAuditorList(List<NameAndIdBean> bean, boolean isLoadMore);

        void updatePicture(List<UploadFileRsponseBean> uploadFileRsponseBeans);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 获取案件详情 by id
         *
         * @param id id
         * @return Observable
         */
        Observable<BaseBean<LawDetailBean>> getLawDetailById(String id);

        /**
         * 获取执法部门
         *
         * @param ignoreNumber
         * @param pageSize
         * @return o
         */
        Observable<BaseBean<List<NameAndIdBean>>> getLegalOperation(int ignoreNumber, int pageSize);


        Observable<BaseBean<List<NameAndIdBean>>> getAuditorList(String departmentId, int ignoreNumber, int pageSize);

        Observable<BaseBean<Object>> submitManageInfo(Map<String, Object> map);

    }
}
