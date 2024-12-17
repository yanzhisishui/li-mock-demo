package com.example.feignmockclient.strategy.bo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RequestResolveResponse {
    /**
     * 请求对象 request.param1.param11 这种格式
     * */
    private Map<String, Object> requestBody;

    /**
     * 真实 uri 列表，@PathVariable 的场景
     * */
    private List<String> originUriList;
}
