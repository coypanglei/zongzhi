package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

public class IncidentReportingBean {


    /**
     * SelfCount : 7292
     * ReportCount : 1218
     * events : [{"Id":"f764a152-0bb1-4c76-9d2a-5ed94fe0c5ac","Name":"市政设施","FullPath":"全部/ 民生类/市政设施","Count":1370},{"Id":"683ef606-1f4e-45de-8703-74a34bfcfdb8","Name":"污水外溢","FullPath":"全部/ 民生类/污水外溢","Count":115},{"Id":"3bfbb593-8453-4b98-b777-c4ac15431d4d","Name":"其他","FullPath":"全部/ 其他","Count":979},{"Id":"2dfb2988-35cc-4147-8230-e7d9df8ea2f4","Name":"日期","FullPath":"全部/ 日期","Count":0},{"Id":"9b90b062-2d47-446b-a96d-002a053e8793","Name":"备注","FullPath":"全部/备注","Count":0},{"Id":"bafbc91c-e05a-4fc7-a69e-32fb429c9e0b","Name":"铁路安全隐患","FullPath":"全部/风险类/ 铁路安全隐患","Count":8},{"Id":"1e69c357-8314-4fd1-abc9-42db2ee80366","Name":"安全生产隐患","FullPath":"全部/风险类/安全生产隐患","Count":263},{"Id":"8c5ca3bc-2315-4cd1-936f-a4d05c411c9b","Name":"电梯等特种设备","FullPath":"全部/风险类/电梯等特种设备","Count":189},{"Id":"d9b33f43-3b95-4986-b312-e018133848c5","Name":"服务业非正常营业","FullPath":"全部/风险类/服务业非正常营业","Count":4},{"Id":"cd215c0c-fa85-48c9-afc4-51ea6d82ad87","Name":"检查督导","FullPath":"全部/风险类/检查督导","Count":158},{"Id":"07ef7ce3-b626-42c7-8a03-2f50846f9d81","Name":"金融业非正常营业","FullPath":"全部/风险类/金融业非正常营业","Count":2},{"Id":"184b7397-9a36-4286-a08a-68f547ac3988","Name":"居民消防隐患","FullPath":"全部/风险类/居民消防隐患","Count":2517},{"Id":"294dab9a-c565-4816-a2f8-023265e3c5b9","Name":"聚集维权纠纷","FullPath":"全部/风险类/聚集维权纠纷","Count":6},{"Id":"37909062-027f-4ff1-8864-f331faf3474c","Name":"企业消防隐患","FullPath":"全部/风险类/企业消防隐患","Count":169},{"Id":"2088c940-32e6-418a-ac9a-d01492aa7452","Name":"全民健身器材","FullPath":"全部/风险类/全民健身器材","Count":125},{"Id":"208685b1-af0e-444d-bac6-581dee1e3114","Name":"生产经营隐患","FullPath":"全部/风险类/生产经营隐患","Count":48},{"Id":"62dbafef-fe26-4039-a2b8-a2e34839bfa5","Name":"信访稳定","FullPath":"全部/风险类/信访稳定","Count":28},{"Id":"9b518c8a-5ff0-4e0a-957c-5d4148f90b1d","Name":"液化气隐患","FullPath":"全部/风险类/液化气隐患","Count":32},{"Id":"582a5d2c-30d5-4206-88fc-34109b60a8c5","Name":"安全生产","FullPath":"全部/公安预警推送类事件专用/ 安全生产","Count":4},{"Id":"34711aa3-9c36-4330-8501-1be0afe65bd6","Name":"公共安全","FullPath":"全部/公安预警推送类事件专用/ 公共安全","Count":11},{"Id":"cdae2c51-cc35-4093-bbc0-e8822d1af3f5","Name":"露天焚烧","FullPath":"全部/环保管理/露天焚烧","Count":16},{"Id":"9a0dffe2-f3ab-4040-8064-05d67bbd5723","Name":"违规排放","FullPath":"全部/环保管理/违规排放","Count":130},{"Id":"66ba2661-52a9-4a95-9469-4fd369c3fbc3","Name":"渔业保护","FullPath":"全部/农业生产/渔业保护","Count":1},{"Id":"85e3de8a-298e-456d-8c41-a9340015b698","Name":"服务热线","FullPath":"全部/区中心预警推送/服务热线","Count":0},{"Id":"91394ac3-a0bd-429d-a2cb-85310999bd3c","Name":"非法虚假广告行为","FullPath":"全部/市场管理/非法虚假广告行为","Count":838},{"Id":"5141d8e8-038d-498d-b9e5-3a25e34f6553","Name":"社会力量办学机构","FullPath":"全部/市场管理/社会力量办学机构","Count":16},{"Id":"8779e5c1-ad50-4730-be50-b05bd837f8d9","Name":"学生小饭桌排查","FullPath":"全部/市场管理/学生小饭桌排查","Count":34},{"Id":"4c801918-809b-426c-9a85-2877621b3ad5","Name":"绿化保护","FullPath":"全部/市容管理/绿化保护","Count":942},{"Id":"3562aca2-5bfd-437d-8f9b-17bac411eb65","Name":"违章搭建","FullPath":"全部/市容管理/违章搭建","Count":123},{"Id":"0cbce76f-5c83-4c7d-b031-fc87505b816c","Name":"信访","FullPath":"全部/预警推送类/信访","Count":53},{"Id":"5d318a27-24aa-4306-8115-ee187e275f1f","Name":"宗教活动协管","FullPath":"全部/宗教管理/宗教活动协管","Count":2}]
     */

    private int SelfCount;
    private int ReportCount;
    private List<GridDataBean> events;

    public int getSelfCount() {
        return SelfCount;
    }

    public void setSelfCount(int SelfCount) {
        this.SelfCount = SelfCount;
    }

    public int getReportCount() {
        return ReportCount;
    }

    public void setReportCount(int ReportCount) {
        this.ReportCount = ReportCount;
    }

    public List<GridDataBean> getEvents() {
        return events;
    }

    public void setEvents(List<GridDataBean> events) {
        this.events = events;
    }

    public static class EventsBean {
    }
}
