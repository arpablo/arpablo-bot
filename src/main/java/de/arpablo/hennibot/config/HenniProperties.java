package de.arpablo.hennibot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix="henni", ignoreUnknownFields=false)
@Data
public class HenniProperties {
    private String appSecret;
    private String verifyToken;
    private String pageAccessToken;
}