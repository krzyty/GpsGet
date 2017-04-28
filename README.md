# GpsGet
安卓Service+ Broadcast +Sqlite实现后台自动搜集设备位置数据

做完这个Demo才发现，在这样一个信息时代，人们隐私被侵犯的成本真的很低。那些原本我们相信只会在电影、小说中才会看到的镜头很可能此时此刻就发生在你我身边。

但转念一想，技术总归是有好的一面。通过妥善使用这些技术，我们的生活真的可以变得更美好。

将这个Demo放在这里，希望使用它的人是为了后者，而不是去做不利于他人的坏事。

这个Demo演示了获取一定权限后，在Activity中创建的服务在后台自动每秒将设备的GPS坐标数据存入数据库。即使Activity、进程中的Application生命周期结束，这个服务也会一直在后台默默采集位置数据。甚至是在关机，然后重新启动设备后，这个服务也会通过监听系统广播而自动启动。只要GPS定位功能开启，位置信息就会以每秒一次的速率源源不断记录到数据库中。

一共包括五个类：
MainActivity： 用来安装后首次启动Service，在这个Demo中还提供直观的数据库存储显示。
AutoStart： BroadcastReceiver，用来在设备关机重新启动时监听系统广播，以启动Service，继续在后台收集数据。
GpsService：在后台默默收集设备GPS坐标的服务。
Gps：用来获取GPS坐标数据，一个封装好的工具类。
MySQLiteOpenHelper：数据库用来建表的工厂类。
