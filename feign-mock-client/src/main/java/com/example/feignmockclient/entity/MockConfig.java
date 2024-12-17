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
}
