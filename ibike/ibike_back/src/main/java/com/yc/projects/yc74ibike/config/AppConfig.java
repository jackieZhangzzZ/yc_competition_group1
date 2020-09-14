package com.yc.projects.yc74ibike.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.yc")
@EnableTransactionManagement
public class AppConfig {
}
