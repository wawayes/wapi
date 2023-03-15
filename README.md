+ wapi-backend 后台

- wapi-client-sdk
- wapi-gateway 网关
  - 隐藏后台数据传输（避免暴露出secretKey）
  - 路由分发
  - 统一鉴权
  - 限流
  - 日志集中处理
  - 统一业务处理
  - 访问控制（白名单-限制ddos）
- wapi-common 公共部分的抽取，简洁代码



- Spring Boot
- Spring Boot Starter(SDK开发)
- Dubbo (RPC)
- Nacos(注册中心)
- Spring Cloud Gateway(网关、限流、日志实现)



- wapi-backend：7529端口，后端接口管理（上传、下线、用户登录）[http://localhost:7529/api/doc.html](https://gitee.com/link?target=http%3A%2F%2Flocalhost%3A7529%2Fapi%2Fdoc.html)
- wapi-gateway：8090端口，网关
- wapi-interface：8123端口，提供各种接口服务（可以有很多个且分布在各个服务器）。
- wapi-client-sdk：客户端SDK，无端口，发送请求到8090端口，由网关进行转发到后端的api-interface

![image](https://user-images.githubusercontent.com/106217376/225377110-6e6371ba-9403-4c40-bcfa-a216847ca032.png)

