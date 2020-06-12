package com.example.seed.common.config;

import com.example.seed.common.repo.base.IBaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

/**
 * JPA集成配置 - 数据源1
 *
 * @author Fururur
 * @date 2020/6/9-14:21
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryHZ",
        transactionManagerRef = "transactionManagerHZ",
        basePackages = {"com.example.seed.common.repo.hz"},
        repositoryBaseClass = IBaseRepositoryImpl.class)
public class RepositoryHZConfig {
    private final DataSource HZDataSource;
    private final JpaProperties jpaProperties;
    private final HibernateProperties hibernateProperties;

    public RepositoryHZConfig(@Qualifier("HZDataSource") DataSource HZDataSource, JpaProperties jpaProperties, HibernateProperties hibernateProperties) {
        this.HZDataSource = HZDataSource;
        this.jpaProperties = jpaProperties;
        this.hibernateProperties = hibernateProperties;
    }

    @Primary
    @Bean(name = "entityManagerFactoryHZ")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryHZ(EntityManagerFactoryBuilder builder) {
        // springboot 2.x
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        return builder.dataSource(HZDataSource)
                .properties(properties)
                .packages("com.example.seed.common.entity")
                .persistenceUnit("HZPersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "transactionManagerHZ")
    public PlatformTransactionManager transactionManagerHZ(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryHZ(builder).getObject());
    }

}
