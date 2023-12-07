package com.swust.zj.api;

import com.swust.zj.req.OtherServiceReq;
import com.swust.zj.res.OtherServiceRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "other.service.name")
public interface OtherServiceApi {

    @PostMapping("/uri")
    OtherServiceRes method(@RequestBody OtherServiceReq req);

}
