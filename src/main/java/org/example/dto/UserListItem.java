package org.example.dto;

import java.util.List;

/**
 * 用户列表项 DTO
 */
public class UserListItem {
    private String username;
    private String memberName;
    private String avatar;
    private String contact;
    private List<String> org;

    public UserListItem() {}

    public UserListItem(String username, String memberName, String avatar, String contact, List<String> org) {
        this.username = username;
        this.memberName = memberName;
        this.avatar = avatar;
        this.contact = contact;
        this.org = org;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<String> getOrg() {
        return org;
    }

    public void setOrg(List<String> org) {
        this.org = org;
    }
}