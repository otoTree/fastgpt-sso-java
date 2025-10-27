package org.example.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 全局存储服务
 */
@Service
public class GlobalStoreService {
    
    private final Map<String, TmpValue> tmpMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    
    @PostConstruct
    public void init() {
        // 每5分钟清理一次过期数据
        scheduler.scheduleAtFixedRate(this::clearExpiredValues, 5, 5, TimeUnit.MINUTES);
    }
    
    /**
     * 设置临时值
     */
    public void setTmpValue(String key, Object value, int expiresMinutes) {
        Date expiresAt = new Date(System.currentTimeMillis() + expiresMinutes * 60 * 1000L);
        tmpMap.put(key, new TmpValue(value, expiresAt));
    }
    
    /**
     * 设置临时值（默认5分钟过期）
     */
    public void setTmpValue(String key, Object value) {
        setTmpValue(key, value, 5);
    }
    
    /**
     * 获取临时值
     */
    @SuppressWarnings("unchecked")
    public <T> T getTmpValue(String key) {
        TmpValue tmpValue = tmpMap.get(key);
        if (tmpValue == null) {
            return null;
        }
        
        if (tmpValue.expiresAt.before(new Date())) {
            tmpMap.remove(key);
            return null;
        }
        
        return (T) tmpValue.value;
    }
    
    /**
     * 清理过期值
     */
    private void clearExpiredValues() {
        Date now = new Date();
        tmpMap.entrySet().removeIf(entry -> {
            if (entry.getValue().expiresAt.before(now)) {
                System.out.println("Key " + entry.getKey() + " with value expired, removed");
                return true;
            }
            return false;
        });
    }
    
    /**
     * 临时值包装类
     */
    private static class TmpValue {
        final Object value;
        final Date expiresAt;
        
        TmpValue(Object value, Date expiresAt) {
            this.value = value;
            this.expiresAt = expiresAt;
        }
    }
}