package com.example.feignmockclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mock_config")
public class MockConfig {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 发起方服务
     */
    private String initiatorService;

    /**
     * 目标服务
     */
    private String targetService;

    /**
     * 服务资源
     */
    private String uri;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTargetService() {
        return targetService;
    }

    public void setTargetService(String targetService) {
        this.targetService = targetService;
    }

    public String getInitiatorService() {
        return initiatorService;
    }

    public void setInitiatorService(String initiatorService) {
        this.initiatorService = initiatorService;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public LocalDateTime getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(LocalDateTime crtTime) {
        this.crtTime = crtTime;
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
