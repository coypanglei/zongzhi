允许程序打开网络套接字

<uses-permission android:name="android.permission.INTERNET"/>
1
允许程序读取短信息

<uses-permission android:name="android.permission.READ_SMS"/>
1
允许程序监视、修改有关播出电话

<uses-permission android:name="android.permissioin.PROCESS_OUTGOING_CALLS"/>
1
ACCESS_CHECKIN_PROPERTIES 允许读写访问"properties"表在checkin数据库中，改值可以修改上传

ACCESS_COARSE_LOCATION 允许应用程序访问粗糙的位置（例如：Cell-ID,WiFi）

ACCESS_FINE_LOCATION 允许一个程序访问精良位置(如GPS)

ACCESS_LOCATION_EXTRA_COMMANDS 允许一个程序访问额外的位置来提供命令。

ACCESS_MOCK_LOCATION 允许程序创建模拟位置提供用于测试

ACCESS_NETWORK_STATE 允许程序访问有关GSM网络信息

ACCESS_SURFACE_FLINGER 允许程序使用SurfaceFlinger底层特性

ACCESS_WIFI_STATE 允许程序访问Wi-Fi网络状态信息

BATTERY_STATS 允许程序收集手机电池统计信息

BIND_APPWIDGET 允许程序通知AppWidget服务，哪个程序可以访问AppWidget数据

BIND_INPUT_METHOD 输入法服务必须请求来确保只有唯一的系统可以绑定他们。

BLUETOOTH 允许程序链接匹配的蓝牙设备。

BLUETOOTH_ADMIN 允许程序发现和配对蓝牙设备

BRICK 请求能够禁用设备(非常危险)

BROADCAST_PACKAGE_REMOVED 允许程序广播一个提示消息在一个应用程序包已经移除后

BROADCAST_SMS 允许程序广播一个SMS接收提示。

BROADCAST_STICKY 允许一个程序广播常用

BROADCAST_WAP_PUSH 允许程序广播一个WAP PUSH接收提示。

CALL_PHONE 允许一个程序初始化一个电话拨号不需通过拨号用户界面需要用户确认

CALL_PRIVILEGED 允许一个程序拨打任何号码，包含紧急号码无需通过拨号用户界面需要用户确认

CAMERA 请求访问使用照相设备

CHANGE_COMPONENT_ENABLED_STATE 允许一个程序是否改变一个组件或其他的启用或禁用

CHANGE_CONFIGURATION 允许一个程序修改当前设置，如本地化

CHANGE_NETWORK_STATE 允许程序改变网络连接状态

CHANGE_WIFI_MULTICAST_STATE 允许程序进入Wi-Fi连通模式。

CHANGE_WIFI_STATE 允许程序改变Wi-Fi连接状态。

CLEAR_APP_CACHE 允许程序清除设备上所有安装程序的缓存。

CLEAR_APP_USER_DATA 允许程序清除用户数据。

CONTROL_LOCATION_UPDATES 允许启用禁止位置更新提示从无线模块

DELETE_CACHE_FILES 允许程序删除缓存文件

DELETE_PACKAGES 允许程序删除数据包

DEVICE_POWER 允许访问底层电源管理

DIAGNOSTIC 允许程序RW诊断资源

DISABLE_KEYGUARD 允许程序禁用键盘锁

DUMP 允许程序恢复状态转存信息通过系统服务

EXPAND_STATUS_BAR 允许程序扩展或折叠状态栏

FACTORY_TEST 作为一个工厂测试程序，运行在root用户

FLASHLIGHT 允许访问闪光灯

FORCE_BACK 允许程序强行一个返回程序无论什么在顶层活动

GET_ACCOUNTS 允许账户服务中访问账户列表

GET_PACKAGE_SIZE 允许一个程序获取任何package占用空间容量

GET_TASKS 允许一个程序获取信息有关当前或最近运行的任务，一个缩略的任务状态，是否活动等等

GLOBAL_SEARCH 许可可能被用在内容提供商上，为了允许全局搜索系统访问他们数据

HARDWARE_TEST 允许访问硬件设备

INJECT_EVENTS 允许一个程序截获用户事件如按键、触摸、轨迹球等变成事件流并发送他们到任何窗口。

INSTALL_LOCATION_PROVIDER 允许程序安装一个位置提供商到位置管理处。

INSTALL_PACKAGES 允许一个程序安装包

INTERNAL_SYSTEM_WINDOW 允许打开窗口使用系统用户界面

INTERNET 允许程序打开网络套接字

MANAGE_APP_TOKENS 允许程序管理(创建、撤消、z-order默认向z轴推移)程序引用在窗口管理器中

