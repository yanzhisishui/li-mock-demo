package com.example.feignmockclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.feignmockclient.entity.MockConfig;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.springframework.stereotype.Component;

@Component
public interface  MockConfigMapper extends BaseMapper<MockConfig> {
}
