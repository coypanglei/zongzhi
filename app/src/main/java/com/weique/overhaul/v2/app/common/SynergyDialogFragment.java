package com.weique.overhaul.v2.app.common;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.ViewHolder;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.NetworkUtils;
import com.weique.overhaul.v2.app.utils.SignUtil;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.app.utils.UserInfoUtil;
import com.weique.overhaul.v2.mvp.model.api.service.EventsReportedService;
import com.weique.overhaul.v2.mvp.model.entity.NameAndIdBean;
import com.weique.overhaul.v2.mvp.ui.adapter.CommonRecyclerPopupAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author GK
 * @description:
 * @date :2020/11/10 13:59
 */
public class SynergyDialogFragment extends BaseNiceDialog {

    private RecyclerView synergyRecyclerView;
    private CommonRecyclerPopupAdapter<NameAndIdBean> synergyAdapter;
    private IRepositoryManager mRepositoryManager;
    private RxErrorHandler mErrorHandler;
    private IView mRootView;

    @Override
    public int intLayoutId() {
        return R.layout.dialog_order_request_synergy;
    }

    public SynergyDialogFragment(IRepositoryManager iRepositoryManager,
                                 RxErrorHandler errorHandler,
                                 IView rootView) {
        mRepositoryManager = iRepositoryManager;
        mErrorHandler = errorHandler;
        mRootView = rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getDialog() != null) {
            try {
                // 解决Dialog内存存泄漏
                getDialog().setOnDismissListener(null);
                getDialog().setOnCancelListener(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        EditText searchText = holder.getView(R.id.search_text);
        EditText editText = holder.getView(R.id.content);
        synergyRecyclerView = holder.getView(R.id.recycler_view);
        holder.setOnClickListener(R.id.search, v -> {
            if (StringUtil.isNullString(searchText.getText().toString())) {
                ArmsUtils.makeText("请输入协同人姓名");
                return;
            }
            Map<String, Object> map = new HashMap<>(8);
            map.put("q", searchText.getText().toString());
            NetworkUtils.commonGetData(mRepositoryManager
                    .obtainRetrofitService(EventsReportedService.class)
                    .getUserInfoForCoop(SignUtil.paramSign(map)), mErrorHandler, mRootView, o -> {
                synergyAdapter = new CommonRecyclerPopupAdapter<>();
                ArmsUtils.configRecyclerView(synergyRecyclerView, new LinearLayoutManager(getContext()));
                synergyAdapter.setOnItemChildClickListener((adapter1, view, position) -> {
                    try {
                        synergyAdapter.setCheckPos(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                synergyAdapter.setEmptyView(R.layout.null_content_home_layout, synergyRecyclerView);
                synergyRecyclerView.setAdapter(synergyAdapter);
                synergyAdapter.setNewData(o);
            });
        });
        holder.setOnClickListener(R.id.confirm, v -> {
            if (synergyAdapter == null || synergyAdapter.getListByPosList().size() <= 0) {
                ArmsUtils.makeText("请搜索并选择协同人");
                return;
            }
            if (StringUtil.isNullString(editText.getText().toString())) {
                ArmsUtils.makeText("请填写协同说明");
                return;
            }
            List<NameAndIdBean> listByPosList = synergyAdapter.getListByPosList();
            List<String> ids = new ArrayList<>();
            Map<String, Object> synergyMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            for (NameAndIdBean andIdBean : listByPosList) {
                ids.add(andIdBean.getId());
            }
            paramMap.put("CoopInfo.SelectedUserCoopList", new Gson().toJson(ids));
            paramMap.put("CoopInfo.coopDesc", editText.getText().toString());
//            paramMap.put("EventRecordId", eventRecordId);
            paramMap.put("UserId", UserInfoUtil.getUserInfo().getUid());
            synergyMap.put("result", paramMap);
//            mPresenter.requestSynergy(synergyMap);
            NetworkUtils.commonGetData(mRepositoryManager
                    .obtainRetrofitService(EventsReportedService.class)
                    .getUserInfoForCoop(SignUtil.paramSign(synergyMap)), mErrorHandler, mRootView, o -> {
            });
        });
    }
}
