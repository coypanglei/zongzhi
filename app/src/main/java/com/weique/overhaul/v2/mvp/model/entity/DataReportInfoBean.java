package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class DataReportInfoBean {


    /**
     * typeName : 网格员
     * departmentName : 徐州市鼓楼区
     * departments : [{"Id":"bb25830d-f8fe-4b33-ab31-1cc08a5bc106","Name":"丰财街道办事处","FullPath":"徐州市鼓楼区/丰财街道办事处/","Level":2,"Count":62},{"Id":"839901b2-b245-494d-b2f4-9a45ee6c36c6","Name":"环城街道办事处","FullPath":"徐州市鼓楼区/环城街道办事处/","Level":2,"Count":60},{"Id":"d79d00a1-030a-412f-b80a-610f7acc018d","Name":"黄楼街道办事处","FullPath":"徐州市鼓楼区/黄楼街道办事处/","Level":2,"Count":24},{"Id":"5b42fd23-d110-45a0-91cb-652739c95f2c","Name":"九里街道办事处","FullPath":"徐州市鼓楼区/九里街道办事处/","Level":2,"Count":39},{"Id":"aa760fad-fb89-402c-beeb-84a5be7bfc55","Name":"牌楼街道办事处","FullPath":"徐州市鼓楼区/牌楼街道办事处/","Level":2,"Count":38},{"Id":"2f830ce5-5266-4cb6-91ee-4a5873762af7","Name":"琵琶街道办事处","FullPath":"徐州市鼓楼区/琵琶街道办事处/","Level":2,"Count":0},{"Id":"d14ff50d-6423-48c8-88b9-0d1172e70849","Name":"铜沛街道办事处","FullPath":"徐州市鼓楼区/铜沛街道办事处/","Level":2,"Count":20},{"Id":"470a733b-2d00-41cc-b2e6-020ddadd3851","Name":"武装部专属网格","FullPath":"徐州市鼓楼区/武装部专属网格/","Level":2,"Count":0}]
     */

    private String typeName;
    private String departmentName;
    private List<GridDataBean> departments;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<GridDataBean> getDepartments() {
        return departments;
    }

    public void setDepartments(List<GridDataBean> departments) {
        this.departments = departments;
    }


}
