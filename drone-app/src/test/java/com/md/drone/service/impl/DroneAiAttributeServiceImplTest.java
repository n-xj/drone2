package com.md.drone.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.md.drone.domain.Drone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import org.springframework.http.HttpStatus;

/**
 * Mock 与 HTTP 模式下应返回可解析 JSON 或透传 HTTP 体。
 */
class DroneAiAttributeServiceImplTest {

    private DroneAiAttributeServiceImpl service;
    private RestTemplate restTemplate;

    /**
     * 构造带 mock 模式的服务实例（不启动 Spring 容器）。
     */
    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        service = new DroneAiAttributeServiceImpl(new ObjectMapper(), restTemplate);
        ReflectionTestUtils.setField(service, "mode", "mock");
    }

    @Test
    void should_return_mock_json_when_mode_mock() {
        Drone d = new Drone();
        d.setName("X1");
        d.setModel("A");
        String json = service.generateJsonAttributes(d);
        assertTrue(json.contains("mock"));
        assertTrue(json.contains("X1"));
    }

    @Test
    void should_fallback_to_mock_when_http_mode_but_url_empty() {
        ReflectionTestUtils.setField(service, "mode", "http");
        ReflectionTestUtils.setField(service, "httpUrl", "");
        Drone d = new Drone();
        d.setName("N");
        String json = service.generateJsonAttributes(d);
        assertTrue(json.contains("mock"));
    }

    @Test
    void should_use_mock_for_unknown_mode() {
        ReflectionTestUtils.setField(service, "mode", "other");
        Drone d = new Drone();
        d.setName("N");
        assertTrue(service.generateJsonAttributes(d).contains("mock"));
    }

    @Test
    void should_return_body_when_http_ok() {
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        String url = "https://ai.example/attrs";
        server.expect(requestTo(url))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType("application/json"))
                .andRespond(withSuccess("{\"ok\":true}", MediaType.APPLICATION_JSON));
        ReflectionTestUtils.setField(service, "mode", "http");
        ReflectionTestUtils.setField(service, "httpUrl", url);
        Drone d = new Drone();
        d.setName("n");
        d.setModel("m");
        assertEquals("{\"ok\":true}", service.generateJsonAttributes(d));
        server.verify();
    }

    @Test
    void should_send_bearer_when_configured() {
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        String url = "https://ai.example/attrs2";
        server.expect(requestTo(url))
                .andExpect(header("Authorization", "Bearer t"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));
        ReflectionTestUtils.setField(service, "mode", "http");
        ReflectionTestUtils.setField(service, "httpUrl", url);
        ReflectionTestUtils.setField(service, "httpBearer", "t");
        Drone d = new Drone();
        d.setName("a");
        service.generateJsonAttributes(d);
        server.verify();
    }

    @Test
    void should_throw_when_http_non2xx() {
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        String url = "https://ai.example/attrs3";
        server.expect(requestTo(url))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
        ReflectionTestUtils.setField(service, "mode", "http");
        ReflectionTestUtils.setField(service, "httpUrl", url);
        Drone d = new Drone();
        d.setName("a");
        assertThrows(IllegalStateException.class, () -> service.generateJsonAttributes(d));
        server.verify();
    }
}
