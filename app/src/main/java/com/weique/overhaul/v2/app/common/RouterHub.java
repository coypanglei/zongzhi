package com.weique.overhaul.v2.app.common;

/**
 * 这里用于统一管理跳转注解路径(这里要注意一定要至少两级并且前面一定要有斜杠,不要忘记，不然不会出错)
 * 1.前面缺少斜杠会出现错误,错误如下
 * Process:  PID: 12831
 * com.alibaba.android.arouter.exception.HandlerException: ARouter::Extract the default group failed, the path must be start with '/' and contain more than 2 '/'!
 * <p>
 * <p>
 * 2.如果注解路径只有一级时，build会在编译的时候直接报错,错误如下
 * 错误: ARouter::Compiler An exception is encountered, [Failed to extract default group! String index out of range: -2]
 * 警告: ARouter::Compiler >>> Route meta verify error, group is  <<<
 * 注: ARouter::Compiler >>> Generated provider map, name is ARouter$$Providers$$app <<<
 * 注: ARouter::Compiler >>> Generated root, name is ARouter$$Root$$app <<<
 * 1 个错误
 * 1 个警告
 *
 * @author Administrator
 */
public interface RouterHub {
    /**
     * App
     */
    String APP = "/app";
    String ZXING = "/lib_zxing";
    /**
     * App
     * 三方登录闪屏页
     */
    String APP_THIRDPARTYSPLASHPRESENTER = APP + "/ThirdPartySplashPresenter";
    /**
     * App
     * 重置 密码
     */
    String APP_RESTPASSWORDACTIVITY = APP + "/RestPasswordActivity";
    /**
     * App
     * 通用web view
     */
    String APP_COMMONWEBVIEWACTIVITY = APP + "/CommonWebViewActivity";


    /**
     * App
     * 闪屏页
     */
    String APP_SPLASHACTIVITY = APP + "/SplashActivity";
    /**
     * 引导页
     */
    String APP_GUIDEACTIVITY = APP + "/GuideActivity";
    /**
     * 主页
     */
    String APP_MAINACTIVITY = APP + "/MainActivity";
    /**
     * 主页 = homef
     */
    String APP_MAINACTIVITY_HOMEFRAGMENT = APP + "/MainActivity/HomeFragment";
    /**
     * 主页 = homef - PatrolsFragment
     */
    String APP_MAINACTIVITY_HOMEFRAGMENT_PATROLSFRAGMENT = APP + "/MainActivity/HomeFragment/PatrolsFragment";
    /**
     * 主页 = home - TaskListHomeFragment
     */
    String APP_MAINACTIVITY_HOMEFRAGMENT_TASKLISTHOMEFRAGMENT = APP + "/MainActivity/HomeFragment/TaskListHomeFragment";
    /**
     * 主页 = MY
     */
    String APP_MAINACTIVITY_MYFRAGMENT = APP + "/MainActivity/MyFragment";
    /**
     * 登录页
     */
    String APP_LOGINACTIVITY = APP + "/LoginActivity";
    /**
     * 注册页
     */
    String APP_REGISTERACTIVITY = APP + "/RegisterActivity";
    /**
     * 地图界面
     */
    String APP_MAPACTIVITY = APP + "/MapActivity";
    /**
     * 我的--个人中心
     */
    String APP_MY_INFO_ACTIVITY = APP + "/MyInfoActivity";

    /**
     * 我的--工作记录
     */
    String APP_MYWORKRECORDACTIVITY = APP + "/MyWorkRecordActivity";

    /**
     * 我的--参与事件
     */
    String APP_PARTICIPATEEVENTACTIVITY = APP + "/ParticipateEventActivity";
    /**
     * 我的--参与事件详情
     */
    String APP_MYPARTICIPATEEVENTDETAILACTIVITY = APP + "/MyParticipateEventDetailActivity";

    /**
     * 我的--上传列表
     */
    String APP_UPLOADLISTACTIVITY = APP + "/UpLoadListActivity";
    /**
     * 我的--设置
     */
    String APP_SETTINGSACTIVITY = APP + "/SettingsActivity";
    /**
     * 综合办理界面
     */
    String APP_INTEGRATEDWITHACTIVITY = APP + "/IntegratedWithActivity";
    /**
     * 事件上报
     */
    String APP_UPEVENTACTIVITY = APP + "/UpEventActivity";
    /**
     * 选择信息类型
     */
    String APP_INFORMATIONCOLLECTIONACTIVITY = APP + "/InformationCollectionActivity";
    /**
     * 党建中心
     */
    String APP_PARTYCENTERACTIVITY = APP + "/PartyCenterActivity";
    /**
     * 党建中心 - 我的 - 吐槽
     */
    String APP_PARTYCENTERDISSACTIVITY = APP + "/PartyCenterDissActivity";
    /**
     * 党建中心 - 我的 - 答题列表
     */
    String APP_ANSWERACTIVITY = APP + "/AnswerActivity";

