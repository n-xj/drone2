package com.md.drone.repository;

import com.md.drone.domain.Drone;

import java.util.List;
import java.util.Optional;

/**
 * 无人机持久化能力抽象，不暴露 MyBatis 细节。
 */
public interface DroneRepository {

    /**
     * 持久化新实体，成功后 {@link Drone#getId()} 被填充。
     *
     * @param drone 实体
     */
    void save(Drone drone);

    /**
     * 按主键更新；不存在时无效果。
     *
     * @param drone 含主键
     * @return 是否实际更新
     */
    boolean update(Drone drone);

    /**
     * 按主键删除。
     *
     * @param id 主键
     * @return 是否实际删除
     */
    boolean deleteById(long id);

    /**
     * 按主键查询。
     *
     * @param id 主键
     * @return 可选行
     */
    Optional<Drone> findById(long id);

    /**
     * 按名称过滤（可空，模糊），按 id 降序。
     *
     * @param nameOrNull 原样过滤串；null/空 表示不筛选
     * @return 列表
     */
    List<Drone> findByNameOptionally(String nameOrNull);
}
