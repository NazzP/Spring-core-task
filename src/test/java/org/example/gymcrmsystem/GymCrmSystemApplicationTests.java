package org.example.gymcrmsystem;

import org.example.gymcrmsystem.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class GymCrmSystemApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        String[] expectedBeans = {
                "traineeStorage", "trainerStorage", "trainingStorage", "appConfig",
                "jacksonConfig", "objectMapper", "requestMappingHandlerAdapter",
                "mappingJackson2HttpMessageConverter", "tomcatConfig", "tomcat",
                "traineeController", "trainerController", "trainingController",
                "globalExceptionHandler", "traineeFacadeImpl", "trainerFacadeImpl",
                "trainingFacadeImpl", "traineeMapper", "trainerMapper", "trainingMapper",
                "traineeRepositoryImpl", "trainerRepositoryImpl", "trainingRepositoryImpl",
                "trainerServiceImpl", "trainingServiceImpl", "usernameGenerator", "jsonStorageParser"
        };

        for (String bean : expectedBeans) {
            assertTrue(applicationContext.containsBean(bean), "Bean not found: " + bean);
        }
    }
}