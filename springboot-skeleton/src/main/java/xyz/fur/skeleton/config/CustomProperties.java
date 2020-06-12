package xyz.fur.skeleton.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置文件读取
 * 目前@PropertySource注解暂时仅支持自定义的properties文件
 * @author Fururur
 * @create 2019-07-22-15:45
 */
@Data
@Component
@ConfigurationProperties(prefix = "meta")
public class CustomProperties {
    private String imageSavePath;
    private Integer maxImageSize;
}
