package com.example.seed.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置
 * @author Fururur
 * @date 2020/6/9-14:04
 */
@Slf4j
@Configuration
public class DataSourceConfiguration {

    @Bean(name = "HZDataSource")
    @Primary
    @Qualifier("HZDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hangzhou")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name = "SHDataSource")
    @Qualifier("SHDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.shanghai")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }
}
