package com.example.feignmockclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.feignmockclient.entity.MockConfig;
import com.example.feignmockclient.entity.MockConfigItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MockConfigItemMapper extends BaseMapper<MockConfigItem> {
}
