package org.example.controller;

import org.example.dto.ApiResponse;
import org.example.dto.UserInfo;
import org.example.dto.UserListItem;
import org.example.dto.OrgListItem;
import org.example.service.SsoProviderService;
import org.example.util.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.List;

/**
 * SSO 控制器
 */
@RestController
public class SsoController {

    @Autowired
    @Qualifier("testSsoProvider")
    private SsoProviderService ssoProviderService;

    @Value("${app.redirect.enabled:false}")
    private boolean redirectEnabled;

    @Value("${app.hostname:}")
    private String hostname;

    /**
     * 获取认证 URL
     */
    @GetMapping("/login/oauth/getAuthURL")
    public ResponseEntity<ApiResponse<String>> getAuthUrl(
            HttpServletRequest request,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam(value = "state", required = false) String state) {

        try {
            if (redirectEnabled && hostname != null && !hostname.isEmpty()) {
                URL hostnameUrl = new URL(hostname);
                if (!request.getServerName().equals(hostnameUrl.getHost())) {
                    String authURL = hostname + request.getRequestURI() + "?" + request.getQueryString();
                    ApiResponse<String> response = ApiResponse.success();
                    response.setAuthURL(authURL);
                    return ResponseEntity.ok(response);
                }
            }

            String authUrl = ssoProviderService.getAuthUrl(request, redirectUri, state);
            ApiResponse<String> response = ApiResponse.success();
            response.setAuthURL(authUrl);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(ErrorUtils.getErrText(e)));
        }
    }

    /**
     * 处理回调
     */
    @GetMapping("/login/oauth/callback")
    public ResponseEntity<Void> handleCallback(HttpServletRequest request) {
        try {
            String redirectUrl = ssoProviderService.handleCallback(request);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", redirectUrl)
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/login/oauth/getUserInfo")
    public ResponseEntity<ApiResponse<UserInfo>> getUserInfo(@RequestParam("code") String code) {
        try {
            UserInfo userInfo = ssoProviderService.getUserInfo(code);
            ApiResponse<UserInfo> response = ApiResponse.success("", userInfo);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(ErrorUtils.getErrText(e)));
        }
    }

    /**
     * 获取用户列表
     */
    @GetMapping("/user/list")
    public ResponseEntity<ApiResponse<List<UserListItem>>> getUserList() {
        try {
            List<UserListItem> userList = ssoProviderService.getUserList();
            ApiResponse<List<UserListItem>> response = ApiResponse.success();
            response.setUserList(userList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(ErrorUtils.getErrText(e)));
        }
    }

    /**
     * 获取组织列表
     */
    @GetMapping("/org/list")
    public ResponseEntity<ApiResponse<List<OrgListItem>>> getOrgList() {
        try {
            List<OrgListItem> orgList = ssoProviderService.getOrgList();
            ApiResponse<List<OrgListItem>> response = ApiResponse.success();
            response.setOrgList(orgList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(ErrorUtils.getErrText(e)));
        }
    }

    /**
     * 测试接口
     */
    @GetMapping("/test")
    public String test() {
        return "FastGPT-SSO-Service";
    }

    /**
     * 获取 SAML 元数据
     */
    @GetMapping(value = "/login/saml/metadata.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getSamlMetadata() {
        try {
            String metadata = ssoProviderService.getSamlMetadata();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .body(metadata);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<?xml version=\"1.0\"?><error>" + ErrorUtils.getErrText(e) + "</error>");
        }
    }

    /**
     * 处理 SAML 断言
     */
    @PostMapping("/login/saml/assert")
    public ResponseEntity<Void> handleSamlAssert(
            @RequestParam("SAMLResponse") String samlResponse,
            @RequestParam(value = "RelayState", required = false) String relayState) {
        try {
            String redirectUrl = ssoProviderService.handleSamlAssert(samlResponse, relayState);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", redirectUrl)
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}