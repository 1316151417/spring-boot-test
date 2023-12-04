package com.swust.zj.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "feign-server", path = "/test")
public interface TestClient {

    @GetMapping("/hello")
    String hello(@RequestParam String name);

}
