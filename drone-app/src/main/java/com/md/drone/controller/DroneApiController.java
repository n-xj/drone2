package com.md.drone.controller;

import com.md.drone.common.AjaxResult;
import com.md.drone.domain.Drone;
import com.md.drone.domain.dto.DroneRequest;
import com.md.drone.service.DroneService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 无人机 REST API。
 */
@RestController
@RequestMapping("/api/drones")
public class DroneApiController {
    
    
    private final DroneService droneService;

    /**
     * @param droneService 用例服务
     */
    public DroneApiController(DroneService droneService) {
        this.droneService = droneService;
    }

    /**
     * @param name 搜索关键字
     * @return 列表
     */
     // 1. 查询无人机列表（支持搜索名称）
    @GetMapping
    public AjaxResult<List<Drone>> list(@RequestParam(value = "name", required = false) String name) {
        return AjaxResult.success(droneService.listByName(name));
    }

    /**
     * @param id 主键
     * @return 详情
     */
    // 2. 获取无人机详情
    @GetMapping("/{id}")
    public AjaxResult<Drone> detail(@PathVariable("id") long id) {
        return AjaxResult.success(droneService.getByIdOrThrow(id));
    }

    /**
     * @param request 请求体
     * @return 新建实体
     */
    // 3. 创建无人机
    @PostMapping
    public AjaxResult<Drone> create(@Valid @RequestBody DroneRequest request) {
        return AjaxResult.success(droneService.create(request));
    }

    /**
     * @param id      主键
     * @param request 请求体
     * @return 更新后实体
     */
    // 4. 更新无人机
    @PutMapping("/{id}")
    public AjaxResult<Drone> update(@PathVariable("id") long id, @Valid @RequestBody DroneRequest request) {
        return AjaxResult.success(droneService.update(id, request));
    }

    /**
     * @param id 主键
     * @return 成功响应
     */
    // 5. 删除无人机
    @DeleteMapping("/{id}")
    public AjaxResult<Void> delete(@PathVariable("id") long id) {
        droneService.delete(id);
        return AjaxResult.success();
    }
}
