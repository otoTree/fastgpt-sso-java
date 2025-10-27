package org.example.util;

/**
 * 错误处理工具类
 */
public class ErrorUtils {
    
    /**
     * 获取错误文本
     */
    public static String getErrText(Throwable error) {
        if (error == null) {
            return "Internal server error";
        }
        
        String message = error.getMessage();
        return message != null ? message : "Internal server error";
    }
}