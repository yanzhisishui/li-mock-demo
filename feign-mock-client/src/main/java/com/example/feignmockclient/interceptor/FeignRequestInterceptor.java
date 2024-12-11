package com.example.feignmockclient.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** Feign 调用的时候传token到下游 */
public class FeignRequestInterceptor implements RequestInterceptor {
  @Override
  public void apply(RequestTemplate template) {
    // 从header获取X-token
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    ServletRequestAttributes attr = (ServletRequestAttributes) requestAttributes;
    HttpServletRequest request = attr.getRequest();
    String token = request.getHeader("x-auth-token");//网关传过来的 token
    if (StringUtils.hasText(token)) {
      template.header("X-AUTH-TOKEN", token);
    }

    String name = template.feignTarget().name(); //feign-mock-server
    String uri = template.path();                // /rcs/apply/query
    //请求参数


  }
}
