package com.weique.overhaul.v2.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.weique.overhaul.v2.mvp.model.entity.interfaces.NameAndIdInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息采集  下的两种数据
 *
 * @author GreatKing
 */
public class InformationTypeOneSecondBean {

    /**
     * pageCount : 1
     * elementTypeList : [{"Id":"9349a65f-14ce-4b77-9a34-45586170e726","Name":"公司信息填报","AnotherName":null,"PinYin":"GSXXTB","PId":"4fab2017-2a13-4bbf-8147-d6d8bbbcdc09","FullPath":"全部分类/人/公司信息填报","Level":2,"DataStructureInJson":"{\"StructureInJson\":[{\"Identification\":{\"type\":\"SingleLine\",\"index\":0,\"initIdx\":0},\"name\":\"姓名\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":false,\"placeholder\":\"请输入内容\",\"isQueryField\":true,\"partition\":false,\"propertyName\":\"name\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":1,\"initIdx\":2},\"name\":\"地址\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"addr\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":2,\"initIdx\":1},\"name\":\"备注\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"memo\"},{\"Identification\":{\"type\":\"SinglePic\",\"index\":3,\"initIdx\":5},\"name\":\"头像上传\",\"isRequired\":true,\"partition\":false,\"default\":\"\",\"propertyName\":\"cover\",\"maxLength\":1,\"isNew\":false}],\"QueryFields\":[\"name\"],\"FieldsLength\":4,\"initIdx\":12}","ElementTypeIconURL":"/img/DefaultElementTypeIconURL.png","EnumElementProcType":0,"DefaultElementTypeGISIconUrl":"/img/GISIcon/DefaultElementTypeGISIcon.png"},{"Id":"a3c5b6e6-54c8-4bc6-b713-90e6cdaeb3f8","Name":"关怀对象","AnotherName":null,"PinYin":"GHDX","PId":"4fab2017-2a13-4bbf-8147-d6d8bbbcdc09","FullPath":"全部分类/人/关怀对象","Level":2,"DataStructureInJson":"{\"StructureInJson\":[{\"Identification\":{\"type\":\"SingleLine\",\"index\":0,\"initIdx\":0},\"name\":\"姓名\",\"isRequired\":true,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"placeholder\":\"请输入内容\",\"isQueryField\":true,\"partition\":false,\"propertyName\":\"name\"},{\"Identification\":{\"type\":\"SingleLine\",\"index\":1,\"initIdx\":11},\"name\":\"身份证号\",\"isRequired\":false,\"isReveal\":true,\"propertyName\":\"SingleLine11\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":2,\"initIdx\":12},\"name\":\"手机号\",\"propertyName\":\"SingleLine12\",\"isReveal\":true,\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":3,\"initIdx\":2},\"name\":\"户籍地址\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"addr\"},{\"name\":\"人员类型\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":4,\"initIdx\":14},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"常住人口\",\"流动人口\",\"寄住人口\"],\"propertyName\":\"DropdownList14\",\"isNew\":false},{\"name\":\"人口类型\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":5,\"initIdx\":15},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"本地户籍人口\",\"非本地户籍人口\",\"境外来华停居留人员\",\"未落户人员\",\"其他\"],\"propertyName\":\"DropdownList15\",\"isNew\":false},{\"name\":\"性别\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":6,\"initIdx\":16},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"男性\",\"女性\",\"未知性别\"],\"propertyName\":\"DropdownList16\",\"isNew\":false},{\"name\":\"民族\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":7,\"initIdx\":17},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"汉\",\"壮\",\"白\",\"蒙古\",\"回\",\"朝鲜\",\"苗\",\"藏\",\"土\",\"满\"],\"propertyName\":\"DropdownList17\",\"isNew\":false},{\"name\":\"学历\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":8,\"initIdx\":18},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"文盲\",\"学龄前儿童\",\"小学\",\"初中\",\"高中\",\"中专\",\"大专\",\"本科\",\"研究生\",\"硕士\",\"博士\"],\"propertyName\":\"DropdownList18\",\"isNew\":false},{\"name\":\"政治面貌\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":9,\"initIdx\":19},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"群众\",\"团员\",\"党员\",\"其他\"],\"propertyName\":\"DropdownList19\",\"isNew\":false},{\"name\":\"婚姻状况\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":10,\"initIdx\":20},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"已婚\",\"未婚\",\"丧偶\",\"离异\",\"其他\"],\"propertyName\":\"DropdownList20\",\"isNew\":false},{\"name\":\"与房主关系\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":11,\"initIdx\":21},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"本人\",\"配偶\",\"子女\",\"亲属\",\"父子\",\"父女\"],\"propertyName\":\"DropdownList21\",\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":12,\"initIdx\":1},\"name\":\"备注\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"memo\"},{\"Identification\":{\"type\":\"SinglePic\",\"index\":13,\"initIdx\":5},\"name\":\"封面上传\",\"isRequired\":true,\"partition\":false,\"default\":\"\",\"propertyName\":\"cover\",\"maxLength\":1,\"isNew\":false},{\"Identification\":{\"type\":\"MultiPics\",\"index\":14,\"initIdx\":22},\"name\":\"图片上传\",\"maxLength\":10,\"isReveal\":true,\"propertyName\":\"MultiPics22\",\"isNew\":false}],\"QueryFields\":[\"name\"],\"FieldsLength\":15,\"initIdx\":22}","ElementTypeIconURL":"/img/DefaultElementTypeIconURL.png","EnumElementProcType":0,"DefaultElementTypeGISIconUrl":"/img/GISIcon/DefaultElementTypeGISIcon.png"},{"Id":"4a37f9a2-bd64-4c97-a368-495fddd195ff","Name":"人员","AnotherName":null,"PinYin":"RY","PId":"4fab2017-2a13-4bbf-8147-d6d8bbbcdc09","FullPath":"全部分类/人/人员","Level":2,"DataStructureInJson":"{\"StructureInJson\":[{\"Identification\":{\"type\":\"SingleLine\",\"index\":0,\"initIdx\":0},\"name\":\"姓名\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"placeholder\":\"请输入内容\",\"isQueryField\":true,\"partition\":false,\"propertyName\":\"name\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":1,\"initIdx\":2},\"name\":\"身份证号码\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":false,\"partition\":false,\"propertyName\":\"addr\"},{\"name\":\"出生日期\",\"isRequired\":false,\"defaultTime\":\"2020-2-18\",\"isReveal\":false,\"partition\":false,\"IsDisabled\":false,\"Identification\":{\"type\":\"DateTime\",\"index\":2,\"initIdx\":14},\"propertyName\":\"DateTime14\",\"isNew\":false},{\"Identification\":{\"type\":\"Number\",\"index\":3,\"initIdx\":15},\"name\":\"联系方式\",\"isRequired\":true,\"placeholder\":\"\",\"defaultVal\":\"\",\"isReveal\":false,\"partition\":false,\"encryption\":false,\"IsDisabled\":false,\"propertyName\":\"Number15\",\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":4,\"initIdx\":1},\"name\":\"户籍地址\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":false,\"partition\":false,\"propertyName\":\"memo\"},{\"name\":\"人员类型\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":5,\"initIdx\":16},\"partition\":false,\"IsDisabled\":false,\"isReveal\":false,\"listType\":\"0\",\"option\":[\"常驻人口\",\"流动人口\",\"寄住人口\"],\"propertyName\":\"DropdownList16\",\"isNew\":false},{\"name\":\"性别\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":6,\"initIdx\":17},\"partition\":false,\"IsDisabled\":false,\"isReveal\":false,\"listType\":\"0\",\"option\":[\"男\",\"女\",\"未知性别\"],\"propertyName\":\"DropdownList17\",\"isNew\":false},{\"name\":\"民族\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":7,\"initIdx\":18},\"partition\":false,\"IsDisabled\":false,\"isReveal\":false,\"listType\":\"0\",\"option\":[\"汉\",\"壮\",\"朝鲜\",\"满\",\"白\",\"苗\",\"蒙古\",\"藏\"],\"propertyName\":\"DropdownList18\",\"isNew\":false},{\"name\":\"学历\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":8,\"initIdx\":19},\"partition\":false,\"IsDisabled\":false,\"isReveal\":false,\"listType\":\"0\",\"option\":[\"学龄前儿童\",\"小学\",\"初中\",\"高中\",\"中专\",\"大专\",\"本科\",\"研究生\",\"硕士\",\"博士\",\"文盲\"],\"propertyName\":\"DropdownList19\",\"isNew\":false},{\"name\":\"政治面貌\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":9,\"initIdx\":20},\"partition\":false,\"IsDisabled\":false,\"isReveal\":false,\"listType\":\"0\",\"option\":[\"群众\",\"团员\",\"党员\",\"其他\"],\"propertyName\":\"DropdownList20\",\"isNew\":false},{\"name\":\"婚姻状态\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":10,\"initIdx\":21},\"partition\":false,\"IsDisabled\":false,\"isReveal\":false,\"listType\":\"0\",\"option\":[\"已婚\",\"未婚\",\"丧偶\",\"离异\",\"其他\"],\"propertyName\":\"DropdownList21\",\"isNew\":false},{\"name\":\"与户主关系\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":11,\"initIdx\":22},\"partition\":false,\"IsDisabled\":false,\"isReveal\":false,\"listType\":\"0\",\"option\":[\"本人\",\"配偶\",\"子女\",\"亲属\"],\"propertyName\":\"DropdownList22\",\"isNew\":false},{\"Identification\":{\"type\":\"SinglePic\",\"index\":12,\"initIdx\":5},\"name\":\"封面上传\",\"isRequired\":true,\"partition\":false,\"default\":\"\",\"propertyName\":\"cover\",\"maxLength\":1,\"isNew\":false}],\"QueryFields\":[\"name\"],\"FieldsLength\":13,\"initIdx\":23}","ElementTypeIconURL":"/img/DefaultElementTypeIconURL.png","EnumElementProcType":0,"DefaultElementTypeGISIconUrl":"/img/GISIcon/DefaultElementTypeGISIcon.png"},{"Id":"9118f6f5-bc82-4e3d-b784-53fbcd02c0d6","Name":"实有人口","AnotherName":null,"PinYin":"SYRK","PId":"4fab2017-2a13-4bbf-8147-d6d8bbbcdc09","FullPath":"全部分类/人/实有人口","Level":2,"DataStructureInJson":"{\"StructureInJson\":[{\"Identification\":{\"type\":\"SingleLine\",\"index\":0,\"initIdx\":0},\"name\":\"名称\",\"isRequired\":true,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"placeholder\":\"请输入内容\",\"isQueryField\":true,\"partition\":false,\"propertyName\":\"name\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":1,\"initIdx\":1},\"name\":\"备注\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"memo\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":2,\"initIdx\":2},\"name\":\"地址\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"addr\"},{\"Identification\":{\"type\":\"SinglePic\",\"index\":3,\"initIdx\":5},\"name\":\"封面上传\",\"isRequired\":true,\"partition\":false,\"default\":\"\",\"propertyName\":\"cover\",\"maxLength\":1,\"isNew\":false}],\"QueryFields\":[\"name\"],\"FieldsLength\":4,\"initIdx\":7}","ElementTypeIconURL":"/img/DefaultElementTypeIconURL.png","EnumElementProcType":0,"DefaultElementTypeGISIconUrl":"/img/GISIcon/DefaultElementTypeGISIcon.png"},{"Id":"44191d09-c56f-4f67-9d3a-38361782ef51","Name":"特殊人群","AnotherName":null,"PinYin":"TSRQ","PId":"4fab2017-2a13-4bbf-8147-d6d8bbbcdc09","FullPath":"全部分类/人/特殊人群","Level":2,"DataStructureInJson":"{\"StructureInJson\":[{\"Identification\":{\"type\":\"SingleLine\",\"index\":0,\"initIdx\":0},\"name\":\"姓名\",\"isRequired\":true,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"placeholder\":\"请输入内容\",\"isQueryField\":true,\"partition\":false,\"propertyName\":\"name\"},{\"Identification\":{\"type\":\"SingleLine\",\"index\":1,\"initIdx\":9},\"name\":\"身份证号\",\"propertyName\":\"SingleLine9\",\"isReveal\":true,\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":2,\"initIdx\":10},\"name\":\"手机号\",\"propertyName\":\"SingleLine10\",\"isReveal\":true,\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":3,\"initIdx\":2},\"name\":\"户籍地址\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"addr\"},{\"name\":\"人员类型\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":4,\"initIdx\":11},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"常住人口\",\"流动人口\",\"寄住人口\"],\"propertyName\":\"DropdownList11\",\"isNew\":false},{\"name\":\"人口类型\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":5,\"initIdx\":12},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"本地户籍人口\",\"非本地户籍人口\",\"未落户人员\",\"境外来华停居留人员\",\"其他\"],\"propertyName\":\"DropdownList12\",\"isNew\":false},{\"name\":\"性别\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":6,\"initIdx\":13},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"男性\",\"女性\",\"未知性别\"],\"propertyName\":\"DropdownList13\",\"isNew\":false},{\"name\":\"民族\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":7,\"initIdx\":14},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"汉\",\"壮\",\"藏\",\"回\",\"土\",\"朝鲜\",\"苗\",\"满\",\"白\",\"蒙古\"],\"propertyName\":\"DropdownList14\",\"isNew\":false},{\"name\":\"学历\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":8,\"initIdx\":15},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"学龄前儿童\",\"小学\",\"初中\",\"高中\",\"中专\",\"大专\",\"本科\",\"研究生\",\"硕士\",\"博士\",\"文盲\"],\"propertyName\":\"DropdownList15\",\"isNew\":false},{\"name\":\"政治面貌\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":9,\"initIdx\":16},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"群众\",\"团员\",\"党员\",\"其他\"],\"propertyName\":\"DropdownList16\",\"isNew\":false},{\"name\":\"婚姻状况\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":10,\"initIdx\":17},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"已婚\",\"未婚\",\"丧偶\",\"离异\",\"其他\"],\"propertyName\":\"DropdownList17\",\"isNew\":false},{\"name\":\"与房主关系\",\"isRequired\":true,\"Identification\":{\"type\":\"DropdownList\",\"index\":11,\"initIdx\":18},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"本人\",\"配偶\",\"子女\",\"亲属\",\"父子\",\"父女\"],\"propertyName\":\"DropdownList18\",\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":12,\"initIdx\":1},\"name\":\"备注\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"memo\"},{\"Identification\":{\"type\":\"SinglePic\",\"index\":13,\"initIdx\":5},\"name\":\"封面上传\",\"isRequired\":true,\"partition\":false,\"default\":\"\",\"propertyName\":\"cover\",\"maxLength\":1,\"isNew\":false}],\"QueryFields\":[\"name\"],\"FieldsLength\":14,\"initIdx\":19}","ElementTypeIconURL":"/img/DefaultElementTypeIconURL.png","EnumElementProcType":0,"DefaultElementTypeGISIconUrl":"/img/GISIcon/DefaultElementTypeGISIcon.png"},{"Id":"7a23ba63-c564-4bff-a2ff-d058a188b362","Name":"疫情人口","AnotherName":null,"PinYin":"YQRK","PId":"4fab2017-2a13-4bbf-8147-d6d8bbbcdc09","FullPath":"全部分类/人/疫情人口","Level":2,"DataStructureInJson":"{\"StructureInJson\":[{\"Identification\":{\"type\":\"SingleLine\",\"index\":0,\"initIdx\":0},\"name\":\"姓名\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":false,\"placeholder\":\"请输入内容\",\"isQueryField\":true,\"partition\":false,\"propertyName\":\"name\"},{\"name\":\"性别\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":1,\"initIdx\":13},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"男\",\"女\",\"未知性别\"],\"propertyName\":\"DropdownList13\",\"isNew\":false},{\"Identification\":{\"type\":\"Number\",\"index\":2,\"initIdx\":14},\"name\":\"年龄\",\"isRequired\":true,\"placeholder\":\"\",\"defaultVal\":\"\",\"isReveal\":true,\"partition\":false,\"encryption\":false,\"IsDisabled\":false,\"propertyName\":\"Number14\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":3,\"initIdx\":15},\"name\":\"身份证号码\",\"isRequired\":true,\"placeholder\":\"\",\"isQueryField\":true,\"isReveal\":true,\"partition\":false,\"encryption\":false,\"IsDisabled\":false,\"propertyName\":\"SingleLine15\",\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":4,\"initIdx\":2},\"name\":\"现住地址\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"addr\"},{\"Identification\":{\"type\":\"MultiLine\",\"index\":5,\"initIdx\":18},\"name\":\"户籍地\",\"isRequired\":false,\"isReveal\":true,\"partition\":false,\"encryption\":false,\"IsDisabled\":false,\"propertyName\":\"MultiLine18\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":6,\"initIdx\":19},\"name\":\"联系电话\",\"isRequired\":false,\"placeholder\":\"\",\"isQueryField\":false,\"isReveal\":true,\"partition\":false,\"encryption\":false,\"IsDisabled\":false,\"propertyName\":\"SingleLine19\",\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":7,\"initIdx\":20},\"name\":\"外出地点1\",\"propertyName\":\"MultiLine20\",\"isReveal\":true,\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":8,\"initIdx\":21},\"name\":\"外出地点2\",\"isReveal\":true,\"propertyName\":\"MultiLine21\",\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":9,\"initIdx\":22},\"name\":\"外出地点3\",\"isReveal\":true,\"propertyName\":\"MultiLine22\",\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":10,\"initIdx\":23},\"name\":\"交通工具（车次、路线）\",\"isRequired\":false,\"isReveal\":true,\"partition\":false,\"encryption\":false,\"IsDisabled\":false,\"propertyName\":\"MultiLine23\",\"isNew\":false},{\"name\":\"返徐日期\",\"isRequired\":false,\"defaultTime\":\"2020-2-3\",\"isReveal\":true,\"partition\":false,\"IsDisabled\":false,\"Identification\":{\"type\":\"DateTime\",\"index\":11,\"initIdx\":24},\"propertyName\":\"DateTime24\",\"isNew\":false},{\"Identification\":{\"type\":\"Number\",\"index\":12,\"initIdx\":25},\"name\":\"体温\",\"isRequired\":true,\"placeholder\":\"\",\"defaultVal\":\"\",\"isReveal\":true,\"partition\":false,\"encryption\":false,\"IsDisabled\":false,\"propertyName\":\"Number25\",\"isNew\":false},{\"name\":\"有无不适（发热、干咳）\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":13,\"initIdx\":26},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"无\",\"有\"],\"propertyName\":\"DropdownList26\",\"isNew\":false},{\"name\":\"人员类型\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":14,\"initIdx\":27},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"常住人口\",\"流动人口\",\"寄住人口\"],\"propertyName\":\"DropdownList27\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":15,\"initIdx\":28},\"name\":\"接触感染者姓名\",\"isRequired\":false,\"placeholder\":\"\",\"isQueryField\":false,\"isReveal\":true,\"partition\":false,\"encryption\":false,\"IsDisabled\":false,\"propertyName\":\"SingleLine28\",\"isNew\":false},{\"name\":\"解除观察\",\"isRequired\":false,\"Identification\":{\"type\":\"DropdownList\",\"index\":16,\"initIdx\":29},\"partition\":false,\"IsDisabled\":false,\"isReveal\":true,\"listType\":\"0\",\"option\":[\"是\",\"否\"],\"propertyName\":\"DropdownList29\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":17,\"initIdx\":30},\"name\":\"网格员姓名\",\"isReveal\":true,\"propertyName\":\"SingleLine30\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":18,\"initIdx\":31},\"name\":\"网格员电话\",\"isReveal\":true,\"propertyName\":\"SingleLine31\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":19,\"initIdx\":32},\"name\":\"办事处责任人姓名\",\"isReveal\":true,\"propertyName\":\"SingleLine32\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":20,\"initIdx\":33},\"name\":\"办事处责任人电话\",\"isReveal\":true,\"propertyName\":\"SingleLine33\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":21,\"initIdx\":34},\"name\":\"社区责任人姓名\",\"isReveal\":true,\"propertyName\":\"SingleLine34\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":22,\"initIdx\":35},\"name\":\"社区责任人电话\",\"propertyName\":\"SingleLine35\",\"isReveal\":true,\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":23,\"initIdx\":36},\"name\":\"责任民警姓名\",\"isReveal\":true,\"propertyName\":\"SingleLine36\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":24,\"initIdx\":37},\"name\":\"责任民警电话\",\"isReveal\":true,\"propertyName\":\"SingleLine37\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":25,\"initIdx\":38},\"name\":\"医生姓名\",\"isReveal\":true,\"propertyName\":\"SingleLine38\",\"isNew\":false},{\"Identification\":{\"type\":\"SingleLine\",\"index\":26,\"initIdx\":39},\"name\":\"医生电话\",\"isReveal\":true,\"propertyName\":\"SingleLine39\",\"isNew\":false},{\"Identification\":{\"type\":\"MultiLine\",\"index\":27,\"initIdx\":1},\"name\":\"备注\",\"isRequired\":false,\"encryption\":false,\"isNew\":false,\"isReveal\":true,\"partition\":false,\"propertyName\":\"memo\"},{\"Identification\":{\"type\":\"MultiPics\",\"index\":28,\"initIdx\":16},\"name\":\"照片\",\"isReveal\":true,\"maxLength\":5,\"propertyName\":\"MultiPics16\",\"isNew\":false},{\"Identification\":{\"type\":\"SinglePic\",\"index\":29,\"initIdx\":5},\"name\":\"封面上传\",\"isRequired\":false,\"partition\":false,\"default\":\"\",\"propertyName\":\"cover\",\"maxLength\":1,\"isNew\":false}],\"QueryFields\":[\"name\",\"SingleLine15\"],\"FieldsLength\":30,\"initIdx\":40}","ElementTypeIconURL":"/img/DefaultElementTypeIconURL.png","EnumElementProcType":0,"DefaultElementTypeGISIconUrl":"/img/GISIcon/DefaultElementTypeGISIcon.png"}]
     * elementList : []
     */