    /**
     * 党建 - 新闻详情页
     */
    String APP_PARTYCONTENTARTICLEDETAILACTIVITY = APP + "/PartyContentArticleDetailActivity";
    /**
     * 党建中心 - 我的 - 答题详情
     */
    String APP_ANSWERDETAILACTIVITY = APP + "/AnswerDetailActivity";
    /**
     * 党建中心 - 我的 - 答题结果
     */
    String APP_ANSWERRESULTACTIVITY = APP + "/AnswerResultActivity";
    /**
     * 党建中心 - 我的 - 收藏列表
     */
    String APP_PARTYCENTERCOLLECTSACTIVITY = APP + "/PartyCenterCollectsActivity";
    /**
     * 党建中心 - 我的 - 答题--学习
     */
    String APP_PARTYCENTERSTUDYACTIVITY = APP + "/PartyCenterStudyActivity";
    /**
     * 党建中心 - 搜索功能
     */
    String APP_SEARCHACTIVITY = APP + "/SearchActivity";
    /**
     * 党建中心 - 积分  详情
     */
    String APP_PARTYCENTERINTEGRALACTIVITY = APP + "/PartyCenterIntegralActivity";
    /**
     * 信息采集--标准地址
     */
//    String APP_STANDARDADDRESSACTIVITY = APP + "/StandardAddressActivity";
    /**
     * 标准地址 - 网格及以上 界面
     */
    String APP_INFORMATIONMAINTABONEACTIVITY = APP + "/InformationMainTabOneActivity";
    /**
     * 标准地址 - 网格以下地址 界面
     */
//    String APP_STANDARDADDRESSONEACTIVITY = APP + "/StandardAddressOneActivity";
    /**
     * 标准地址 - 地址详情 添加 个修改
     */
    String APP_STANDARDADDRESSADDALERTACTIVITY = APP + "/StandardAddressAddAlertActivity";
    /**
     * 标准地址 - 地址详情 查询
     */
    String APP_STANDARDADDRESSLOOKACTIVITY = APP + "/StandardAddressLookActivity";
    /**
     * 标准地址 - 地址详情 — 绑定的相关信息采集fragment
     */
    String APP_INFORMATIONLISTFORADDRESSDETAILFRAGMENT = APP + "/InformationListForAddressDetailFragment";
    /**
     * 标准地址 - 地址详情 - 选择上级地址
     */
    String APP_STANDARDADDRESSUPLISTACTIVITY = APP + "/StandardAddressUpListActivity";
    /**
     * 信息采集  人 地 事  物
     */
    String APP_INFORMATIONTYPEFIRSTACTIVITY = APP + "/InformationTypeFirstActivity";
    /**
     * 信息采集  人 地 事  物 子集
     */
    String APP_INFORMATIONTYPESECONDACTIVITY = APP + "/InformationTypeSecondActivity";
    /**
     * 信息采集  人 地 事  物 子集 查询详情
     */
    String APP_INFORMATIONDYNAMICFORMSELECTACTIVITY = APP + "/InformationDynamicFormSelectActivity";
    /**
     * 信息采集  人 地 事  物 子集 查询详情-添加或修改
     */
    String APP_INFORMATIONDYNAMICFORMAAACTIVITY = APP + "/InformationDynamicFormCrudActivity";
    /**
     * 寻访地图
     */
    String APP_TOURVISITACTIVITY = APP + "/TourVisitActivity";
    /**
     * 走访首页列表
     */
    String APP_RESOURCESEARCHACTIVITY = APP + "/ResourceSearchActivity";
    /**
     * 巡检首页列表
     */
    String APP_INSPECTIONLISTACTIVITY = APP + "/InspectionListActivity";
    /**
     * 巡检首页列表
     */
    String APP_INSPECTIONDETAILLISTACTIVITY = APP + "/InspectionDetailListActivity";
    /**
     * 巡检地图选择
     */
    String APP_INSPECTIONMAPACTIVITY = APP + "/InspectionMapActivity";
    /**
     * 巡检某地点增加巡检
     */
    String APP_INSPECTIONADDACTIVITY = APP + "/InspectionAddActivity";
    /**
     * 寻访列表子列表
     */
    String APP_RESOURCESEARCHDETAILACTIVITY = APP + "/ResourceSearchDetailActivity";
    /**
     * 查看图片大图
     */
    String APP_PICTURELOOKACTIVITY = APP + "/PictureLookActivity";
    /**
     * 事件上报 已上报列表
     */
    String APP_EVENTSREPORTEDACTIVITY = APP + "/EventsReportedActivity";
    /**
     * 事件上报  CRUD
     */
    String APP_EVENTSREPORTEDCRUDACTIVITY = APP + "/EventsReportedCrudActivity";
    /**
     * 事件上报  CRUD - 选择事件分类
     */
    String APP_EVENTSREPORTEDSORTACTIVITY = APP + "/EventsReportedSortActivity";
    /**
     * 事件上报  查看 已上报详情
     */
    String APP_EVENTSREPORTEDLOOKACTIVITY = APP + "/EventsReportedLookActivity";
    /**
     * 区，街道，社区，网格列表
     */
    String APP_ADDRESSBOOKACTIVITY = APP + "/AddressBookActivity";
    /**
     * 通讯录列表
     */
    String APP_ADDRESSLOOKLISTACTIVITY = APP + "/AddressLookListActivity";
    /**
     * 通讯录列表搜索
     */


