package com.weique.overhaul.v2.mvp.model.entity;

import java.util.List;

/**
 * @author GK
 * @description:
 * @date :2020/11/1 15:17
 */
public class MatterListBean {


    /**
     * list : [{"Code":"SXD000055","Title":"哭了哭了","Content":"来来来优诺京哦墨","ImgsInJson":null,"RecordUrl":null,"EventAddress":"CK兔兔","VideoUrl":null,"LocationInJson":"{\"lat\":34.234415,\"lng\":117.291885}","EnumOrderStatus":0,"EnumOrderStatusStr":"已核查","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320324198909013056","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"5762a533-9b6c-409d-ac65-1e1d487bed6e","CreateTime":"/Date(1604295224297+0800)/","CreateTimeStr":"2020/11/0213:33","UpdateTime":"/Date(1604295224297+0800)/","UpdateTimeStr":"2020/11/0213:33","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":""},{"Code":"SXD095047","Title":"1030上报事项","Content":"1030上报事项1030上报事项1030上报事项","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区思民路","VideoUrl":null,"LocationInJson":"{\"lng\":117.277507,\"lat\":34.178297}","EnumOrderStatus":3,"EnumOrderStatusStr":"正在处置","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/程庄社区/程庄社区网格2/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300198812010203","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"43fc8448-272f-495b-b763-a5b18f121fd5","CreateTime":"/Date(1604023944670+0800)/","CreateTimeStr":"2020/10/3010:12","UpdateTime":"/Date(1604024024807+0800)/","UpdateTimeStr":"2020/10/3010:13","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095047；【派单人】：管理员；【时间】：2020-10-3010:13#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095047；【受理人】：2020；【时间】：2020-10-3010:13"},{"Code":"SXD095039","Title":"测试订单","Content":"1111111111111111","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区黄河东路","VideoUrl":null,"LocationInJson":"{\"lng\":117.242709,\"lat\":34.190773}","EnumOrderStatus":3,"EnumOrderStatusStr":"正在处置","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/曹山社区/曹山社区网格1/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"111111111111111111","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"9c7ba847-8ea8-4789-b8c1-9bc3632c56d7","CreateTime":"/Date(1603952724323+0800)/","CreateTimeStr":"2020/10/2914:25","UpdateTime":"/Date(1603952818717+0800)/","UpdateTimeStr":"2020/10/2914:26","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/大龙湖街道/】【未配置单位】派出事项单据：SXD095039；【派单人】：2020；【时间】：2020-10-2914:26#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095039；【受理人】：2020；【时间】：2020-10-2914:26"},{"Code":"SXD095038","Title":"社区上报的第二个事项","Content":"社区上报的第二个事项社区上报的第二个事项社区上报的第二个事项社区上报的第二个事项社区上报的第二个事项","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区","VideoUrl":null,"LocationInJson":"{\"lng\":117.306103,\"lat\":34.210486}","EnumOrderStatus":3,"EnumOrderStatusStr":"正在处置","ReturnReason":null,"EnumEventLevel":1,"EnumEventLevelStr":"紧急事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/程庄社区/程庄社区网格5/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300193601020304","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":1,"PRSuperviseCount":0,"Id":"b9bfc13f-e124-4f7a-9953-fb35eb48efb9","CreateTime":"/Date(1603940813803+0800)/","CreateTimeStr":"2020/10/2911:06","UpdateTime":"/Date(1603940856367+0800)/","UpdateTimeStr":"2020/10/2911:07","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095038；【派单人】：管理员；【时间】：2020-10-2911:07#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095038；【受理人】：2020；【时间】：2020-10-2911:07#【徐州市云龙区/】【未配置该用户的单位】管理员发送'协同请求'至：【未配置该用户的单位】2024，【时间】：2020-10-2914:16"},{"Code":"SXD095036","Title":"1244","Content":"323343如果是发给","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区","VideoUrl":null,"LocationInJson":"{\"lng\":117.277184,\"lat\":34.17848}","EnumOrderStatus":4,"EnumOrderStatusStr":"处置完毕","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/程庄社区/程庄社区网格2/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300198812010203","DisposeContent":"#【徐州市云龙区/大龙湖街道/】【未配置单位名称】2020处置了该事项，并进行办结操作，【操作时间】：2020-10-2910:33【处置内容】：gsfrhgsfdgfgh","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"504c219a-61c7-4327-a356-fd2442347c8a","CreateTime":"/Date(1603938518307+0800)/","CreateTimeStr":"2020/10/2910:28","UpdateTime":"/Date(1603938780087+0800)/","UpdateTimeStr":"2020/10/2910:33","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095036；【派单人】：管理员；【时间】：2020-10-2910:29#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095036；【受理人】：2020；【时间】：2020-10-2910:30#【徐州市云龙区/大龙湖街道/】【未配置单位】已确认协同该事项单据：SXD095036；【受理人】：2021；【时间】：2020-10-2910:30#【徐州市云龙区/大龙湖街道/】【未配置单位名称】2020处置了该事项，并进行办结操作，【操作时间】：2020-10-2910:33【处置内容】：gsfrhgsfdgfgh"},{"Code":"SXD095034","Title":"102901事项","Content":"十月二十九上报的事项","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区","VideoUrl":null,"LocationInJson":"{\"lng\":117.276982,\"lat\":34.178424}","EnumOrderStatus":8,"EnumOrderStatusStr":"归档","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/程庄社区/程庄社区网格2/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300198812010203","DisposeContent":"#【徐州市云龙区/大龙湖街道/】【未配置单位名称】2020处置了该事项，并进行办结操作，【操作时间】：2020-10-2910:06【处置内容】：机构赛季狗肉馆","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"ab19c88f-d6d5-4cac-85d2-06a8a10c3d71","CreateTime":"/Date(1603936883857+0800)/","CreateTimeStr":"2020/10/2910:01","UpdateTime":"/Date(1603937237773+0800)/","UpdateTimeStr":"2020/10/2910:07","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095034；【派单人】：管理员；【时间】：2020-10-2910:02#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095034；【受理人】：2020；【时间】：2020-10-2910:03#【徐州市云龙区/大龙湖街道/】【未配置单位】已确认协同该事项单据：SXD095034；【受理人】：2021；【时间】：2020-10-2910:05#【徐州市云龙区/大龙湖街道/】【未配置单位名称】2020处置了该事项，并进行办结操作，【操作时间】：2020-10-2910:06【处置内容】：机构赛季狗肉馆#【徐州市云龙区/】【未配置单位名称】管理员评价了该事项单据，评价时间：【2020-10-2910:07】评价结果：满意#【徐州市云龙区/】【未配置单位名称】管理员执行了该事项单据的归档操作归档时间：【2020-10-2910:07】"},{"Code":"SXD095030","Title":"X子分类01","Content":"第三个审批服务申请事项","ImgsInJson":null,"RecordUrl":null,"EventAddress":"徐州市云龙区/大龙湖街道/程庄社区/","VideoUrl":null,"LocationInJson":"{\"lng\":117.305294,\"lat\":34.21124}","EnumOrderStatus":1,"EnumOrderStatusStr":"等待受理","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":3,"EnumSourceStr":"网格中心推送","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/程庄社区/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300198501020304","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"d7f8e688-1ceb-4b10-b248-bf8be1eff267","CreateTime":"/Date(1603872304753+0800)/","CreateTimeStr":"2020/10/2816:05","UpdateTime":"/Date(1603872304753+0800)/","UpdateTimeStr":"2020/10/2816:05","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/大龙湖街道/程庄社区/】【未配置单位】派出事项单据：SXD095030；【派单人】：2022；【时间】：2020-10-2816:12"},{"Code":"SXD095027","Title":"102810事项","Content":"十月二十八日上报的第十个事项","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区惠民东支路","VideoUrl":null,"LocationInJson":"{\"lng\":117.27958,\"lat\":34.179652}","EnumOrderStatus":1,"EnumOrderStatusStr":"等待受理","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/崔庄社区/崔庄社区网格4/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320322195602010203","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"d36c265c-d2f3-42ae-9600-0a67cc5aa0e8","CreateTime":"/Date(1603867351037+0800)/","CreateTimeStr":"2020/10/2814:42","UpdateTime":"/Date(1603867351037+0800)/","UpdateTimeStr":"2020/10/2814:42","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095027；【派单人】：管理员；【时间】：2020-10-2814:42#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095027；【派单人】：管理员；【时间】：2020-10-2814:44"},{"Code":"SXD095026","Title":"102808事项","Content":"十月二十八日上报的第八个事项","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区","VideoUrl":null,"LocationInJson":"{\"lng\":117.250596,\"lat\":34.199106}","EnumOrderStatus":3,"EnumOrderStatusStr":"正在处置","ReturnReason":null,"EnumEventLevel":1,"EnumEventLevelStr":"紧急事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/段山社区/段山社区网格2/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300185601050609","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"25914aae-cb7a-4ba8-9346-84d5fe82dbdc","CreateTime":"/Date(1603864981803+0800)/","CreateTimeStr":"2020/10/2814:03","UpdateTime":"/Date(1603865335557+0800)/","UpdateTimeStr":"2020/10/2814:08","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095026；【派单人】：管理员；【时间】：2020-10-2814:05#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095026；【派单人】：管理员；【时间】：2020-10-2814:07#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095026；【受理人】：2020；【时间】：2020-10-2814:08#【徐州市云龙区/】【未配置该用户的单位】管理员发送'协同请求'至：【未配置该用户的单位】2022，【时间】：2020-10-2814:27#【徐州市云龙区/】【未配置该用户的单位】管理员发送'协同请求'至：【翠屏山街道安监科】100，【时间】：2020-10-2814:28#【徐州市云龙区/大龙湖街道/程庄社区/】【未配置单位】拒绝协同该事项：SXD095026；【操作人】：2022【时间】：2020-10-2814:29【拒绝原因】：暂不参与该协同事项#【徐州市云龙区/大龙湖街道/】【未配置单位】拒绝协同该事项：SXD095026；【操作人】：2021【时间】：2020-10-2814:31【拒绝原因】：不愿参加协同事项#【徐州市云龙区/翠屏山街道/】【翠屏山街道安监科】已确认协同该事项单据：SXD095026；【受理人】：100；【时间】：2020-10-2814:40"},{"Code":"SXD095024","Title":"102806事项","Content":"十月二十八日上报的第六个事项","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区思民路管理中心","VideoUrl":null,"LocationInJson":"{\"lng\":117.27629,\"lat\":34.176942}","EnumOrderStatus":4,"EnumOrderStatusStr":"处置完毕","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/大王庙社区/大王庙社区网格5/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300195601020305","DisposeContent":"#【徐州市云龙区/大龙湖街道/】【未配置单位名称】2020处置了该事项，并进行办结操作，【操作时间】：2020-10-2813:45【处置内容】：处置完成经度","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"7bd33f7b-780a-49c6-864f-a52f94b02210","CreateTime":"/Date(1603856905470+0800)/","CreateTimeStr":"2020/10/2811:48","UpdateTime":"/Date(1603863908260+0800)/","UpdateTimeStr":"2020/10/2813:45","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095024；【派单人】：管理员；【时间】：2020-10-2813:38#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095024；【受理人】：2020；【时间】：2020-10-2813:40#【徐州市云龙区/大龙湖街道/】【未配置单位名称】2020处置了该事项，并进行办结操作，【操作时间】：2020-10-2813:45【处置内容】：处置完成经度"},{"Code":"SXD095020","Title":"102801事项","Content":"十月二十八日上报的事项","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区思民路50号","VideoUrl":null,"LocationInJson":"{\"lng\":117.276991,\"lat\":34.178192}","EnumOrderStatus":11,"EnumOrderStatusStr":"撤回","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":1,"EnumSourceStr":"随手拍","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/程庄社区/程庄社区网格2/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300195212030506","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"444b172e-a593-4f49-a540-ff5361e8c90f","CreateTime":"/Date(1603847761040+0800)/","CreateTimeStr":"2020/10/2809:16","UpdateTime":"/Date(1603850756070+0800)/","UpdateTimeStr":"2020/10/2810:05","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095020；【派单人】：管理员；【时间】：2020-10-2809:57#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095020；【受理人】：2020；【时间】：2020-10-2810:00#【徐州市云龙区/】【未配置单位】撤回事项单据：SXD095020；【执行人】：管理员；【时间】：2020-10-2810:00【撤回原因】：撤回了，暂不下派#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095020；【派单人】：管理员；【时间】：2020-10-2810:01#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095020；【受理人】：2020；【时间】：2020-10-2810:05#【徐州市云龙区/】【未配置单位】撤回事项单据：SXD095020；【执行人】：管理员；【时间】：2020-10-2810:05【撤回原因】：再次撤回，不予处理"},{"Code":"SXD095019","Title":"102707事项","Content":"十月二十七日上报的紧急事项","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区惠民小区A区","VideoUrl":null,"LocationInJson":"{\"lng\":117.252036,\"lat\":34.215353}","EnumOrderStatus":11,"EnumOrderStatusStr":"撤回","ReturnReason":null,"EnumEventLevel":1,"EnumEventLevelStr":"紧急事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/崔庄社区/崔庄社区网格6/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300165201020103","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"8b2e51fd-bfd4-49ef-ad72-7911fd02f39a","CreateTime":"/Date(1603788174397+0800)/","CreateTimeStr":"2020/10/2716:42","UpdateTime":"/Date(1603788429397+0800)/","UpdateTimeStr":"2020/10/2716:47","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095019；【派单人】：管理员；【时间】：2020-10-2716:46#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095019；【受理人】：2020；【时间】：2020-10-2716:46#【徐州市云龙区/】【未配置单位】撤回事项单据：SXD095019；【执行人】：管理员；【时间】：2020-10-2716:47【撤回原因】：风大哥法国发过"},{"Code":"SXD095018","Title":"102705事件","Content":"10月27日上报的第五个事件","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区","VideoUrl":null,"LocationInJson":"{\"lng\":117.238726,\"lat\":34.185099}","EnumOrderStatus":3,"EnumOrderStatusStr":"正在处置","ReturnReason":null,"EnumEventLevel":0,"EnumEventLevelStr":"普通事项","EnumSource":0,"EnumSourceStr":"来自12345","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/曹山社区/曹山社区网格5/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300195602010203","DisposeContent":"","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"0dbce13f-59b5-4693-afdc-78b7c2fdc4fe","CreateTime":"/Date(1603783023067+0800)/","CreateTimeStr":"2020/10/2715:17","UpdateTime":"/Date(1603785167720+0800)/","UpdateTimeStr":"2020/10/2715:52","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095018；【派单人】：管理员；【时间】：2020-10-2715:18#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095018；【派单人】：管理员；【时间】：2020-10-2715:27#【徐州市云龙区/】【未配置单位】撤回事项单据：SXD095018；【执行人】：管理员；【时间】：2020-10-2715:28【撤回原因】：交给非噶尔嘎达法尔#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095018；【派单人】：管理员；【时间】：2020-10-2715:30#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095018；【派单人】：管理员；【时间】：2020-10-2715:45#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095018；【派单人】：管理员；【时间】：2020-10-2715:51#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095018；【受理人】：2020；【时间】：2020-10-2715:52#【徐州市云龙区/大龙湖街道/】【未配置单位】已确认协同该事项单据：SXD095018；【受理人】：2021；【时间】：2020-10-2715:54#【徐州市云龙区/】【未配置该用户的单位】管理员发送'协同请求'至：【未配置该用户的单位】2022，【时间】：2020-10-2716:16"},{"Code":"SXD095016","Title":"102703事件","Content":"10月27日上报的第三个事件","ImgsInJson":null,"RecordUrl":null,"EventAddress":"江苏省徐州市云龙区丽水路","VideoUrl":null,"LocationInJson":"{\"lng\":117.286394,\"lat\":34.195535}","EnumOrderStatus":8,"EnumOrderStatusStr":"归档","ReturnReason":null,"EnumEventLevel":1,"EnumEventLevelStr":"紧急事项","EnumSource":4,"EnumSourceStr":"市长信箱","SourceId":"00000000-0000-0000-0000-000000000000","DepartmentId":"00000000-0000-0000-0000-000000000000","DepartmentFullPath":"徐州市云龙区/大龙湖街道/汉风社区/汉风社区网格5/","CreateEmName":null,"CreateEmTel":null,"CreateEmSID":"320300196301020304","DisposeContent":"#【徐州市云龙区/大龙湖街道/】【未配置单位名称】2020处置了该事项，并进行办结操作，【操作时间】：2020-10-2715:01【处置内容】：时间覅骄傲欸给","DeadlineTime":null,"DeadlineTimeStr":"无","CancelReason":null,"ReminderCount":0,"PRSuperviseCount":0,"Id":"cf2f3284-67ba-4db1-a456-9062d7e82153","CreateTime":"/Date(1603780030690+0800)/","CreateTimeStr":"2020/10/2714:27","UpdateTime":"/Date(1603782416953+0800)/","UpdateTimeStr":"2020/10/2715:06","CreateUId":"00000000-0000-0000-0000-000000000000","CreateEmployeeName":null,"UpdateUId":"00000000-0000-0000-0000-000000000000","UpdateEmployeeName":null,"Memo":"#【徐州市云龙区/】【未配置单位】派出事项单据：SXD095016；【派单人】：管理员；【时间】：2020-10-2714:29#【徐州市云龙区/大龙湖街道/】【未配置单位名称】已受理事项单据：SXD095016；【受理人】：2020；【时间】：2020-10-2714:33#【徐州市云龙区/大龙湖街道/】【未配置单位名称】2020处置了该事项，并进行办结操作，【操作时间】：2020-10-2715:01【处置内容】：时间覅骄傲欸给#【徐州市云龙区/】【未配置单位名称】管理员评价了该事项单据，评价时间：【2020-10-2715:05】评价结果：满意#【徐州市云龙区/】【未配置单位名称】管理员执行了该事项单据的归档操作归档时间：【2020-10-2715:06】"}]
     * pageCount : 1
     * totalCount : 14
     */

