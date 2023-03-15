package com.wapi.wapiclientsdk;

import com.wapi.wapiclientsdk.client.UserClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("wapi.client")
@Data
@ComponentScan
public class WApiClientConfig {
    private String accessKey;

    private String secretKey;

    @Bean
    public UserClient userClient() {
        return new UserClient(accessKey, secretKey);
    }
}
