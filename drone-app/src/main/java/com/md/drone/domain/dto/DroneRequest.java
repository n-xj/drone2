package com.md.drone.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 创建/更新无人机的 API 请求体。
 * 这个类就是前端调用新增 / 修改无人机接口时，传过来的参数格式，专门用来接收前端数据。
 */
public class DroneRequest {

    @NotBlank
    @Size(max = 200)
    private String name;
    @Size(max = 100)
    private String model;
    @Size(max = 100)
    private String serialNo;
    @Size(max = 2000)
    private String remark;
    private Boolean regenerateAttributes;

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
     * @return 为 true 时在更新流程中再次调用 AI 覆盖属性
     */
    public Boolean getRegenerateAttributes() {
        return regenerateAttributes;
    }

    /**
     * @param regenerateAttributes 为 true 时在更新流程中再次调用 AI 覆盖属性
     */
    public void setRegenerateAttributes(Boolean regenerateAttributes) {
        this.regenerateAttributes = regenerateAttributes;
    }
}
//在控制器里面使用