    private int pageCount;
    private int count;
    /**
     * 0  无
     * 1  走访
     * 2  巡检
     */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private List<ElementTypeListBean> elementTypeList;
    private List<ElementListBean> elementList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<ElementTypeListBean> getElementTypeList() {
        return elementTypeList;
    }

    public void setElementTypeList(List<ElementTypeListBean> elementTypeList) {
        this.elementTypeList = elementTypeList;
    }

    public List<ElementListBean> getElementList() {
        return elementList;
    }

    public void setElementList(List<ElementListBean> elementList) {
        this.elementList = elementList;
    }

    public static class ElementTypeListBean extends AbstractExpandableItem<ElementTypeListBean> implements Parcelable {
        /**
         * Name : 人
         * PinYin : R
         * PId : 290abbf0-1517-4f1c-9e1f-7a38d82f0866
         * FullPath : 全部分类/人
         * Level : 1
         * DataStructureInJson : {"StructureInJson":[{"Identification":{"type":"SingleLine","index":0},"name":"名称","isRequired":true,"isNew":false,"isReveal":true,"placeholder":"请输入内容","isQueryField":true,"partition":false,"propertyName":"name"},{"Identification":{"type":"MultiLine","index":1},"name":"备注","isRequired":false,"isNew":false,"isReveal":true,"partition":false,"propertyName":"memo"},{"Identification":{"type":"MultiLine","index":2},"name":"地址","isRequired":false,"isNew":false,"isReveal":true,"partition":false,"propertyName":"address"},{"Identification":{"type":"Option","index":3},"name":"性别","isRequired":true,"partition":false,"option":["男","女"],"propertyName":"Option3","isNew":false}],"QueryFields":["name"],"FieldsLength":4}
         * IsEnabled : true
         * EnumElementTypeSaveStatus : 1
         * ElementTypeIconURL : /img/DefaultElementTypeIconURL.svg
         * EnumElementProcType : 0
         * DBTableNameAlias : ctable_R
         * DBTableName : ctable_R_0
         * MenuId : 63ee5940-0fb9-400c-a98f-0c372dbd4995
         * InsertSql : null
         * DefaultElementTypeGISIconUrl : /Uploads/CustomerData/image/ren.png
         * Id : 89c6a8d0-1118-4a4e-a0d4-1ee4f59e5c22
         * CreateUserId : 88a24b03-7a8b-440d-89e8-7595af7b3106
         * UpdateUserId : 88a24b03-7a8b-440d-89e8-7595af7b3106
         * CreateTime : /Date(1574750960350)/
         * UpdateTime : /Date(1574750960350)/
         * Memo : null
         * IsDeleted : false
         * DeletedTime : null
         */

