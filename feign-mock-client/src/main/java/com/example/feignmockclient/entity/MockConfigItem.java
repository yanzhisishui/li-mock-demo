package com.example.feignmockclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * mock 配置项
 * */
@Data
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
}
