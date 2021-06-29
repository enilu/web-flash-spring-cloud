# web-flash(spring cloud版)
- 参考：https://github.com/JourWon/springcloud-learning
- 参考 aisspringcloud项目 

## 模块
- eureka-server 服务注册中心
- flash-common 基础公共模块
- flash-config-server 配置中心（基于git服务）
- flash-generator 代码生成模块
- flash-manage 后台管理api
- flash-message 消息服务
- flash-schedule 定时任务
- flash-vue-admin 后台管理界面
- flash-vue-h5 前端界面
## 使用
- 克隆本项目
- 导入idea或者eclipse
- 创建数据库：web-flash
     ```sql
        CREATE DATABASE IF NOT EXISTS webflashsc DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
        CREATE USER 'webflashsc'@'%' IDENTIFIED BY 'webflash190602@ABC';
        GRANT ALL privileges ON webflashsc.* TO 'webflashsc'@'%';
        flush privileges;
        ```    
- 在开发环境中配置了系统启动后自动创建数据库和初始化数据，所以不需要开发人员手动初始化数据库
- 确保开发工具下载了lombok插件
- 修改flash-api中数据库连接配置
- 启动flash-api，访问http://localhost:8082/swagger-ui.html ， 保证api服务启动成功
- 进入flash-vue-admin目录
    - 运行 npm install --registry=https://registry.npm.taobao.org
    - 运行npm run dev
    - 启动成功后访问 http://localhost:9528 ,登录，用户名密码:admin/admin     
- 后台管理运行效果图：
    ![admin](https://gitee.com/enilu/web-flash/raw/master/docs/vuejs.gif)
- 进入flash-vue-h5目录
    - 运行 npm install --registry=https://registry.npm.taobao.org
    - 运行npm run dev
    - 启动成功后访问 http://localhost:8088/#/index
- 手机端运行效果图：
    ![mobile](https://gitee.com/enilu/web-flash/raw/master/docs/flash-mobile.gif)

## 在线文档
- [http://webflash.enilu.cn](http://webflash.enilu.cn)

## 交流
- Gitter: [Gitter channel](https://gitter.im/web-flash/community)
- QQ:752844606

[Change Log](http://enilu.gitee.io/web-flash/other/changeLog.html)
