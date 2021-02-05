package com.weique.overhaul.v2.app.utils;

import android.graphics.Color;

import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;
import com.weique.overhaul.v2.mvp.model.entity.AdminGridBean;
import com.weique.overhaul.v2.mvp.model.entity.StandardAddressStairBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GK
 * @description: 用户网格  相关的判断
 * @date :2020/6/24 16:35
 */
public class UserGridUtil {
    /**
     * @param gridJson    网格json
     * @param latLngLists 网格 LatLng 集合
     * @param ooPolygons  多边形信息
     */
    public static void gridJsonToListLatLng(String gridJson,
                                            List<List<LatLng>> latLngLists,
                                            List<PolygonOptions> ooPolygons) {
        if (StringUtil.isNotNullString(gridJson)) {
            LatLng latLng;
            List<StandardAddressStairBean.PointsInJsonBean.PolygoysBean> polygoysBeans = null;
            try {
                StandardAddressStairBean.PointsInJsonBean pointsInJsonBean = new Gson().fromJson(gridJson, StandardAddressStairBean.PointsInJsonBean.class);
                polygoysBeans = pointsInJsonBean.getPolygoys();
                if (polygoysBeans == null) {
                    throw new Exception();
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    AdminGridBean adminGridInfoBean = new Gson().fromJson(gridJson, AdminGridBean.class);
                    polygoysBeans = new ArrayList<>();
                    for (AdminGridBean.AdministrativesBean bean : adminGridInfoBean.getAdministratives()) {
                        StandardAddressStairBean.PointsInJsonBean.PolygoysBean polygoysBean = new StandardAddressStairBean.PointsInJsonBean.PolygoysBean();
                        String administrative = bean.getAdministrative();
                        String[] split = administrative.split(";");

                        List<StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean> pathBeans = new ArrayList<>();
                        for (String ss : split) {
                            StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean =
                                    new StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean();
                            String[] sss = ss.split(",");
                            pathBean.setLng(Double.parseDouble(sss[0]));
                            pathBean.setLat(Double.parseDouble(sss[1]));
                            pathBeans.add(pathBean);
                        }
                        AdminGridBean.AdministrativesBean.AdministrativeStyleBean administrativeStyle = bean.getAdministrativeStyle();
                        StandardAddressStairBean.PointsInJsonBean.PolygoysBean.StyleOptionsBean styleOptionsBean =
                                new StandardAddressStairBean.PointsInJsonBean.PolygoysBean.StyleOptionsBean();
                        styleOptionsBean.setFillColor(administrativeStyle.getFillColor());
                        styleOptionsBean.setFillOpacity(administrativeStyle.getFillOpacity());
                        styleOptionsBean.setStrokeColor(administrativeStyle.getStrokeColor());
                        styleOptionsBean.setStrokeOpacity(administrativeStyle.getStrokeOpacity());
                        styleOptionsBean.setStrokeStyle(administrativeStyle.getStrokeStyle());
                        styleOptionsBean.setStrokeWeight(administrativeStyle.getStrokeWeight());
                        polygoysBean.setStyleOptions(styleOptionsBean);
                        polygoysBean.setPath(pathBeans);
                        polygoysBeans.add(polygoysBean);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    ArmsUtils.makeText("网格渲染失败");
                }
            }
            if (polygoysBeans != null && polygoysBeans.size() > 0) {
                for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean polygoys : polygoysBeans) {
                    List<LatLng> latLngList = new ArrayList<>();
                    if (polygoys != null && polygoys.getPath() != null && polygoys.getPath().size() > 0) {
                        for (StandardAddressStairBean.PointsInJsonBean.PolygoysBean.PathBean pathBean : polygoys.getPath()) {
                            latLng = new LatLng(pathBean.getLat(), pathBean.getLng());
                            latLngList.add(latLng);
                        }
                        //2 是两个点不成面
                        if (latLngList.size() > 2) {
                            ooPolygons.add(new PolygonOptions()
                                    .points(latLngList)
                                    .stroke(new Stroke(5, Color.parseColor(polygoys.getStyleOptions().getStrokeColor().replace("#", "#40"))))
                                    .fillColor(Color.parseColor(polygoys.getStyleOptions().getFillColor().replace("#", "#40"))));
                            latLngLists.add(latLngList);
                        }
                    }
                }
            }
        }
    }

    /**
     * 判断点是否在网格内
     *
     * @param latLng latLng
     * @return boolean
     */
    public static boolean pointInGrid(LatLng latLng, List<List<LatLng>> latLngLists) {
        boolean isPolygonContains = false;
        for (List<LatLng> latLngs : latLngLists) {
            if (SpatialRelationUtil.isPolygonContainsPoint(latLngs, latLng)) {
                isPolygonContains = true;
                break;
            }
        }
        return isPolygonContains;
    }
}
