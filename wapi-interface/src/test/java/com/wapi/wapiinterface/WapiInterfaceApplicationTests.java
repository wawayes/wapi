package com.wapi.wapiinterface;

import com.wapi.wapiclientsdk.client.UserClient;
import com.wapi.wapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class WpiInterfaceApplicationTests {

	@Resource
	private UserClient userClient;

	@Test
	void contextLoads() {
		String result = userClient.getNameByGet("wmy");
		String result2 = userClient.getNameByPost("wmy");
		String result3 = userClient.getUsernameByPost(new User("wmy"));
		System.out.println(result);
		System.out.println(result2);
		System.out.println(result3);
	}

}