package com.md.drone.domain.dto;

import javax.validation.constraints.NotBlank;

/**
 * 登录 API 请求体。
 * 这个类就是前端登录时，传给后端的【用户名 + 密码】数据格式，专门用来接收登录表单数据。
 * 在登录控制器里面使用
 */
public class LoginRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    /**
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
