package org.example.gymcrmsystem.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Profile("test")
@EnableTransactionManagement
@TestPropertySource(locations = "classpath:application-test.properties")
public class JpaTestConfig {

    @Value("${test.datasource.url}")
    private String datasourceUrl;

    @Value("${test.datasource.username}")
    private String datasourceUsername;

    @Value("${test.datasource.password}")
    private String datasourcePassword;

    @Value("${test.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${test.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${test.hibernate.show_sql}")
    private String showSql;

    @Value("${test.hibernate.format_sql}")
    private String formatSql;

    @Value("${test.hibernate.jdbc.lob.non_contextual_creation}")
    private String lobCreation;

    @Value("${test.jakarta.persistence.sql-load-script-source}")
    private String loadScriptSource;

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("org.example.gymcrmsystem.entity");
        em.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
        em.setJpaProperties(getJpaProperties());
        em.afterPropertiesSet();

        return em.getObject();
    }

    @Bean
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public TransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(datasourceUrl);
        dataSource.setUsername(datasourceUsername);
        dataSource.setPassword(datasourcePassword);
        return dataSource;
    }

    private Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.format_sql", formatSql);
        properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", lobCreation);
        properties.setProperty("jakarta.persistence.sql-load-script-source", loadScriptSource);
        return properties;
    }
}
