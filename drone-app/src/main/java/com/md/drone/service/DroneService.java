package com.md.drone.service;

import com.md.drone.domain.dto.DroneRequest;
import com.md.drone.domain.Drone;
import java.util.List;

/**
 * 无人机用例级服务：查询、增删改与 AI 属性编排。
 */
public interface DroneService {

    /**
     * 按名称（可空、模糊）列出记录。
     *
     * @param nameOrNull 原样子串
     * @return 列表
     */
    List<Drone> listByName(String nameOrNull);

    /**
     * 按主键获取单条，不存在时抛出业务异常（由表现层转译）。
     *
     * @param id 主键
     * @return 实体
     * @throws IllegalArgumentException 不存在
     */
    Drone getByIdOrThrow(long id);

    /**
     * 创建：调用 AI 填属性后保存。
     *
     * @param request 请求体
     * @return 新建后的实体（含主键与属性）
     */
    Drone create(DroneRequest request);

    /**
     * 更新：如请求声明 {@code regenerateAttributes} 则重算属性。
     *
     * @param id      主键
     * @param request 请求体
     * @return 更新后实体
     * @throws IllegalArgumentException 不存在
     */
    Drone update(long id, DroneRequest request);
    /**
     * 按主键删除。
     *
     * @param id 主键
     * @throws IllegalArgumentException 不存在
     */
    void delete(long id);
}
