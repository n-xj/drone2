package com.md.drone.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.md.drone.domain.Drone;
import com.md.drone.service.DroneAiAttributeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过配置选择 Mock JSON 或 HTTP 调用方式生成 {@code attributes_json} 文本。
 */
@Service
public class DroneAiAttributeServiceImpl implements DroneAiAttributeService {

    private static final Logger LOG = LoggerFactory.getLogger(DroneAiAttributeServiceImpl.class);

    private static final String MODE_MOCK = "mock";
    private static final String MODE_HTTP = "http";

    @Value("${app.ai.mode:mock}")
    private String mode;

    @Value("${app.ai.http-url:}")
    private String httpUrl;

    @Value("${app.ai.http-bearer:}")
    private String httpBearer;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    /**
     * @param objectMapper    JSON
     * @param restTemplate    HTTP 客户端
     */
    public DroneAiAttributeServiceImpl(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateJsonAttributes(Drone context) {
        if (MODE_MOCK.equalsIgnoreCase(mode) || (MODE_HTTP.equalsIgnoreCase(mode) && !StringUtils.hasText(httpUrl))) {
            if (MODE_HTTP.equalsIgnoreCase(mode) && !StringUtils.hasText(httpUrl)) {
                LOG.warn("app.ai.mode=http but app.ai.http-url is empty, falling back to mock");
            }
            return buildMockJson(context);
        }
        if (MODE_HTTP.equalsIgnoreCase(mode)) {
            return callHttpOrThrow(context);
        }
        return buildMockJson(context);
    }

    private String buildMockJson(Drone d) {
        try {
            ObjectNode root = objectMapper.createObjectNode();
            root.put("source", "mock");
            root.put("suggestedMaxAltitudeM", 120);
            root.put("batteryCells", 4);
            root.put("nameHint", d.getName() == null ? "" : d.getName());
            root.put("modelHint", d.getModel() == null ? "" : d.getModel());
            root.put("serialHint", d.getSerialNo() == null ? "" : d.getSerialNo());
            return objectMapper.writeValueAsString(root);
        }
        catch (Exception e) {
            throw new IllegalStateException("Mock JSON 序列化失败", e);
        }
    }

    private String callHttpOrThrow(Drone d) {
        try {
            Map<String, String> body = new HashMap<String, String>();
            body.put("name", d.getName());
            body.put("model", d.getModel());
            body.put("serialNo", d.getSerialNo());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if (StringUtils.hasText(httpBearer)) {
                headers.set("Authorization", "Bearer " + httpBearer);
            }
            HttpEntity<Map<String, String>> entity = new HttpEntity<Map<String, String>>(body, headers);
            ResponseEntity<String> res = restTemplate.exchange(httpUrl, HttpMethod.POST, entity, String.class);
            if (!res.getStatusCode().is2xxSuccessful() || res.getBody() == null) {
                throw new IllegalStateException("AI HTTP 非 2xx 或空体");
            }
            return res.getBody();
        }
        catch (RestClientException e) {
            throw new IllegalStateException("AI HTTP 调用失败: " + e.getMessage(), e);
        }
    }
}
