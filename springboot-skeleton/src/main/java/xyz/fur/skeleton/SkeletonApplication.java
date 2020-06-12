package xyz.fur.skeleton;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import xyz.fur.skeleton.repo.base.BaseRepositoryImpl;

import java.util.TimeZone;

@ServletComponentScan
@EnableJpaAuditing
@EnableScheduling
@EnableAsync
@EnableMongoRepositories(basePackages = "xyz.fur.skeleton.repo.mongo")
@EnableJpaRepositories(basePackages = "xyz.fur.skeleton.repo.jpa",
        repositoryBaseClass = BaseRepositoryImpl.class)
@SpringBootApplication
public class SkeletonApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(SkeletonApplication.class, args);
    }
}
