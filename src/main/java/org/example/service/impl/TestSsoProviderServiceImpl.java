package org.example.service.impl;

import org.example.dto.UserInfo;
import org.example.dto.UserListItem;
import org.example.dto.OrgListItem;
import org.example.service.SsoProviderService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 测试 SSO 提供者服务实现
 */
@Service("testSsoProvider")
public class TestSsoProviderServiceImpl implements SsoProviderService {

    @Override
    public String getAuthUrl(HttpServletRequest request, String redirectUri, String state) {
        // 测试实现：返回一个模拟的认证 URL
        return "https://test-sso.example.com/auth?redirect_uri=" + redirectUri + "&state=" + state;
    }

    @Override
    public String handleCallback(HttpServletRequest request) {
        // 测试实现：返回一个模拟的重定向 URL
        String redirectUri = request.getParameter("redirect_uri");
        String state = request.getParameter("state");
        return redirectUri + "?code=test_code&state=" + state;
    }

    @Override
    public UserInfo getUserInfo(String code) {
        // 测试实现：返回模拟用户信息
        return new UserInfo("testuser", "https://example.com/avatar.jpg", "test@example.com", "Test User");
    }

    @Override
    public List<UserListItem> getUserList() {
        // 测试实现：返回模拟用户列表
        return Arrays.asList(
            new UserListItem("user1", "User One", "https://example.com/avatar1.jpg", "user1@example.com", Arrays.asList("org1")),
            new UserListItem("user2", "User Two", "https://example.com/avatar2.jpg", "user2@example.com", Arrays.asList("org1", "org2"))
        );
    }

    @Override
    public List<OrgListItem> getOrgList() {
        // 测试实现：返回模拟组织列表
        return Arrays.asList(
            new OrgListItem("org1", "Organization One", ""),
            new OrgListItem("org2", "Organization Two", "org1")
        );
    }

    @Override
    public String getSamlMetadata() {
        // 测试实现：返回模拟 SAML 元数据
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><EntityDescriptor>Test SAML Metadata</EntityDescriptor>";
    }

    @Override
    public String handleSamlAssert(String samlResponse, String relayState) {
        // 测试实现：返回模拟重定向 URL
        return "https://example.com/callback?saml=success&state=" + relayState;
    }
}