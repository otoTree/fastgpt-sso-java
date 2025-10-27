package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户信息 DTO
 */
public class UserInfo {
    private String username;
    private String avatar;
    private String contact;
    
    @JsonProperty("memberName")
    private String memberName;

    public UserInfo() {}

    public UserInfo(String username, String avatar, String contact, String memberName) {
        this.username = username;
        this.avatar = avatar;
        this.contact = contact;
        this.memberName = memberName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}