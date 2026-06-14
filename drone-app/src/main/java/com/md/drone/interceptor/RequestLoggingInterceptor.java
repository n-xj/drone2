package com.md.drone.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录进入控制器前的请求行与查询参数、User-Agent（不记录密码等敏感名）。
 */
@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    private static final String SENSITIVE = "password";

    /**
     * 在控制层执行前输出请求方法、URI、非敏感参数与客户端信息。
     *
     * @param request  当前请求
     * @param response 当前响应
     * @param handler  目标处理器
     * @return 始终为 true
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler) {
        if (!LOG.isInfoEnabled()) {
            return true;
        }
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String ua = request.getHeader("User-Agent");
        Map<String, String> safeParams = safeParameterMap(request);
        LOG.info("HTTP {} {}{} | params={} | User-Agent={}", method, uri,
                query == null ? "" : "?" + query, safeParams, ua);
        return true;
    }

    private static Map<String, String> safeParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        if (names == null) {
            return Collections.unmodifiableMap(map);
        }
        while (names.hasMoreElements()) {
            String n = names.nextElement();
            if (n == null) {
                continue;
            }
            if (SENSITIVE.equalsIgnoreCase(n)) {
                map.put(n, "******");
            }
            else {
                map.put(n, request.getParameter(n));
            }
        }
        return Collections.unmodifiableMap(map);
    }
}
