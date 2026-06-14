package com.md.drone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 对外 HTTP 调用（如可配置的 AI 服务）的 {@link RestTemplate} 工厂。
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 创建默认的 {@link RestTemplate} 实例，供单例复用。
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
