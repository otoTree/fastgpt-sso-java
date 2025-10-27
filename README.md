# FastGPT SSO Java

FastGPT 单点登录（SSO）Java 实现，提供完整的 OAuth 2.0 和 SAML 2.0 认证服务。

## 项目简介

本项目是 FastGPT 的 SSO 服务 Java 版本实现，支持多种认证协议和提供者，为 FastGPT 系统提供统一的身份认证和授权服务。

## 技术栈

- **Java 8**
- **Spring Boot 2.7.18**
- **Spring Security**
- **Maven**
- **JWT (JSON Web Token)**
- **SAML 2.0**
- **OAuth 2.0**

## 主要功能

### 认证功能
- OAuth 2.0 认证流程
- SAML 2.0 认证支持
- JWT Token 生成和验证
- 多种 SSO 提供者支持

### API 接口
- 获取认证 URL
- 处理认证回调
- 获取用户信息
- 用户列表管理
- 组织列表管理
- SAML 元数据服务

### 安全特性
- 请求认证中间件
- CORS 跨域支持
- 安全配置管理

## 项目结构

```
src/
├── main/
│   ├── java/org/example/
│   │   ├── FastGptSsoApplication.java     # 主应用程序入口
│   │   ├── config/                        # 配置类
│   │   │   ├── SecurityConfig.java        # 安全配置
│   │   │   └── FilterConfig.java          # 过滤器配置
│   │   ├── controller/                    # 控制器层
│   │   │   └── SsoController.java         # SSO 主控制器
│   │   ├── dto/                          # 数据传输对象
│   │   │   ├── ApiResponse.java          # 通用 API 响应
│   │   │   ├── UserInfo.java             # 用户信息
│   │   │   ├── UserListItem.java         # 用户列表项
│   │   │   └── OrgListItem.java          # 组织列表项
│   │   ├── service/                      # 服务层
│   │   │   ├── SsoProviderService.java   # SSO 服务接口
│   │   │   ├── GlobalStoreService.java   # 全局存储服务
│   │   │   └── impl/                     # 服务实现
│   │   │       └── TestSsoProviderServiceImpl.java
│   │   ├── middleware/                   # 中间件
│   │   │   └── AuthMiddleware.java       # 认证中间件
│   │   └── util/                         # 工具类
│   └── resources/
│       └── application.properties        # 应用配置
└── test/                                # 测试代码
```

## 快速开始

### 环境要求

- Java 8 或更高版本
- Maven 3.6 或更高版本

### 安装步骤

1. **克隆项目**
   ```bash
   git clone <repository-url>
   cd fastgpt-sso-java
   ```

2. **配置环境变量**
   ```bash
   cp .env.example .env
   ```
   
   编辑 `.env` 文件，配置必要的环境变量：
   ```properties
   # 服务器配置
   SERVER_PORT=8080
   SERVER_HOST=localhost
   
   # 应用配置
   SPRING_APPLICATION_NAME=fastgpt-sso-java
   APP_SSO_PROVIDER=test
   APP_AUTH_TOKEN=your-auth-token
   APP_REDIRECT_ENABLED=false
   
   # 测试配置
   TEST_REDIRECT_URI=http://localhost:3000/callback
   TEST_STATE=test-state-123
   TEST_CODE=test-code-456
   TEST_BASE_URL=http://localhost:8080
   ```

3. **构建项目**
   ```bash
   mvn clean compile
   ```

4. **运行应用**
   ```bash
   mvn spring-boot:run
   ```
   
   或者构建 JAR 包后运行：
   ```bash
   mvn clean package
   java -jar target/fastgpt-sso-java-1.0-SNAPSHOT.jar
   ```

5. **验证服务**
   
   访问 http://localhost:8080/test，应该返回 "FastGPT-SSO-Service"

## API 文档

### OAuth 2.0 接口

#### 获取认证 URL
```
GET /login/oauth/getAuthURL
```
参数：
- `redirect_uri` (必需): 回调地址
- `state` (可选): 状态参数

#### 处理认证回调
```
GET /login/oauth/callback
```

#### 获取用户信息
```
GET /login/oauth/getUserInfo
```
参数：
- `code` (必需): 授权码

### 用户管理接口

#### 获取用户列表
```
GET /user/list
```
需要认证 Token

#### 获取组织列表
```
GET /org/list
```
需要认证 Token

### SAML 2.0 接口

#### 获取 SAML 元数据
```
GET /login/saml/metadata.xml
```

#### 处理 SAML 断言
```
POST /login/saml/assert
```
参数：
- `SAMLResponse` (必需): SAML 响应
- `RelayState` (可选): 中继状态

### 测试接口

#### 服务状态检查
```
GET /test
```

## 配置说明

### 应用配置 (application.properties)

```properties
# 服务器端口
server.port=8080

# 应用名称
spring.application.name=fastgpt-sso-java

# SSO 提供者
app.sso.provider=test

# 认证 Token
app.auth.token=xxx

# 重定向功能
app.redirect.enabled=false

# 主机名
app.hostname=
```

### 环境变量配置

支持通过环境变量覆盖配置文件中的设置：

- `SERVER_PORT`: 服务端口
- `APP_SSO_PROVIDER`: SSO 提供者
- `APP_AUTH_TOKEN`: 认证令牌
- `APP_REDIRECT_ENABLED`: 是否启用重定向
- `SAML_*`: SAML 相关配置
- `OAUTH_*`: OAuth 相关配置

## 开发指南

### 添加新的 SSO 提供者

1. 实现 `SsoProviderService` 接口
2. 在 `config` 包中添加相应的配置类
3. 更新 `application.properties` 中的提供者配置

### 自定义认证逻辑

1. 修改 `AuthMiddleware` 类
2. 更新 `SecurityConfig` 配置
3. 添加必要的过滤器规则

### 扩展 API 接口

1. 在 `SsoController` 中添加新的端点
2. 创建相应的 DTO 类
3. 实现业务逻辑

## 部署

### 使用 Maven 构建

```bash
mvn clean package -DskipTests
```

### Docker 部署

项目包含 TypeScript 版本的 Docker 配置，可参考 `fastgpt-sso-template` 目录。

### 生产环境配置

1. 设置正确的数据库连接
2. 配置 HTTPS 证书
3. 设置生产环境的认证密钥
4. 启用日志记录
5. 配置监控和健康检查

## 故障排除

### 常见问题

1. **端口冲突**
   - 修改 `server.port` 配置
   - 检查端口占用情况

2. **认证失败**
   - 检查 `app.auth.token` 配置
   - 验证请求头中的 Token

3. **CORS 错误**
   - 检查 `SecurityConfig` 中的 CORS 配置
   - 确认前端域名在允许列表中

### 日志配置

在 `application.properties` 中添加日志配置：

```properties
logging.level.org.example=DEBUG
logging.level.org.springframework.security=DEBUG
```

## 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证，详情请参阅 LICENSE 文件。

## 联系方式

如有问题或建议，请通过以下方式联系：

- 提交 Issue
- 发送邮件
- 参与讨论

## 更新日志

### v1.0-SNAPSHOT
- 初始版本发布
- 支持 OAuth 2.0 和 SAML 2.0
- 基础用户和组织管理功能
- 完整的 API 接口