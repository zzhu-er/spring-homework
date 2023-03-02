
<h1 align="center">
  <br>
  Spring Homework (with Docker)
  <br>
</h1>



## Spring App Requirements

* 使用Spring initializr(https://start.spring.io/) 初始化项目，使用Gradle进行构建 ✅
* 创建REST API（User API），实现对User的CRUD。User对象需要包含以下属性：id, name, age, createdAt, updatedAt等 ✅
* 使用 PostgreSQL (docker) 进行数据持久化，使用 Flyway 进行数据库版本控制 ✅
* 使用Docker搭建本地运行环境, 本地Docker运行环境中使用不同profile ✅
* 使用Junit5和Mockito编写单元测试，使用jacoco进行测试覆盖率检查 ✅ 
* 除配置类外测试覆盖率100% <span style="color:red">(TBD)</span>
* 需要实现动态查询，比如根据姓名和年龄或者创建时间区间进行查询 <span style="color:red">(TBD)</span>
* 创建用户时，id、createdAt 和 updatedAt需要自动生成 ✅
* 需要实现分页查询功能 ✅
* 使用Nginx转发请求 ✅

## Docker Requirements

* Build docker image ✅
* 使用 docker-compose 管理 docker container ✅
* 使用数据库, restart 后数据不丢失（volume) ✅
* 根据需求，各services分布在不同network ✅

## How To Use

To clone and run this application, you'll need [Docker](https://www.docker.com) installed on your computer. From your command line:

```bash
# Clone this repository
$ git clone https://github.com/zzhu-er/spring-homework.git

# Go into the docker directory
$ cd src/main/docker

# Create and starts containers
$ docker compose --profile main up 
```

Nginx server is listenning on port 8000. You can use [curl](https://curl.se) to send requests. From your command line:
```bash
# For Delete
$ curl --request DELETE http://localhost:8000/users/{id}

#For Get
$ curl --request GET localhost:8000/users | jq

#For Post
$ curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name":"Junnan","age":18}' \
  http://localhost:8000/users

#For Put
$ curl --header "Content-Type: application/json" \
  --request PUT \
  --data '{"name":"Zihao","age":18}' \
  http://localhost:8000/users/{id}
```
> **Note**
> You can use [jq](https://stedolan.github.io/jq) to format the response from GET request.