@echo off
REM FastGPT SSO Java API 测试脚本 (Windows版本)
REM 使用方法: test-api.bat

REM 配置变量
set BASE_URL=http://localhost:8080
set REDIRECT_URI=http://localhost:3000/callback
set STATE=test-state-123
set CODE=test-code-456

echo === FastGPT SSO Java API 测试 ===
echo 基础URL: %BASE_URL%
echo.

REM 1. 测试健康检查接口
echo 1. 测试健康检查接口
echo GET %BASE_URL%/test
curl -X GET "%BASE_URL%/test" ^
  -H "Content-Type: application/json" ^
  -w "%%{http_code}" ^
  -s
echo.
echo ---

REM 2. 获取认证URL
echo 2. 获取认证URL
echo GET %BASE_URL%/login/oauth/getAuthURL?redirect_uri=%REDIRECT_URI%^&state=%STATE%
curl -X GET "%BASE_URL%/login/oauth/getAuthURL" ^
  -G ^
  -d "redirect_uri=%REDIRECT_URI%" ^
  -d "state=%STATE%" ^
  -H "Content-Type: application/json" ^
  -w "%%{http_code}" ^
  -s
echo.
echo ---

REM 3. 获取用户信息
echo 3. 获取用户信息
echo GET %BASE_URL%/login/oauth/getUserInfo?code=%CODE%
curl -X GET "%BASE_URL%/login/oauth/getUserInfo" ^
  -G ^
  -d "code=%CODE%" ^
  -H "Content-Type: application/json" ^
  -w "%%{http_code}" ^
  -s
echo.
echo ---

REM 4. 获取用户列表
echo 4. 获取用户列表
echo GET %BASE_URL%/user/list
curl -X GET "%BASE_URL%/user/list" ^
  -H "Content-Type: application/json" ^
  -w "%%{http_code}" ^
  -s
echo.
echo ---

REM 5. 获取组织列表
echo 5. 获取组织列表
echo GET %BASE_URL%/org/list
curl -X GET "%BASE_URL%/org/list" ^
  -H "Content-Type: application/json" ^
  -w "%%{http_code}" ^
  -s
echo.
echo ---

REM 6. 获取SAML元数据
echo 6. 获取SAML元数据
echo GET %BASE_URL%/login/saml/metadata.xml
curl -X GET "%BASE_URL%/login/saml/metadata.xml" ^
  -H "Accept: application/xml" ^
  -w "%%{http_code}" ^
  -s
echo.
echo ---

REM 7. 测试SAML断言 (POST)
echo 7. 测试SAML断言
echo POST %BASE_URL%/login/saml/assert
set SAML_RESPONSE=PHNhbWw6UmVzcG9uc2U+dGVzdDwvc2FtbDpSZXNwb25zZT4=
curl -X POST "%BASE_URL%/login/saml/assert" ^
  -H "Content-Type: application/x-www-form-urlencoded" ^
  -d "SAMLResponse=%SAML_RESPONSE%" ^
  -d "RelayState=test-relay-state" ^
  -w "%%{http_code}" ^
  -s
echo.
echo ---

REM 8. 测试OAuth回调
echo 8. 测试OAuth回调
echo GET %BASE_URL%/login/oauth/callback?code=%CODE%^&state=%STATE%
curl -X GET "%BASE_URL%/login/oauth/callback" ^
  -G ^
  -d "code=%CODE%" ^
  -d "state=%STATE%" ^
  -H "Content-Type: application/json" ^
  -w "%%{http_code}" ^
  -s
echo.

echo === 测试完成 ===
pause