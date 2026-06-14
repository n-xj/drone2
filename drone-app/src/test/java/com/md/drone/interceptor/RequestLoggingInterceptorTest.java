package com.md.drone.interceptor;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 请求拦截器不解析请求体、仅记录安全字段。
 */
class RequestLoggingInterceptorTest {

    @Test
    void should_proceed_and_log_when_info_enabled() {
        RequestLoggingInterceptor interceptor = new RequestLoggingInterceptor();
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/drone");
        MockHttpServletResponse res = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(req, res, this));
    }

    @Test
    void should_return_true_without_logging_when_info_disabled() {
        Logger log = (Logger) LoggerFactory.getLogger(RequestLoggingInterceptor.class);
        Level previous = log.getLevel();
        log.setLevel(Level.ERROR);
        try {
            RequestLoggingInterceptor interceptor = new RequestLoggingInterceptor();
            assertTrue(interceptor.preHandle(
                    new MockHttpServletRequest("GET", "/x"), new MockHttpServletResponse(), this));
        }
        finally {
            log.setLevel(previous);
        }
    }

    @Test
    void should_mask_password_parameter_name() {
        RequestLoggingInterceptor interceptor = new RequestLoggingInterceptor();
        MockHttpServletRequest req = new MockHttpServletRequest("POST", "/login");
        req.addParameter("name", "a");
        req.addParameter("password", "s");
        assertTrue(interceptor.preHandle(req, new MockHttpServletResponse(), this));
    }
}
