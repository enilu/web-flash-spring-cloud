# web-flash(spring cloud版)

## 模块
- eureka-server 服务注册中心
- flash-common 基础公共模块
- flash-config-server 配置中心（基于git服务）
- flash-manage 后台管理api
- flash-message 消息服务
- flash-schedule 定时任务
- flash-vue-admin 后台管理界面
- flash-vue-h5 前端界面
## 使用
- 克隆本项目
- 导入idea或者eclipse
- 创建数据库：webflashsc
     ```sql
        CREATE DATABASE IF NOT EXISTS webflashsc DEFAULT CHARSET utf8 COLLATE utf8_general_ci; 
        CREATE USER 'webflashsc'@'%' IDENTIFIED BY 'webflash190602@ABC';
        GRANT ALL privileges ON webflashsc.* TO 'webflashsc'@'%';
        flush privileges;
        ```    
- flash-manage模块启动后会自动创建数据库表和初始化数据
- 确保开发工具下载了lombok插件
- 按照顺序启动一下各个微服务
    - eureka-server
    - flash-config-server
    - flash-message
    - flash-schedule
    - flash-manage
- 进入flash-vue-admin目录
    - 运行 npm install --registry=https://registry.npm.taobao.org
    - 运行npm run dev
    - 启动成功后访问 http://localhost:9528 ,登录，用户名密码:admin/admin     
- 后台管理运行效果图：
    ![admin](https://gitee.com/enilu/web-flash/raw/master/docs/vuejs.gif)


## 在线文档
- [http://webflash.enilu.cn](http://webflash.enilu.cn)

## 交流
- Gitter: [Gitter channel](https://gitter.im/web-flash/community)
- QQ:752844606

[Change Log](http://webflash.enilu.cn/guide/other/changeLog.html)