MASTER_CLEAR 目前还没有明确的解释，Android开发网分析可能是清除一切数据，类似硬格机

MODIFY_AUDIO_SETTINGS 允许程序修改全局音频设置

MODIFY_PHONE_STATE 允许修改话机状态，如电源，人机接口等

MOUNT_FORMAT_FILESYSTEMS 允许格式化文字系统可移动存储。

MOUNT_UNMOUNT_FILESYSTEMS 允许挂载和反挂载文件系统可移动存储

PERSISTENT_ACTIVITY 允许一个程序设置他的activities显示

PROCESS_OUTGOING_CALLS 允许程序监视、修改有关播出电话

READ_CALENDAR 允许程序读取用户日历数据

READ_CONTACTS 允许程序读取用户联系人数据

READ_FRAME_BUFFER 允许程序屏幕波或和更多常规的访问帧缓冲数据

READ_HISTORY_BOOKMARKS 允许程序读取（但不能写入）用户浏览器的历史记录和收藏夹。

READ_INPUT_STATE 允许程序恢复按键的当前状态。

READ_LOGS 允许程序读取底层系统日志文件

READ_OWNER_DATA 允许程序读取所有者数据

READ_PHONE_STATE 允许只读来访问话机状态

READ_SMS 允许程序读取短信息

READ_SYNC_SETTINGS 允许程序读取同步设置

READ_SYNC_STATS 许程序读取同步状态

REBOOT 请求能够重新启动设备

RECEIVE_BOOT_COMPLETED 允许一个程序接收到 ACTION_BOOT_COMPLETED，它在系统完成启动后广播

RECEIVE_MMS 允许一个程序监控将收到MMS彩信,记录或处理

RECEIVE_SMS 允许程序监控一个将收到短信息，记录或处理

RECEIVE_WAP_PUSH 允许程序监控将收到WAP PUSH信息

RECORD_AUDIO 允许程序录制音频

REORDER_TASKS 允许程序改变Z轴排列任务

RESTART_PACKAGES 允许程序重新启动其他程序

SEND_SMS 许程序发送SMS短信

SET_ACTIVITY_WATCHER 允许程序监控和控制活动如何在全球范围系统中开始的

SET_ALWAYS_FINISH 允许程序控制是否活动间接完成当处于后台时

SET_ANIMATION_SCALE 修改全局信息比例

SET_DEBUG_APP 配置一个程序用于调试

SET_ORIENTATION 允许底层访问设置屏幕方向和实际旋转

SET_PREFERRED_APPLICATIONS 允许一个程序修改列表参数PackageManager.addPackageToPreferred() 和PackageManager.removePackageFromPreferred()方法

SET_PROCESS_LIMIT 允许设置最大的运行进程数量（不是必需的）

SET_TIME_ZONE 允许程序设置系统时间时区

SET_WALLPAPER 允许程序设置壁纸

SET_WALLPAPER_HINTS 允许程序设置壁纸提示

SIGNAL_PERSISTENT_PROCESSES 允许程序请求发送信号到所有显示的进程中

STATUS_BAR 允许程序打开、关闭或禁用状态栏及图标

SUBSCRIBED_FEEDS_READ 允许一个程序访问订阅RSS Feed（内容提供者）

SUBSCRIBED_FEEDS_WRITE 系统暂时保留改设置,Android开发网认为未来版本会加入该功能。

SYSTEM_ALERT_WINDOW 允许程序打开窗口通过使用TYPE_SYSTEM_ALERT，显示在其他所有程序的顶层

UPDATE_DEVICE_STATS 允许程序更新设备统计信息

VIBRATE 允许访问振动设备

WAKE_LOCK 允许使用PowerManager的WakeLocks保持进程在休眠时从屏幕消失

WRITE_APN_SETTINGS 允许程序写入API设置

WRITE_CALENDAR 允许程序写入但不读取用户日历数据

WRITE_CONTACTS 允许程序写入但不读取用户联系人数据

WRITE_EXTERNAL_STORAGE 允许程序写入外部存储器

WRITE_GSERVICES 允许程序修改Google服务地图

WRITE_HISTORY_BOOKMARKS 允许程序写入但不读取用户浏览器的历史记录和收藏夹

WRITE_OWNER_DATA 允许一个程序写入但不读取所有者数据

WRITE_SECURE_SETTINGS 允许程序读取或写入安全系统设置

WRITE_SETTINGS 允许程序读取或写入系统设置

WRITE_SMS 允许程序写短信

WRITE_SYNC_SETTINGS 允许程序写入同步设置

我是程序小白，每次进步一丢丢
————————————————
版权声明：本文为CSDN博主「一只爱学习的小白」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_30341855/article/details/91980853