    private int pageCount;
    private int totalCount;
    private List<ListBean> list;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * Code : SXD000055
         * Title : 哭了哭了
         * Content : 来来来优诺京哦墨
         * ImgsInJson : null
         * RecordUrl : null
         * EventAddress : CK兔兔
         * VideoUrl : null
         * LocationInJson : {"lat":34.234415,"lng":117.291885}
         * EnumOrderStatus : 0
         * EnumOrderStatusStr : 已核查
         * ReturnReason : null
         * EnumEventLevel : 0
         * EnumEventLevelStr : 普通事项
         * EnumSource : 0
         * EnumSourceStr : 来自12345
         * SourceId : 00000000-0000-0000-0000-000000000000
         * DepartmentId : 00000000-0000-0000-0000-000000000000
         * DepartmentFullPath : 徐州市云龙区/大龙湖街道/
         * CreateEmName : null
         * CreateEmTel : null
         * CreateEmSID : 320324198909013056
         * DisposeContent :
         * DeadlineTime : null
         * DeadlineTimeStr : 无
         * CancelReason : null
         * ReminderCount : 0
         * PRSuperviseCount : 0
         * Id : 5762a533-9b6c-409d-ac65-1e1d487bed6e
         * CreateTime : /Date(1604295224297+0800)/
         * CreateTimeStr : 2020/11/0213:33
         * UpdateTime : /Date(1604295224297+0800)/
         * UpdateTimeStr : 2020/11/0213:33
         * CreateUId : 00000000-0000-0000-0000-000000000000
         * CreateEmployeeName : null
         * UpdateUId : 00000000-0000-0000-0000-000000000000
         * UpdateEmployeeName : null
         * Memo :
         */