    String APP_ADDRESSLOOKLISTSEARCHACTIVITY = APP + "/AddressLookListSearchActivity";
    /**
     * 签到
     */
    String APP_SIGNINACTIVITY = APP + "/SignInActivity";
    /**
     * 个人签到记录
     */
    String APP_MYSIGNRECORDACTIVITY = APP + "/MySignRecordActivity";
    /**
     * 下級签到记录
     */
    String APP_SIGNSEARCHACTIVITY = APP + "/SignSearchActivity";
    /**
     * 知识库首页
     */
    String APP_KNOWLEDGEBASEACTIVITY = APP + "/KnowledgeBaseActivity";
    /**
     * 矛盾调解页
     */
    String APP_CONTRADICTIONACTIVITY = APP + "/ContradictionActivity";
    /**
     * 矛盾调解新增
     */
    String APP_CONTRADICTIONADDACTIVITY = APP + "/ContradictionAddActivity";
    /**
     * 矛盾调解标准地址选择页面
     */
    String APP_CONTRADICTIONADDSEARCHACTIVITY = APP + "/ContradictionAddSearchActivity";
    /**
     * 工作轨迹
     */
    String APP_MYWORKLINEACTIVITY = APP + "/MyWorkLineActivity";
    /**
     * 消息列表
     */
    String APP_MESSAGELISTACTIVITY = APP + "/MessageListActivity";
    /**
     * 消息详情
     */
    String APP_MESSAGEDETAILACTIVITY = APP + "/MessageDetailActivity";
    /**
     * 待办巡检，走访任务详情
     */
    String APP_PATROLSDETAILACTIVITY = APP + "/PatrolsDetailActivity";
    /**
     * 任务列表 注意这里  放到了首页但是 ARouter 没有修改
     */
    String APP_TASKLISTACTIVITY = APP + "/TaskListActivity";
    /**
     * 公司复工 申请 界面
     */
    String APP_ENTERPRISEREWORKACTIVITY = APP + "/EnterpriseReworkActivity";

    /**
     * 公司复工 人员列表
     */
    String APP_STAFFLISTACTIVITY = APP + "/StaffListActivity";
    /**
     * 复工企业疫情防控工作信息表
     */
    String APP_DAYEPIDEMICACTIVITY = APP + "/DayEpidemicActivity";
    /**
     * 温度记录列表
     */
    String APP_STAFFTEMPERATUREACTIVITY = APP + "/StaffTemperatureActivity";
    /**
     * 视频会商人员列表
     */
    String APP_CHATSELECTACTIVITY = APP + "/ChatSelectActivity";
    /**
     * 掃描二維碼跳轉
     */
    String APP_SCANRESULTACTIVITY = APP + "/ScanResultActivity";
    /**
     * 扫描二维码相机 这里比较测试 时外部依赖的activity  所有  CaptureActivity上没有使用 RouterHub
     */
    String APP_CAPTUREACTIVITY = ZXING + "/CaptureActivity";
    /**
     * 企业秦增走访
     */
    String APP_BUSINESSVISITACTIVITY = APP + "/BusinessAddVisitActivity";
    /**
     * 企业信息
     */
    String APP_BUSINESSINFORMATIONACTIVITY = APP + "/BusinessInformationActivity";
    /**
     * 企业信息详情
     */
    String APP_BUSINESSINFORMATIONDETAILACTIVITY = APP + "/BusinessInformationDetailActivity";
    /**
     * 企业上报问题列表
     */
    String APP_ENTERPRISEISSUELISTACTIVITY = APP + "/EnterpriseIssueListActivity";
    /**
     * 企业上报问题详情
     */
    String APP_ENTERPRISEISSUESEEACTIVITY = APP + "/EnterpriseIssueSeeActivity";
    /**
     * 企业上报问题编辑
     */
    String APP_ENTERPRISEISSUEEDITACTIVITY = APP + "/EnterpriseIssueEditActivity";
    /**
     * 企业走访
     */
    String APP_BUSINESSINTERVIEWLISTACTIVITY = APP + "/BusinessInterviewListActivity";
    /**
     * 企业问题
     */
    String APP_BUSINESSQUESTIONACTIVITY = APP + "/BusinessQuestionActivity";

