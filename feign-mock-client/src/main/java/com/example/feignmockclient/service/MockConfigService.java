package com.example.feignmockclient.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.feignmockclient.entity.MockConfig;
import com.example.feignmockclient.entity.MockConfigItem;
import com.example.feignmockclient.mapper.MockConfigItemMapper;
import com.example.feignmockclient.mapper.MockConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockConfigService {

    @Value("${spring.application.name}")
    public String CURRENT_SERVICE_NAME;


    @Autowired
    private MockConfigMapper mockConfigMapper;
    @Autowired
    private MockConfigItemMapper mockConfigItemMapper;
    public MockConfig getMockConfig(String initiatorService,String targetService,List<String> uriList) {
       return mockConfigMapper.selectOne(Wrappers.<MockConfig>lambdaQuery().eq(MockConfig::getInitiatorService,initiatorService)
                .eq(MockConfig::getTargetService,targetService)
                .in(MockConfig::getUri,uriList));
    }

    public List<MockConfigItem> queryOpenMockConfigItemList(Integer id) {
        return mockConfigItemMapper.selectList(Wrappers.<MockConfigItem>lambdaQuery()
                .eq(MockConfigItem::getConfigId,id)
                .eq(MockConfigItem::getStatus,1).last(" order by id desc"));
    }

    public MockConfigItem getMockConfigItem(int id) {
        return mockConfigItemMapper.selectById(id);
    }
}