        private String Code;
        private String Title;
        private String Content;
        private String ImgsInJson;
        private String RecordUrl;
        private String EventAddress;
        private String VideoUrl;
        private String LocationInJson;
        private int EnumOrderStatus;
        private String EnumOrderStatusStr;
        private String ReturnReason;
        private int EnumEventLevel;
        private String EnumEventLevelStr;
        private int EnumSource;
        private String EnumSourceStr;
        private String SourceId;
        private String DepartmentId;
        private String DepartmentFullPath;
        private String CreateEmName;
        private String CreateEmTel;
        private String CreateEmSID;
        private String DisposeContent;
        private String DeadlineTime;
        private String DeadlineTimeStr;
        private String CancelReason;
        private int ReminderCount;
        private int PRSuperviseCount;
        private String Id;
        private String EventRecordId;
        private String CreateTime;
        private String CreateTimeStr;
        private String UpdateTime;
        private String UpdateTimeStr;
        private String CreateUId;
        private String CreateEmployeeName;
        private String UpdateUId;
        private String UpdateEmployeeName;
        private String EnumEventProceedStatusStr;
        private String Memo;

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getImgsInJson() {
            return ImgsInJson;
        }

        public void setImgsInJson(String ImgsInJson) {
            this.ImgsInJson = ImgsInJson;
        }

