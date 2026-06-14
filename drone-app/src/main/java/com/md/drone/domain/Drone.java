package com.md.drone.domain;

import java.time.LocalDateTime;

/**
 * 无人机领域实体，对应表 {@code t_drone}；{@code attributesJson} 由 AI 服务生成后持久化。
 */
public class Drone {

    private Long id;
    private String name;
    private String model;
    private String serialNo;
    private String attributesJson;
    private String remark;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * @return 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 型号
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model 型号
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return 序列号
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * @param serialNo 序列号
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * @return JSON 形态的设备属性
     */
    public String getAttributesJson() {
        return attributesJson;
    }

    /**
     * @param attributesJson JSON 形态的设备属性
     */
    public void setAttributesJson(String attributesJson) {
        this.attributesJson = attributesJson;
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return 创建时间
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt 创建时间
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return 最近更新时间
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt 最近更新时间
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
