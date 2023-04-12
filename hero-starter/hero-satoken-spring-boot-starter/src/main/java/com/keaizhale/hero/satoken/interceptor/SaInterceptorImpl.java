package com.keaizhale.hero.satoken.interceptor;

import cn.dev33.satoken.stp.StpInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * description: SaInterceptorImpl
 * date: 2023/4/6 10:39
 * author: keaizhale
 * version: 1.0
 */
public class SaInterceptorImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object o, String s) {
        //TODO 获取菜单权限列表
        return List.of();
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        //TODO 获取角色列表
        return List.of("admin", "user");
    }
}
