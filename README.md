# WHUT OOP
<p align="center">武汉理工大学面向对象程序设计课程实验</p>
<p align="center">
    <img src="https://img.shields.io/static/v1?label=%E5%BC%80%E5%8F%91%E6%97%B6%E9%97%B4&message=2023-2024&color=007bff"/>
    <img src="https://img.shields.io/static/v1?label=Java&message=17&color=e83e8c"/>
    <img src="https://img.shields.io/static/v1?label=MySQL&message=8.0.28&color=fd7e14"/>
    <a href="https://github.com/springbear2020/whut-data-mining-system" target="_blank">
        <img src="https://img.shields.io/static/v1?label=%E5%BC%80%E6%BA%90%E9%A1%B9%E7%9B%AE&message=whut-oop&color=20c997"/>
    </a>
</p>


## 项目介绍

- 基于Java和MySQL的面向对象程序设计课程实验
- 项目为档案管理系统，分为客户端与服务端，使用swing图形页面，maven管理工程
- 具体功能介绍参见[面向对象与多线程综合实验报告](./面向对象与多线程综合实验报告.pdf)
- 本项目基本可以运行，如果有bug请提交issue，我会及时处理

## 运行
在bin目录下有client与server两个jar包，运行时需要先启动服务端，再启动客户端，其中配置文件为config.properties
### 服务端
#### 配置文件
配置文件位于server.jar包同目录下
```properties
server_filepath=server
jdbc_url=jdbc:mysql://mysql:3306/db_oop?serverTimezone=GMT%2b8
jdbc_username=root
jdbc_password=qwert12345
server_port=8080
server_backlog=300
email_name=*** (QQ邮箱)
email_host=smtp.qq.com
email_password=****（QQ邮箱授权码）
```
#### 服务端启动
该服务端有两种启动方式
##### 本地运行
1. 按照[db.sql](./bin/server/mysql/init/db_oop.sql)配置数据库
2. 运行[Server.jar](../oop/bin/server/java/server.jar)
```shell
java -jar server.jar config.properties
```
##### docker运行
1. 按照[docker-compose.yml](./bin/server/docker/docker-compose.yml)配置
2. 运行[docker-compose.yml](./bin/server/docker/docker-compose.yml)
```shell
docker-compose up -d
```
### 客户端
#### 配置文件
配置文件位于client.jar包同目录下
```properties
server_ip=127.0.0.1
server_port=8080
download_filepath=D:\\Code\\Java\\oop\\client\\download
```

#### 客户端启动
1. 运行[Client.jar](../oop/bin/client/java/client.jar)
   
```shell
java -jar client.jar config.properties
```
