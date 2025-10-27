package org.example.service;

import org.example.dto.UserInfo;
import org.example.dto.UserListItem;
import org.example.dto.OrgListItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * SSO 提供者服务接口
 */
public interface SsoProviderService {
    
    /**
     * 获取认证 URL
     */
    String getAuthUrl(HttpServletRequest request, String redirectUri, String state);
    
    /**
     * 处理回调
     */
    String handleCallback(HttpServletRequest request);
    
    /**
     * 获取用户信息
     */
    UserInfo getUserInfo(String code);
    
    /**
     * 获取用户列表
     */
    List<UserListItem> getUserList();
    
    /**
     * 获取组织列表
     */
    List<OrgListItem> getOrgList();
    
    /**
     * 获取 SAML 元数据
     */
    String getSamlMetadata();
    
    /**
     * 处理 SAML 断言
     */
    String handleSamlAssert(String samlResponse, String relayState);
}