package com.example.feignmockclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.feignmockclient.entity.MockConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface  MockConfigMapper extends BaseMapper<MockConfig> {
}
