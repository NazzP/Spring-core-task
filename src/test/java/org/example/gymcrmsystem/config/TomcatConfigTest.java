package org.example.gymcrmsystem.config;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TomcatConfigTest {

    private TomcatConfig tomcatConfig;

    @BeforeEach
    void setUp() {
        tomcatConfig = new TomcatConfig();
    }

    @Test
    void tomcatBeanShouldBeConfiguredCorrectly() {
        int mockPort = 8080;
        setMockPort(mockPort);

        Tomcat tomcat = tomcatConfig.tomcat();
        assertNotNull(tomcat, "Tomcat bean should not be null");

        Connector connector = tomcat.getConnector();
        assertNotNull(connector, "Connector should not be null");
        assertEquals(mockPort, connector.getPort(), "Connector port should match the configured value");
        assertEquals("HTTP/1.1", connector.getProperty("protocol"), "Connector protocol should be HTTP/1.1");
    }

    private void setMockPort(int port) {
        try {
            var portField = TomcatConfig.class.getDeclaredField("port");
            portField.setAccessible(true);
            portField.set(tomcatConfig, port);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("Failed to set mock port value");
        }
    }
}