package com.example.app.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件读取
 * @author Fururur
 * @create 2019-07-22-15:45
 */
@Data
@Component

@ConfigurationProperties(prefix = "meta")
public class PropertiesConfig {

}
