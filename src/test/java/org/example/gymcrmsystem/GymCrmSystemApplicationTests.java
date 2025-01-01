package org.example.gymcrmsystem;

import org.example.gymcrmsystem.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class GymCrmSystemApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        System.out.println(Arrays.toString(beanNames));
        assertTrue(applicationContext.containsBean("traineeStorage"));
        assertTrue(applicationContext.containsBean("trainerStorage"));
        assertTrue(applicationContext.containsBean("trainingStorage"));
    }
}
