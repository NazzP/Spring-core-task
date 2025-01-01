package org.example.gymcrmsystem.config;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@PropertySource("classpath:application.properties")
public class TomcatConfig {

    @Value("${server.port}")
    private int port;

    @Bean
    public Tomcat tomcat() {
        Tomcat tomcat = new Tomcat();

        Connector connector = new Connector();
        connector.setPort(port);
        connector.setProperty("protocol", "HTTP/1.1");
        tomcat.getService().addConnector(connector);
        tomcat.setConnector(connector);

        System.out.println("Starting Tomcat on port " + port);

        try {
            AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
            context.register(AppConfig.class);

            DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
            String servletName = "dispatcher";

            Context tomcatContext = tomcat.addContext("", null);
            Tomcat.addServlet(tomcatContext, servletName, dispatcherServlet);

            tomcatContext.addServletMappingDecoded("/*", servletName);
            System.out.println("Mapped servlet to /*");

            tomcat.getServer().addLifecycleListener(event -> {
                if (Lifecycle.START_EVENT.equals(event.getType())) {
                    System.out.println("Tomcat server started successfully!");
                } else if (Lifecycle.STOP_EVENT.equals(event.getType())) {
                    System.out.println("Tomcat server stopped.");
                }
            });
        } catch (Exception e) {
            System.err.println("Error during Tomcat setup: " + e.getMessage());
            e.printStackTrace();
        }

        return tomcat;
    }
}

