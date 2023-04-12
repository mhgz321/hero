package com.keaizhale.hero.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 安全相关配置
 * date: 2023/4/6 9:48
 * author: keaizhale
 * version: 1.0
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    /**权限认证忽略的地址，支持通配符*/
    private List<String> ignores = new ArrayList<>();
}
