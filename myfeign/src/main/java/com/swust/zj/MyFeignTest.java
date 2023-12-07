package com.swust.zj;

import com.swust.zj.api.OtherServiceApi;
import com.swust.zj.feign.MyFeignInvocationHandler;
import com.swust.zj.req.OtherServiceReq;
import com.swust.zj.res.OtherServiceRes;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class MyFeignTest {

    public static void main(String[] args) {
        ClassLoader classLoader = MyFeignTest.class.getClassLoader();
        Class[] interfaces = {OtherServiceApi.class};
        InvocationHandler invocationHandler = new MyFeignInvocationHandler();
        OtherServiceApi feignClient = (OtherServiceApi) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
        OtherServiceRes otherServiceRes = feignClient.method(new OtherServiceReq());
        System.out.println(otherServiceRes);
    }

}
