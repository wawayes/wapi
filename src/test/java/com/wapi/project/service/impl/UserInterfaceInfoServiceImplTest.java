package com.wapi.project.service.impl;

import com.wapi.project.service.UserInterfaceInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
public class UserInterfaceInfoServiceImplTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void testInvokeCount(){
        boolean res = userInterfaceInfoService.invokeCount(3,2);
        System.out.println(res);
    }

}