package com.example.feignmockclient.client;


import com.example.feignmockclient.client.request.RcsApplyRequest;
import com.example.feignmockclient.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "feign-mock-server",configuration = {
         FeignConfiguration.class
})
public interface FeignMockServerClient {
    @GetMapping("/rcs/apply/query")
    Object rcsApplyQuery(@RequestParam("applyNo") String applyNo,@RequestParam("memberId") String memberId);

    @PostMapping("/rcs/apply")
    Object rcsApply(@RequestBody RcsApplyRequest request);

    @GetMapping("/rcs/apply/query-map")
    Object rcsApplyQueryMap(@SpringQueryMap RcsApplyRequest request);

    @GetMapping(value = {"/rcs/apply/{applyNo}/{testParam}"})
    Object rcsApplyQueryPathVariable(@PathVariable String applyNo,@PathVariable String testParam);


}
