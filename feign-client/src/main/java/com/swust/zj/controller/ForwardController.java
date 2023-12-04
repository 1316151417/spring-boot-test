package com.swust.zj.controller;

import com.swust.zj.client.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forward")
public class ForwardController {

    @Autowired
    private TestClient testClient;

    @GetMapping("/hello")
    Object hello() {
        return testClient.hello("World");
    }

}
