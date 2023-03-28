package com.keaizhale.hero.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * description: CommonProperties
 * date: 2023/3/23 13:16
 * author: keaizhale
 * version: 1.0
 */
@Data
@ConfigurationProperties("hero.common")
public class CommonProperties {
    /**
     * i18异常文件地址，默认i18n/biz/error
     * */
    private List<String> i18ErrorPath = List.of("i18n/biz/error");

}
