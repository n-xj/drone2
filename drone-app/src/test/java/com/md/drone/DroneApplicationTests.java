package com.md.drone;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * 确保 Spring 上下文在测试环境（SQLite 内存、mock AI）下能启动。
 */
@SpringBootTest
@ActiveProfiles("test")
class DroneApplicationTests {

    @Test
    void contextLoads() {
        // 若依赖或 Shiro / MyBatis 配置错误，本方法将启动失败
    }
}
