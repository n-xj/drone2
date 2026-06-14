package com.md.drone.config;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * API 未认证时返回 JSON 401。
 */
public class ApiAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isOptionsRequest(request)) {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
         // 如果是【登录接口请求】，走Shiro默认逻辑（执行登录校验）
        if (isLoginRequest(request, response)) {
            return super.onAccessDenied(request, response);
        }
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpResponse.getWriter().write("{\"code\":401,\"msg\":\"未登录或会话已过期\",\"data\":null}");
        return false;
    }

    private static boolean isOptionsRequest(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        return "OPTIONS".equalsIgnoreCase(httpRequest.getMethod());
    }
}
