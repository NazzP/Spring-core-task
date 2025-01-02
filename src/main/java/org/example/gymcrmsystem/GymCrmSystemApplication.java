package org.example.gymcrmsystem;


import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.example.gymcrmsystem.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GymCrmSystemApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GymCrmSystemApplication.class);

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            Tomcat tomcat = context.getBean(Tomcat.class);
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            LOGGER.error("Error occurred while starting Tomcat server", e);
        }
    }
}

