package com.md.drone.controller;

import com.md.drone.common.AjaxResult;
import com.md.drone.domain.dto.LoginRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证 REST API。
 */
@RestController //提供 JSON 接口
@RequestMapping("/api/auth") //所有接口都以 /api/auth 开头
public class AuthApiController {

    /**
     * @param request 登录凭据
     * @return 当前用户信息
     */
    @PostMapping("/login")//登录接口
    public AjaxResult<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {//接收请求体 JSON 数据
        Subject subject = SecurityUtils.getSubject();
        // 创建 Shiro 的登录令牌
        UsernamePasswordToken token = new UsernamePasswordToken(request.getUsername(), request.getPassword());
        try {
            subject.login(token);//执行 Shiro 登录认证
        }
        catch (AuthenticationException e) {//登录失败
            return AjaxResult.fail(401, "用户名或密码错误");
        }
        Map<String, String> data = new HashMap<String, String>();  //返回用户名
        data.put("username", String.valueOf(subject.getPrincipal()));
        return AjaxResult.success(data);
    }

    /**
     * @return 成功响应
     */
    @PostMapping("/logout")//登出接口
    public AjaxResult<Void> logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        return AjaxResult.success();
    }

    /**
     * @return 当前用户名
     */
    @GetMapping("/me")//获取当前用户信息接口
    public AjaxResult<Map<String, String>> me() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return AjaxResult.fail(401, "未登录");
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("username", String.valueOf(subject.getPrincipal()));
        return AjaxResult.success(data);
    }
}
