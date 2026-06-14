package com.md.drone.repository.mapper;

import com.md.drone.domain.Drone;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表 {@code t_drone} 的 MyBatis 映射器，SQL 定义见 {@code mapper/DroneMapper.xml}。
 */
@Mapper

//在 MybatisMapperConfig 里配置了 Mapper 接口全部放在：com.md.drone.repository.mapper 包下
//mybatis接口
//每个方法对应xml里的一条SQL
public interface DroneMapper {

    /**
     * @param row 行数据
     * @return 影响行数
     */
    int insert(Drone row);//插入无人机数据

    /**
     * @param row 行数据
     * @return 影响行数
     */
    int updateById(Drone row);//更新无人机数据

    /**
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(@Param("id") long id);//删除无人机数据

    /**
     * @param id 主键
     * @return 行或 null
     */
    Drone selectById(@Param("id") long id);//根据id查询无人机数据

    /**
     * @param nameLike like 值；为 null 时不按名称过滤
     * @return 列表
     */
    List<Drone> selectList(@Param("nameLike") String nameLike);//根据模糊查询条件查询无人机数据
}
