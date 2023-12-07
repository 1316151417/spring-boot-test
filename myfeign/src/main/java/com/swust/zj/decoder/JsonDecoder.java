package com.swust.zj.decoder;

import com.swust.zj.util.JsonUtil;

import java.nio.charset.StandardCharsets;

public class JsonDecoder {

    public Object decode(byte[] bytes, Class<?> clazz) {
        return JsonUtil.toObject(new String(bytes, StandardCharsets.UTF_8), clazz);
    }

}
