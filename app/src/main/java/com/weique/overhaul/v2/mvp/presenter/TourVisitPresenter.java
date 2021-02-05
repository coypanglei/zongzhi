package com.weique.overhaul.v2.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.weique.overhaul.v2.app.ReworkBasePresenter;
import com.weique.overhaul.v2.app.common.CommonNetworkRequest;
import com.weique.overhaul.v2.app.common.Constant;
import com.weique.overhaul.v2.app.utils.CommonPermissionUtil;
import com.weique.overhaul.v2.mvp.contract.TourVisitContract;
import com.weique.overhaul.v2.mvp.model.entity.UpDateTourVistBean;
import com.weique.overhaul.v2.mvp.ui.activity.visit.TourVisitActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/03/2019 16:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class TourVisitPresenter extends ReworkBasePresenter<TourVisitContract.Model, TourVisitContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    IRepositoryManager iRepositoryManager;

    @Inject
    public TourVisitPresenter(TourVisitContract.Model model, TourVisitContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void setTourVisitData(boolean b, boolean b1, UpDateTourVistBean toJson) {
        commonGetData(mModel.setTourVisitData(toJson), mErrorHandler, itemBean -> {
            mRootView.setTourVisitData(itemBean);
        });

    }


    /**
     * 上传文件
     *
     * @param compressPaths compressPath 文件压缩路径
     */
    public void upLoadFile(String elementId, List<String> compressPaths) {

        CommonNetworkRequest.upLoadFile(elementId, compressPaths,
                mErrorHandler, mRootView, iRepositoryManager,
                uploadFileRsponseBeans -> {
                    mRootView.updatePicture(uploadFileRsponseBeans);
                });
    }

    public void getPermission(int maxImgCount, ArrayList<ImageItem> selImageList, List<String> names) {
        try {
            CommonPermissionUtil.getPermission(mRootView.getActivity(), mErrorHandler, () -> {
                try {
                    mRootView.showDialog((parent, view, position, id) -> {
                        try {
                            switch (position) {
                                case 0: // 直接调起相机
                                    /*
                                     * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，
                                     *
                                     * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                     *
                                     * 如果实在有所需要，请直接下载源码引用。
                                     */
                                    //打开选择,本次允许选择的数量
                                    ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                    Intent intent = new Intent(mRootView.getActivity(), ImageGridActivity.class);
                                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                    mRootView.getActivity().startActivityForResult(intent, TourVisitActivity.REQUEST_CODE_SELECT);
                                    break;
                                case 1:
                                    //打开选择,本次允许选择的数量
                                    ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                    Intent intent1 = new Intent(mRootView.getActivity(), ImageGridActivity.class);
                                    /* 如果需要进入选择的时候显示已经选中的图片，
                                     * 详情请查看ImagePickerActivity
                                     * */
                                    //                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                    mRootView.getActivity().startActivityForResult(intent1, TourVisitActivity.REQUEST_CODE_SELECT);
                                    break;
                                default:
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }, names);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, Constant.PERMISSIONS_LIST_READ_WRITE_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
