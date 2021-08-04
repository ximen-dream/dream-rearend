# CICD - DEVOPS
## 服务器

| 服务        | 主机                                                         | 访问                |
| ----------- | ------------------------------------------------------------ | ------------------- |
| Nacos服务端 | 47.100.22.236:8848  -> 10.8.0.10:8848(192.168.111.81:8848)   | 47.100.22.236:8848  |
| Harbor      | 47.100.22.236:20000  -> 10.8.0.10:20000(192.168.111.81:20000) | 47.100.22.236:20000 |
| Jenkins     | 47.100.22.236:8080-> 10.8.0.10:8080(192.168.111.81:8080)     | 47.100.22.236:8080  |
| Nginx       | 47.100.22.236                                                | 47.100.22.236       |

## 服务和端口

| 服务                | 描述     | 端口 |
| ------------------- | -------- | ---- |
| deream-gateway      | 网关     | 7301 |
| dream-auth          | 认证服务 | 7101 |
| dream-server-system | 系统服务 | 7201 |



## Jenkins

### 配置信息

### pipeline脚本


# 47.100.22.236 damoncai.top

## 前端工程
## Nacos

## 111.231.15.245

# 工程快速搭建
1. POM文件
```xml
<dependency>
    <groupId>com.ximen</groupId>
    <artifactId>dream-common-security-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
2. 开启资源服务器认证
```shell script
@EnableDreamCloudResourceServer
```
3.YML文件