# FastGPT SSO Java API 测试指南

## 概述

本文档提供了 FastGPT SSO Java 应用的 API 测试指南，包括所有可用的端点、请求示例和响应格式。

## 基础信息

- **基础URL**: `http://localhost:8080`
- **默认端口**: `8080`
- **认证方式**: JWT Token / Basic Auth

## API 端点列表

### 1. 健康检查

**端点**: `GET /test`

**描述**: 检查服务是否正常运行

**请求示例**:
```bash
curl -X GET "http://localhost:8080/test"
```

**响应示例**:
```
FastGPT-SSO-Service
```

---

### 2. 获取认证URL

**端点**: `GET /login/oauth/getAuthURL`

**描述**: 获取OAuth认证URL

**参数**:
- `redirect_uri` (必需): 回调URL
- `state` (可选): 状态参数

**请求示例**:
```bash
curl -X GET "http://localhost:8080/login/oauth/getAuthURL" \
  -G \
  -d "redirect_uri=http://localhost:3000/callback" \
  -d "state=test-state-123" \
  -H "Content-Type: application/json"
```

**响应示例**:
```json
{
  "success": true,
  "message": "",
  "authURL": "http://localhost:8080/oauth/authorize?redirect_uri=..."
}
```

---

### 3. OAuth回调处理

**端点**: `GET /login/oauth/callback`

**描述**: 处理OAuth认证回调

**参数**:
- `code` (必需): 授权码
- `state` (可选): 状态参数

**请求示例**:
```bash
curl -X GET "http://localhost:8080/login/oauth/callback" \
  -G \
  -d "code=test-code-456" \
  -d "state=test-state-123"
```

**响应**: 重定向到指定URL

---

### 4. 获取用户信息

**端点**: `GET /login/oauth/getUserInfo`

**描述**: 根据授权码获取用户信息

**参数**:
- `code` (必需): 授权码

**请求示例**:
```bash
curl -X GET "http://localhost:8080/login/oauth/getUserInfo" \
  -G \
  -d "code=test-code-456" \
  -H "Content-Type: application/json"
```

**响应示例**:
```json
{
  "success": true,
  "message": "",
  "data": {
    "username": "testuser",
    "avatar": "https://example.com/avatar.jpg",
    "contact": "test@example.com",
    "memberName": "Test User"
  }
}
```

---

### 5. 获取用户列表

**端点**: `GET /user/list`

**描述**: 获取系统中的用户列表

**请求示例**:
```bash
curl -X GET "http://localhost:8080/user/list" \
  -H "Content-Type: application/json"
```

**响应示例**:
```json
{
  "success": true,
  "message": "",
  "userList": [
    {
      "id": "1",
      "username": "user1",
      "email": "user1@example.com"
    },
    {
      "id": "2",
      "username": "user2",
      "email": "user2@example.com"
    }
  ]
}
```

---

### 6. 获取组织列表

**端点**: `GET /org/list`

**描述**: 获取系统中的组织列表

**请求示例**:
```bash
curl -X GET "http://localhost:8080/org/list" \
  -H "Content-Type: application/json"
```

**响应示例**:
```json
{
  "success": true,
  "message": "",
  "orgList": [
    {
      "id": "1",
      "name": "Organization 1",
      "description": "First organization"
    },
    {
      "id": "2",
      "name": "Organization 2",
      "description": "Second organization"
    }
  ]
}
```

---

### 7. 获取SAML元数据

**端点**: `GET /login/saml/metadata.xml`

**描述**: 获取SAML服务提供者元数据

**请求示例**:
```bash
curl -X GET "http://localhost:8080/login/saml/metadata.xml" \
  -H "Accept: application/xml"
```

**响应**: XML格式的SAML元数据

---

### 8. SAML断言处理

**端点**: `POST /login/saml/assert`

**描述**: 处理SAML身份提供者的断言

**参数**:
- `SAMLResponse` (必需): Base64编码的SAML响应
- `RelayState` (可选): 中继状态

**请求示例**:
```bash
curl -X POST "http://localhost:8080/login/saml/assert" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "SAMLResponse=PHNhbWw6UmVzcG9uc2U+dGVzdDwvc2FtbDpSZXNwb25zZT4=" \
  -d "RelayState=test-relay-state"
```

**响应**: 重定向到指定URL

---

## 快速测试

### 使用提供的测试脚本

#### Linux/Mac:
```bash
chmod +x test-api.sh
./test-api.sh
```

#### Windows:
```cmd
test-api.bat
```

### 手动测试步骤

1. **启动应用**:
   ```bash
   mvn spring-boot:run
   ```

2. **测试健康检查**:
   ```bash
   curl http://localhost:8080/test
   ```

3. **测试认证流程**:
   ```bash
   # 获取认证URL
   curl "http://localhost:8080/login/oauth/getAuthURL?redirect_uri=http://localhost:3000/callback&state=test"
   
   # 获取用户信息
   curl "http://localhost:8080/login/oauth/getUserInfo?code=test-code"
   ```

## 错误处理

所有API端点都返回统一的错误格式:

```json
{
  "success": false,
  "message": "错误描述信息"
}
```

常见HTTP状态码:
- `200`: 成功
- `302`: 重定向 (用于OAuth/SAML回调)
- `400`: 请求参数错误
- `401`: 未授权
- `500`: 服务器内部错误

## 环境配置

确保在测试前正确配置了环境变量，参考 `.env.example` 文件:

```bash
# 复制环境配置文件
cp .env.example .env

# 编辑配置文件
# 修改必要的配置项如JWT密钥、数据库连接等
```

## 注意事项

1. 测试前确保应用已正常启动
2. 某些端点可能需要有效的认证token
3. SAML相关功能需要配置身份提供者
4. 生产环境中请修改默认的安全配置