package com.example.feignmockclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * mock 配置项
 * */
@Data
@TableName("mock_config_item")
public class MockConfigItem {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 配置 id
     * */
    private Integer configId;
    /**
     * 0:禁用 1:启用
     * */
    private Integer status;
    /**
     * 表达式集合 且
     * */
    private String expressionListStr;
    private String mockResponse;

    /**
     * 创建者
     */
    private String creator;

    private LocalDateTime crtTime;
    private LocalDateTime uptTime;

    @TableLogic
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(LocalDateTime crtTime) {
        this.crtTime = crtTime;
    }

    public String getExpressionListStr() {
        return expressionListStr;
    }

    public void setExpressionListStr(String expressionListStr) {
        this.expressionListStr = expressionListStr;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMockResponse() {
        return mockResponse;
    }

    public void setMockResponse(String mockResponse) {
        this.mockResponse = mockResponse;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getUptTime() {
        return uptTime;
    }

    public void setUptTime(LocalDateTime uptTime) {
        this.uptTime = uptTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