        public String getRecordUrl() {
            return RecordUrl;
        }

        public void setRecordUrl(String RecordUrl) {
            this.RecordUrl = RecordUrl;
        }

        public String getEventAddress() {
            return EventAddress;
        }

        public void setEventAddress(String EventAddress) {
            this.EventAddress = EventAddress;
        }

        public String getVideoUrl() {
            return VideoUrl;
        }

        public void setVideoUrl(String VideoUrl) {
            this.VideoUrl = VideoUrl;
        }

        public String getLocationInJson() {
            return LocationInJson;
        }

        public void setLocationInJson(String LocationInJson) {
            this.LocationInJson = LocationInJson;
        }

        public int getEnumOrderStatus() {
            return EnumOrderStatus;
        }

        public void setEnumOrderStatus(int EnumOrderStatus) {
            this.EnumOrderStatus = EnumOrderStatus;
        }

        public String getEnumOrderStatusStr() {
            return EnumOrderStatusStr;
        }

        public void setEnumOrderStatusStr(String EnumOrderStatusStr) {
            this.EnumOrderStatusStr = EnumOrderStatusStr;
        }

        public String getReturnReason() {
            return ReturnReason;
        }

        public void setReturnReason(String ReturnReason) {
            this.ReturnReason = ReturnReason;
        }

