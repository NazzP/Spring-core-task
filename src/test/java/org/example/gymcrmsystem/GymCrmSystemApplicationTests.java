package org.example.gymcrmsystem;

import org.example.gymcrmsystem.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class GymCrmSystemApplicationTests {

    @Test
    void contextLoads() {

    }
}
