package org.example.gymcrmsystem;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.example.gymcrmsystem.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class GymCrmSystemApplication {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            Tomcat tomcat = context.getBean(Tomcat.class);
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            log.error("Error occurred while starting Tomcat server", e);
        }
    }
}