        public int getEnumEventLevel() {
            return EnumEventLevel;
        }

        public void setEnumEventLevel(int EnumEventLevel) {
            this.EnumEventLevel = EnumEventLevel;
        }

        public String getEnumEventLevelStr() {
            return EnumEventLevelStr;
        }

        public void setEnumEventLevelStr(String EnumEventLevelStr) {
            this.EnumEventLevelStr = EnumEventLevelStr;
        }

        public int getEnumSource() {
            return EnumSource;
        }

        public void setEnumSource(int EnumSource) {
            this.EnumSource = EnumSource;
        }

        public String getEnumSourceStr() {
            return EnumSourceStr;
        }

        public void setEnumSourceStr(String EnumSourceStr) {
            this.EnumSourceStr = EnumSourceStr;
        }

        public String getSourceId() {
            return SourceId;
        }

        public void setSourceId(String SourceId) {
            this.SourceId = SourceId;
        }

        public String getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(String DepartmentId) {
            this.DepartmentId = DepartmentId;
        }

        public String getDepartmentFullPath() {
            return DepartmentFullPath;
        }

        public void setDepartmentFullPath(String DepartmentFullPath) {
            this.DepartmentFullPath = DepartmentFullPath;
        }

        public String getCreateEmName() {
            return CreateEmName;
        }

