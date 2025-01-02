package org.example.gymcrmsystem.config;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class TomcatConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatConfig.class);

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

        LOGGER.info("Configuring Tomcat to run on port {}", port);

        try {
            AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
            context.register(AppConfig.class);

            DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
            String servletName = "dispatcher";

            Context tomcatContext = tomcat.addContext("", null);
            Tomcat.addServlet(tomcatContext, servletName, dispatcherServlet);

            tomcatContext.addServletMappingDecoded("/*", servletName);
            LOGGER.info("Mapped servlet '{}' to /*", servletName);

            tomcat.getServer().addLifecycleListener(event -> {
                if (Lifecycle.START_EVENT.equals(event.getType())) {
                    LOGGER.info("Tomcat server started successfully!");
                } else if (Lifecycle.STOP_EVENT.equals(event.getType())) {
                    LOGGER.info("Tomcat server stopped.");
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error during Tomcat setup: {}", e.getMessage(), e);
        }

        return tomcat;
    }
}