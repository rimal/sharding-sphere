package com.example.shardingsphere;

import com.example.shardingsphere.repository.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan("com.example.shardingsphere.model")
@EnableJpaRepositories(
    basePackages = "com.example.shardingsphere.repository",
    repositoryBaseClass = BaseRepositoryImpl.class)
public class ShardingSphereApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShardingSphereApplication.class, args);
  }

}
