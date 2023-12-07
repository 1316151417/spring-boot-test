package com.swust.zj.feign;

import com.swust.zj.decoder.JsonDecoder;
import com.swust.zj.encoder.JsonEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyFeignInvocationHandler implements InvocationHandler {

    private JsonEncoder jsonEncoder = new JsonEncoder();
    private JsonDecoder jsonDecoder = new JsonDecoder();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> clazz = proxy.getClass().getInterfaces()[0];
        Class<?> returnType = method.getReturnType();
        FeignClient feignClientAnnotation = clazz.getAnnotation(FeignClient.class);
        PostMapping postMappingAnnotation = method.getAnnotation(PostMapping.class);
        String name = feignClientAnnotation.name();
        String uri = postMappingAnnotation.value()[0];
        String baseUrl = "http://" + name;
        URL url = new URL(baseUrl + uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        // 输出流
        for (int i = 0; i < method.getParameterCount(); i++) {
            Parameter parameter = method.getParameters()[i];
            if (parameter.isAnnotationPresent(RequestBody.class)) {
                continue;
            }
            Object arg = args[i];
            byte[] outputData = jsonEncoder.encode(arg);
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(outputData, 0, outputData.length);
            }
            break;
        }
        byte[] inputData;
        try (InputStream inputStream = connection.getInputStream()) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            inputData = buffer.toByteArray();
        }
        connection.disconnect();
        return jsonDecoder.decode(inputData, returnType);
    }

}
