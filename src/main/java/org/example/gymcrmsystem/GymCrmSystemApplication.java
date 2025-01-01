package org.example.gymcrmsystem;


import org.apache.catalina.startup.Tomcat;
import org.example.gymcrmsystem.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GymCrmSystemApplication {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // Retrieve Tomcat bean and start it
            Tomcat tomcat = context.getBean(Tomcat.class);
            System.out.println("Gym CRM System application started.");

            // Start Tomcat and keep it running
            tomcat.start();
            tomcat.getServer().await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