        public void setCreateEmName(String CreateEmName) {
            this.CreateEmName = CreateEmName;
        }

        public String getCreateEmTel() {
            return CreateEmTel;
        }

        public void setCreateEmTel(String CreateEmTel) {
            this.CreateEmTel = CreateEmTel;
        }

        public String getCreateEmSID() {
            return CreateEmSID;
        }

        public void setCreateEmSID(String CreateEmSID) {
            this.CreateEmSID = CreateEmSID;
        }

        public String getDisposeContent() {
            return DisposeContent;
        }

        public void setDisposeContent(String DisposeContent) {
            this.DisposeContent = DisposeContent;
        }

        public String getDeadlineTime() {
            return DeadlineTime;
        }

        public void setDeadlineTime(String DeadlineTime) {
            this.DeadlineTime = DeadlineTime;
        }

        public String getDeadlineTimeStr() {
            return DeadlineTimeStr;
        }

        public void setDeadlineTimeStr(String DeadlineTimeStr) {
            this.DeadlineTimeStr = DeadlineTimeStr;
        }

        public String getCancelReason() {
            return CancelReason;
        }

        public void setCancelReason(String CancelReason) {
            this.CancelReason = CancelReason;
        }

        public int getReminderCount() {
            return ReminderCount;
        }

        public void setReminderCount(int ReminderCount) {
            this.ReminderCount = ReminderCount;
        }

