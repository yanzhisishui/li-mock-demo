package com.example.feignmockclient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("")
public class MockConfig {
    @TableId(type = IdType.AUTO)
    private Integer id;
}
