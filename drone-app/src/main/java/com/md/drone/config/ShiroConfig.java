package com.md.drone.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Apache Shiro 安全过滤器链（REST API 模式）。
 */
@Configuration
@Order(1)
public class ShiroConfig {

    private static final String INI_PATH = "classpath:shiro.ini";

    /**
     * @return Shiro 生命周期后处理器
     */
    @Bean
    public static LifecycleBeanPostProcessor shiroLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @return Web 安全管理器
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        IniRealm iniRealm = new IniRealm(INI_PATH);
        manager.setRealm(iniRealm);
        return manager;
    }
    //SecurityManager：Shiro 的总控制器
    //IniRealm：Shiro 的配置文件

    /**
     * 注意：{@link ApiAuthenticationFilter} 不可单独声明为 {@code @Bean}，
     * 否则 Spring Boot 会将其注册为独立 Servlet Filter，导致 SecurityManager 未绑定。
     *
     * @param securityManager 安全管理器
     * @return Shiro 过滤器工厂
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
         // 注册你自定义的 JSON 认证过滤器
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        filters.put("apiAuthc", new ApiAuthenticationFilter());
        factoryBean.setFilters(filters);
        Map<String, String> chain = new LinkedHashMap<String, String>();
        chain.put("/api/auth/login", "anon");// 登录接口 → 无需登录
        chain.put("/api/health", "anon");// 健康检查 → 无需登录
        chain.put("/api/**", "apiAuthc");// 其他所有接口 → 必须登录
        factoryBean.setFilterChainDefinitionMap(chain);
        return factoryBean;
    }
}
/*apiAuthc
绑定你之前的 ApiAuthenticationFilter
→ 未登录直接返回 JSON 401，不跳转页面*/