        public int getPRSuperviseCount() {
            return PRSuperviseCount;
        }

        public void setPRSuperviseCount(int PRSuperviseCount) {
            this.PRSuperviseCount = PRSuperviseCount;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getCreateTimeStr() {
            return CreateTimeStr;
        }

        public void setCreateTimeStr(String CreateTimeStr) {
            this.CreateTimeStr = CreateTimeStr;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getUpdateTimeStr() {
            return UpdateTimeStr;
        }

        public void setUpdateTimeStr(String UpdateTimeStr) {
            this.UpdateTimeStr = UpdateTimeStr;
        }

        public String getCreateUId() {
            return CreateUId;
        }

        public void setCreateUId(String CreateUId) {
            this.CreateUId = CreateUId;
        }

        public String getCreateEmployeeName() {
            return CreateEmployeeName;
        }

        public void setCreateEmployeeName(String CreateEmployeeName) {
            this.CreateEmployeeName = CreateEmployeeName;
        }

        public String getUpdateUId() {
            return UpdateUId;
        }

        public void setUpdateUId(String UpdateUId) {
            this.UpdateUId = UpdateUId;
        }

        public String getUpdateEmployeeName() {
            return UpdateEmployeeName;
        }

        public void setUpdateEmployeeName(String UpdateEmployeeName) {
            this.UpdateEmployeeName = UpdateEmployeeName;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public String getEventRecordId() {
            return EventRecordId;
        }

        public void setEventRecordId(String eventRecordId) {
            EventRecordId = eventRecordId;
        }

        public String getEnumEventProceedStatusStr() {
            return EnumEventProceedStatusStr;
        }

        public void setEnumEventProceedStatusStr(String enumEventProceedStatusStr) {
            EnumEventProceedStatusStr = enumEventProceedStatusStr;
        }
    }
}
