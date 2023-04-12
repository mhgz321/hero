package com.keaizhale.hero.satoken.utils;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import lombok.experimental.UtilityClass;

/**
 * description: LoginHelper
 * date: 2023/4/4 15:02
 * author: keaizhale
 * version: 1.0
 */
@UtilityClass
public class LoginHelper {
    public static final String LOGIN_USER_KEY = "loginUser";
    public static final String USER_KEY = "USER_ID";

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        Long userId;
        try {
            userId = Convert.toLong(SaHolder.getStorage().get(USER_KEY));
            if (ObjectUtil.isNull(userId)) {
                userId = Convert.toLong(StpUtil.getExtra(USER_KEY));
                SaHolder.getStorage().set(USER_KEY, userId);
            }
        } catch (Exception e) {
            return null;
        }
        return userId;
    }
}
