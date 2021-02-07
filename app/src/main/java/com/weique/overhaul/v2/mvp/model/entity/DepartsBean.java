package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2021/1/26 15:21
 */
public class DepartsBean {

    /**
     * items : [{"id":"6fe0ae33-b3d8-4dc1-a607-50c16e357ec5","text":"静海镇安全办(调度)"},{"id":"94e19ad1-6f20-4934-8032-50cfe53021cb","text":"网格员(调度)"},{"id":"ac25b86c-ea18-4010-b1bd-8eef4f3c24e4","text":"系统默认部门(调度)"},{"id":"b8de6196-d27c-453d-9f29-c911ed7f51ac","text":"金海园邻里服务站事件处置中心(调度)"},{"id":"4cda9c16-3272-4462-a959-33743c69a557","text":"测试(上报)"},{"id":"909edf8d-3cfc-4566-845f-9241c65a60c8","text":"天津市静海区(上报)"},{"id":"fef320be-7cdb-4abb-8f61-cc9212186106","text":"事件控制平台(上报)"},{"id":"eb040004-208d-4410-b148-576f80201d3e","text":"街道事件处置中心(下派)"}]
     * total_count : 8
     */

    private int total_count;
    private List<NameAndIdBean> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<NameAndIdBean> getItems() {
        return items;
    }

    public void setItems(List<NameAndIdBean> items) {
        this.items = items;
    }

}
