package com.md.drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 无人机管理应用入口。
 */
@SpringBootApplication
@EnableTransactionManagement
public class DroneApplication {

    /**
     * 启动内嵌容器与 Spring 上下文。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(DroneApplication.class, args);
    }
}
