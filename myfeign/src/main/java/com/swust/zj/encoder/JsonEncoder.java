package com.swust.zj.encoder;

import com.swust.zj.util.JsonUtil;

import java.nio.charset.StandardCharsets;

public class JsonEncoder {

    public byte[] encode(Object obj) {
        return JsonUtil.toJsonString(obj).getBytes(StandardCharsets.UTF_8);
    }

}