    /**
     * 包挂人走访企业详情
     */
    String APP_BUSINESSINTERVIEWDETAILACTIVITY = APP + "/BusinessInterviewDetailActivity";

    /**
     * 企业问题 详情
     */
    String APP_BUSINESSQUESTIONDETAILACTIVITY = APP + "/BusinessQuestionDetailActivity";
    /**
     * 企业员工详情
     */
    String APP_BUSINESSSTAFFDETAILACTIVITY = APP + "/BusinessStaffDetailActivity";
    /**
     * 统计
     */
    String APP_PIECHARTACTIVITY = APP + "/PieChartActivity";

    /**
     * 新标准地址
     */
    String APP_STANDARDADDRESSONENEWACTIVITY = APP + "/StandardAddressOneNewActivity";
    /**
     * 法务订单中心
     */
    String APP_LAWWORKSACTIVITY = APP + "/LawWorksActivity";
    /**
     * 法务订单中心 - 订单列表
     */
    String APP_LAWWORKSORDERFRAGMENT = APP + "/LawWorksOrderFragment";
    /**
     * 法务订单中心 - 通知列表
     */
    String APP_LAWWORKSINFORMFRAGMENT = APP + "/LawWorksInformFragment";
    /**
     * 法务订单详情
     */
    String APP_LAWWORKSORDERDETAILACTIVITY = APP + "/LawWorksOrderDetailActivity";

    /**
     * 搜索采集信息
     */
    String APP_SEARCH_COLLECTION_ACTICITY = APP + "/SearchCollectionActivity";


    /**
     * 数据统计
     */
    String APP_STATISTICS_ACTIVITY = APP + "/DataStatisticsMainActivity";
    /**
     * 数据统计---区域详情
     */
    String APP_AREADETAILSECONDACTIVITY = APP + "/AreaDetailSecondActivity";

    /**
     * 数据统计详情
     */
    String APP_STATISTICS_DETAIL_ACTIVITY = APP + "/DataStatisticsDetailsActivity";


    /**
     * VR视角activity
     */
    String APP_ARACTIVITY = APP + "/ARActivity";


    /**
     * 综合执法
     */
    String APP_COMPREHENSIVE_LAW_ENFORCEMENT = APP + "/EnforceLawEventActivity";


    /**
     * 综合执法 新建 综合执法
     */
    String APP_ENFORCELAWLAWADDACTIVITY = APP + "/EnforceLawLawAddActivity";

    /**
     * 新地图界面
     */
    String APP_NEWMAPACTIVITY = APP + "/NewMapActivity";


    /**
     * 经济管理首页
     */
    String APP_ECONOMIC_MANAGEMENT = APP + "/EconomicManagementActivity";
    /**
     * 案件详情
     */
    String APP_ENFORCE_LAW_LAW_DETAIL_ACTIVITY = APP + "/EnforceLawLawDetailActivity";


    /**
     * 企业数据采集 页面
     */
    String APP_ENTERPRISE_INFORMATION_COLLECTION = APP + "/EnterpriseInformationCollectionActivity";


    /**
     * 项目数据采集 页面
     */
    String APP_PROJECT_COLLECTION = APP + "/ProjectCollectionActivity";
    /**
     * 项目详情
     */
    String APP_PM_ACTIVITY = APP + "/PmActivity";
    /**
     * 企业详情
     */
    String APP_FIRM_MANAGE_ACTIVITY = APP + "/FirmManageActivity";


    /**
     * 项目搜索 页面
     */
    String APP_PROJECT_COLLECTION_SEARCH = APP + "/SearchEconoicManageMentActivity";


    /**
     * 通用数据新增 编辑页面
     */
    String APP_COMMON_COLLECTION = APP + "/CommonCollectionActivity";

    /**
     * 地图周边activity
     */
    String APP_CIRCUM_MAP_ACTIVITY = APP + "/CircumMapActivity";


    /**
     * 积分界面
     */
    String APP_INTEGRAL_ACTIVITY = APP + "/IntegralMainActivity";

    /**
     * 积分规则界面
     */
    String APP_INTEGRAL_RULE_ACTIVITY = APP + "/IntegralRuleActivity";
    /**
     * 事项列表
     */
    String APP_MATTERLISTACTIVITY = APP + "/MatterListActivity";
    /**
     * 事项详情
     */
    String APP_MATTERDETAILACTIVITY = APP + "/MatterDetailActivity";
    /**
     * 新增事项
     */
    String APP_MATTERADDACTIVITY = APP + "/MatterAddActivity";

    /**
     * 个人工作日志
     */
    String APP_PERSONAL_ACTIVITY = APP + "/PersonalWorkActivity";
}

