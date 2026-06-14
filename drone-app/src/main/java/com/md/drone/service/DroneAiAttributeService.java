package com.md.drone.service;

import com.md.drone.domain.Drone;

/**
 * 为无人机根据业务字段生成可持久化的 JSON 属性（可对接外部 AI 或本地 Mock）。
 */
public interface DroneAiAttributeService {

    /**
     * 基于当前实体中名称、型号、序列号等生成一条 JSON 文本。
     *
     * @param context 已填充基础字段的实体（id 可空）
     * @return 合法 JSON 字符串
     * @throws IllegalStateException 配置错误或远程调用不可用时
     */
    String generateJsonAttributes(Drone context);
}
