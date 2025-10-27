package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 通用 API 响应 DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String authURL;
    private T userList;
    private T orgList;

    public ApiResponse() {}

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, "");
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "", data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getAuthURL() {
        return authURL;
    }

    public void setAuthURL(String authURL) {
        this.authURL = authURL;
    }

    public T getUserList() {
        return userList;
    }

    public void setUserList(T userList) {
        this.userList = userList;
    }

    public T getOrgList() {
        return orgList;
    }

    public void setOrgList(T orgList) {
        this.orgList = orgList;
    }
}