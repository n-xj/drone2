package com.md.drone.controller;

import com.md.drone.common.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 健康检查，用于确认后端已启动。
 */
@RestController //提供 JSON 接口
@RequestMapping("/api") //所有接口都以 /api 开头
public class HealthController {

    /**
     * @return 运行状态
     */
    @GetMapping("/health")//健康检查接口
    public AjaxResult<String> health() {//返回运行状态
        return AjaxResult.success("ok");//返回成功响应
    }
}