        private String Name;
        private String PinYin;
        private String AnotherName;
        private String PId;
        private String FullPath;
        private int Level;
        private String DataStructureInJson;
        private boolean IsEnabled;
        private int EnumElementTypeSaveStatus;
        private String ElementTypeIconURL;
        private int EnumElementProcType;
        private String DBTableNameAlias;
        private String DBTableName;
        private String MenuId;
        private String InsertSql;
        private String DefaultElementTypeGISIconUrl;
        private String Id;
        private String CreateUserId;
        private String UpdateUserId;
        private String CreateTime;
        private String UpdateTime;
        private String Memo;
        private boolean IsDeleted;
        private boolean IsLeaf;
        private String DeletedTime;
        private boolean HasResourece;
        private String Count;

        public String getCount() {
            return Count;
        }

        public void setCount(String count) {
            this.Count = count;
        }

        private List<ElementTypeListBean> lists;


        public boolean isExpand() {
            return super.isExpanded();
        }

        public void setExpand(boolean expand) {
            super.setExpanded(expand);
        }

        public String getAnotherName() {
            return AnotherName;
        }

        public void setAnotherName(String anotherName) {
            AnotherName = anotherName;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPinYin() {
            return PinYin;
        }

        public void setPinYin(String PinYin) {
            this.PinYin = PinYin;
        }

        public String getPId() {
            return PId;
        }

        public void setPId(String PId) {
            this.PId = PId;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        @Override
        public int getLevel() {
            return Level;
        }

        public void setLevel(int Level) {
            this.Level = Level;
        }

        public String getDataStructureInJson() {
            return DataStructureInJson;
        }

        public void setDataStructureInJson(String DataStructureInJson) {
            this.DataStructureInJson = DataStructureInJson;
        }

        public boolean isIsEnabled() {
            return IsEnabled;
        }

        public void setIsEnabled(boolean IsEnabled) {
            this.IsEnabled = IsEnabled;
        }

        public int getEnumElementTypeSaveStatus() {
            return EnumElementTypeSaveStatus;
        }

        public void setEnumElementTypeSaveStatus(int EnumElementTypeSaveStatus) {
            this.EnumElementTypeSaveStatus = EnumElementTypeSaveStatus;
        }

        public String getElementTypeIconURL() {
            return ElementTypeIconURL;
        }

        public void setElementTypeIconURL(String ElementTypeIconURL) {
            this.ElementTypeIconURL = ElementTypeIconURL;
        }

        public int getEnumElementProcType() {
            return EnumElementProcType;
        }

        public void setEnumElementProcType(int EnumElementProcType) {
            this.EnumElementProcType = EnumElementProcType;
        }

        public String getDBTableNameAlias() {
            return DBTableNameAlias;
        }

        public void setDBTableNameAlias(String DBTableNameAlias) {
            this.DBTableNameAlias = DBTableNameAlias;
        }

        public String getDBTableName() {
            return DBTableName;
        }

        public void setDBTableName(String DBTableName) {
            this.DBTableName = DBTableName;
        }

        public String getMenuId() {
            return MenuId;
        }

        public void setMenuId(String MenuId) {
            this.MenuId = MenuId;
        }

        public String getInsertSql() {
            return InsertSql;
        }

        public void setInsertSql(String InsertSql) {
            this.InsertSql = InsertSql;
        }

        public String getDefaultElementTypeGISIconUrl() {
            return DefaultElementTypeGISIconUrl;
        }

        public void setDefaultElementTypeGISIconUrl(String DefaultElementTypeGISIconUrl) {
            this.DefaultElementTypeGISIconUrl = DefaultElementTypeGISIconUrl;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getCreateUserId() {
            return CreateUserId;
        }

        public void setCreateUserId(String CreateUserId) {
            this.CreateUserId = CreateUserId;
        }

        public String getUpdateUserId() {
            return UpdateUserId;
        }

        public void setUpdateUserId(String UpdateUserId) {
            this.UpdateUserId = UpdateUserId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public String getMemo() {
            return Memo;
        }

        public void setMemo(String Memo) {
            this.Memo = Memo;
        }

        public boolean isIsDeleted() {
            return IsDeleted;
        }

        public void setIsDeleted(boolean IsDeleted) {
            this.IsDeleted = IsDeleted;
        }

        public String getDeletedTime() {
            return DeletedTime;
        }

        public void setDeletedTime(String DeletedTime) {
            this.DeletedTime = DeletedTime;
        }

        public boolean isLeaf() {
            return IsLeaf;
        }

        public void setLeaf(boolean leaf) {
            IsLeaf = leaf;
        }

        public List<ElementTypeListBean> getList() {
            return super.getSubItems();
        }

        public void setList(List<ElementTypeListBean> list) {
            super.setSubItems(list);
        }

        public boolean isHasResourece() {
            return HasResourece;
        }

        public void setHasResourece(boolean hasResourece) {
            HasResourece = hasResourece;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.Count);
            dest.writeString(this.Name);
            dest.writeString(this.PinYin);
            dest.writeString(this.AnotherName);
            dest.writeString(this.PId);
            dest.writeString(this.FullPath);
            dest.writeInt(this.Level);
            dest.writeString(this.DataStructureInJson);
            dest.writeByte(this.IsEnabled ? (byte) 1 : (byte) 0);
            dest.writeInt(this.EnumElementTypeSaveStatus);
            dest.writeString(this.ElementTypeIconURL);
            dest.writeInt(this.EnumElementProcType);
            dest.writeString(this.DBTableNameAlias);
            dest.writeString(this.DBTableName);
            dest.writeString(this.MenuId);
            dest.writeString(this.InsertSql);
            dest.writeString(this.DefaultElementTypeGISIconUrl);
            dest.writeString(this.Id);
            dest.writeString(this.CreateUserId);
            dest.writeString(this.UpdateUserId);
            dest.writeString(this.CreateTime);
            dest.writeString(this.UpdateTime);
            dest.writeString(this.Memo);
            dest.writeByte(this.IsDeleted ? (byte) 1 : (byte) 0);
            dest.writeByte(this.IsLeaf ? (byte) 1 : (byte) 0);
            dest.writeString(this.DeletedTime);
            dest.writeByte(this.HasResourece ? (byte) 1 : (byte) 0);
            dest.writeList(this.lists);
        }

        public ElementTypeListBean() {
        }

        protected ElementTypeListBean(Parcel in) {
            this.Count = in.readString();
            this.Name = in.readString();
            this.PinYin = in.readString();
            this.AnotherName = in.readString();
            this.PId = in.readString();
            this.FullPath = in.readString();
            this.Level = in.readInt();
            this.DataStructureInJson = in.readString();
            this.IsEnabled = in.readByte() != 0;
            this.EnumElementTypeSaveStatus = in.readInt();
            this.ElementTypeIconURL = in.readString();
            this.EnumElementProcType = in.readInt();
            this.DBTableNameAlias = in.readString();
            this.DBTableName = in.readString();
            this.MenuId = in.readString();
            this.InsertSql = in.readString();
            this.DefaultElementTypeGISIconUrl = in.readString();
            this.Id = in.readString();
            this.CreateUserId = in.readString();
            this.UpdateUserId = in.readString();
            this.CreateTime = in.readString();
            this.UpdateTime = in.readString();
            this.Memo = in.readString();
            this.IsDeleted = in.readByte() != 0;
            this.IsLeaf = in.readByte() != 0;
            this.DeletedTime = in.readString();
            this.HasResourece = in.readByte() != 0;
            this.lists = new ArrayList<ElementTypeListBean>();
            in.readList(this.lists, ElementTypeListBean.class.getClassLoader());
        }

        public static final Parcelable.Creator<ElementTypeListBean> CREATOR = new Parcelable.Creator<ElementTypeListBean>() {
            @Override
            public ElementTypeListBean createFromParcel(Parcel source) {
                return new ElementTypeListBean(source);
            }

            @Override
            public ElementTypeListBean[] newArray(int size) {
                return new ElementTypeListBean[size];
            }
        };
    }

    public static class ElementListBean implements Parcelable, NameAndIdInterface {
        /**
         * Id : 4c18ecd7-7432-4e0e-8302-3b8ec560c87e
         * DepartmentId : 27a8465b-4c18-47e0-b677-d12e6b414a44
         * STANDARDADDRESSID : dfb1a8f6-9c55-4e81-92d9-e8547d3f4718
         * ElementTypeId : 4fc63cbc-885a-4286-8f6d-257f81ef595b
         * name : 小红
         * memo : 我不是人
         * address : 非洲
         * Option3 : 男
         * PointsInJson : {"lng":117.188441,"lat":35.30215}
         * ElementTypeName : 全部分类/人/特殊人群
         * FullPath : 鼓楼生态园/18号楼/3单元/801
         * RowNum : 1
         * total : 10
         */

        private String Id;
        private String DepartmentId;
        private String StandardAddressId;
        private String ElementTypeId;
        private String name;
        private String SingleLine0;
        private String memo;
        private String address;
        private String Option3;
        private String PointsInJson;
        private String ElementTypeName;
        private String FullPath;
        private int RowNum;
        private int total;


       private String RecordTime;

        public String getRecordTime() {
            return RecordTime;
        }

        public void setRecordTime(String recordTime) {
            RecordTime = recordTime;
        }

        protected ElementListBean(Parcel in) {
            Id = in.readString();
            DepartmentId = in.readString();
            StandardAddressId = in.readString();
            ElementTypeId = in.readString();
            name = in.readString();
            SingleLine0 = in.readString();
            memo = in.readString();
            address = in.readString();
            Option3 = in.readString();
            PointsInJson = in.readString();
            ElementTypeName = in.readString();
            FullPath = in.readString();
            RowNum = in.readInt();
            total = in.readInt();
            RecordTime =in.readString();

        }

        public static final Creator<ElementListBean> CREATOR = new Creator<ElementListBean>() {
            @Override
            public ElementListBean createFromParcel(Parcel in) {
                return new ElementListBean(in);
            }

            @Override
            public ElementListBean[] newArray(int size) {
                return new ElementListBean[size];
            }
        };


        @Override
        public String getId() {
            return Id;
        }

        @Override
        public String getId2() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(String DepartmentId) {
            this.DepartmentId = DepartmentId;
        }
        public String getStandardAddressId() {
            return StandardAddressId;
        }

        public void setStandardAddressId(String StandardAddressId) {
            this.StandardAddressId = StandardAddressId;
        }

        public String getElementTypeId() {
            return ElementTypeId;
        }

        public void setElementTypeId(String ElementTypeId) {
            this.ElementTypeId = ElementTypeId;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getOption3() {
            return Option3;
        }

        public void setOption3(String Option3) {
            this.Option3 = Option3;
        }

        public String getPointsInJson() {
            return PointsInJson;
        }

        public void setPointsInJson(String PointsInJson) {
            this.PointsInJson = PointsInJson;
        }

        public String getElementTypeName() {
            return ElementTypeName;
        }

        public void setElementTypeName(String ElementTypeName) {
            this.ElementTypeName = ElementTypeName;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public int getRowNum() {
            return RowNum;
        }

        public void setRowNum(int RowNum) {
            this.RowNum = RowNum;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getSingleLine0() {
            return SingleLine0;
        }

        public void setSingleLine0(String singleLine0) {
            SingleLine0 = singleLine0;
        }

        /**
         * Describe the kinds of special objects contained in this Parcelable
         * instance's marshaled representation. For example, if the object will
         * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
         * the return value of this method must include the
         * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
         *
         * @return a bitmask indicating the set of special object types marshaled
         * by this Parcelable object instance.
         */
        @Override
        public int describeContents() {
            return 0;
        }

        /**
         * Flatten this object in to a Parcel.
         *
         * @param dest  The Parcel in which the object should be written.
         * @param flags Additional flags about how the object should be written.
         *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
         */
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Id);
            dest.writeString(DepartmentId);
            dest.writeString(StandardAddressId);
            dest.writeString(ElementTypeId);
            dest.writeString(name);
            dest.writeString(SingleLine0);
            dest.writeString(memo);
            dest.writeString(address);
            dest.writeString(Option3);
            dest.writeString(PointsInJson);
            dest.writeString(ElementTypeName);
            dest.writeString(FullPath);
            dest.writeInt(RowNum);
            dest.writeInt(total);
            dest.writeString(RecordTime);
        }
    }
}
