package com.weique.overhaul.v2.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.weique.overhaul.v2.R;
import com.weique.overhaul.v2.app.utils.StringUtil;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GK
 */
public class StandardAddressOneAdapter extends BaseQuickAdapter<StandardAddressStairBean.StandardAddressStairBaseBean, BaseViewHolder> {

    private List<List<LatLng>> latLngLists;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public StandardAddressOneAdapter(int layoutResId, @Nullable List<StandardAddressStairBean.StandardAddressStairBaseBean> data) {
        super(layoutResId, data);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, StandardAddressStairBean.StandardAddressStairBaseBean item) {
        try {
            helper.setText(R.id.name, (item.getName()));
            helper.setText(R.id.one_value, (item.getAddressCode()));
            helper.setText(R.id.full_path_value, (item.getFullPath()));
            helper.addOnClickListener(R.id.all_item);
            /*TextView infoLack = helper.getView(R.id.info_lack);
            TextView localError = helper.getView(R.id.local_error);
            if (StringUtil.isNullString(item.getName()) ||
                    StringUtil.isNullString(item.getAddressCode()) ||
                    StringUtil.isNullString(item.getFullPath())) {
                infoLack.setVisibility(View.VISIBLE);
            } else {
                infoLack.setVisibility(View.GONE);
            }
            if (StringUtil.isNullString(item.getGisLocation())) {
                localError.setText("无定位");
                localError.setVisibility(View.VISIBLE);
            } else {
                if (latLngLists != null && latLngLists.size() > 0) {
                    try {
                        StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                new Gson().fromJson(item.getGisLocation(), StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean.class);
                        LatLng latLng = new LatLng(pathBean.getLat(), pathBean.getLng());
                        if (!isPolygonContainsPoint(latLng)) {
                            localError.setText("定位偏离");
                            localError.setText(View.VISIBLE);
                        } else {
                            localError.setVisibility(View.GONE);
                        }
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        localError.setText("定位信息异常");
                        localError.setVisibility(View.VISIBLE);
                    }
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 网格信息
     *
     * @param pointsInJSON pointsInJSON
     */
    public void setGridArea(String pointsInJSON) {
        if (StringUtil.isNullString(pointsInJSON)) {
            return;
        }
        try {
            latLngLists = new ArrayList<>();
            StandardAddressStairBean.PointsInJsonBean pointsInJsonBean = new Gson().fromJson(pointsInJSON, StandardAddressStairBean.PointsInJsonBean.class);
            List<StandardAddressStairBean.PointsInJsonBean.PolygoysBean> polygoysBeans = pointsInJsonBean.getPolygoys();
            List<LatLng> latLngs;
            for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean polygoysBean : polygoysBeans) {
                latLngs = new ArrayList<>();
                LatLng latLng;
                if (polygoysBean != null && polygoysBean.getPath() != null && polygoysBean.getPath().size() > 0) {
                    for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean : polygoysBean.getPath()) {
                        latLng = new LatLng(pathBean.getLat(), pathBean.getLng());
                        latLngs.add(latLng);
                    }
                }
                latLngLists.add(latLngs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isPolygonContainsPoint(LatLng latLng) {
        boolean isPolygonContains = false;
        for (List<LatLng> lists : latLngLists) {
            if (SpatialRelationUtil.isPolygonContainsPoint(lists, latLng)) {
                isPolygonContains = true;
                break;
            }
        }
        return isPolygonContains;
    }
}
