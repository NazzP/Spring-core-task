package org.example.gymcrmsystem.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "org.example.gymcrmsystem")
@PropertySource("classpath:application.properties")
public class AppConfig {

}
