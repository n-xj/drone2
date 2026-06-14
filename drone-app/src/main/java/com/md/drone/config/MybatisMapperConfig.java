package com.md.drone.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 在存在 {@link DataSource} 时注册 MyBatis Mapper（WebMvc 切片无数据源则跳过，避免无 SqlSession 报错）。
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@MapperScan("com.md.drone.repository.mapper")
public class MybatisMapperConfig {
}
