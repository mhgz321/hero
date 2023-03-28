package com.keaizhale.hero.common.filter;

import com.keaizhale.hero.common.context.ServiceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * description: 统一上下文过滤器
 * date: 2023/3/24 9:31
 * author: keaizhale
 * version: 1.0
 */
public class ServiceContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            ServiceContext.getCurrentContext().setRequest(request);

            filterChain.doFilter(request, response);
        } finally {
            ServiceContext.getCurrentContext().unset();
        }
    }
}
