package com.javarush.config;


import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Environment;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class AppConfig {


    private static final Logger LOGGER = LogManager.getLogger();

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(){
        LOGGER.info("Factory Bean is about to be created");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        LOGGER.info("Factory Bean is created");
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.javarush.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(Environment.HBM2DDL_AUTO, "validate");
        return properties;
    }

    @Value("${spring.datasource.hikari.driver-class-name}") String drName;
    @Value("${spring.datasource.hikari.jdbc-url}") String url;
    @Value("${spring.datasource.hikari.username}") String userName;
    @Value("${spring.datasource.hikari.password}") String password;
    @Value("${spring.datasource.hikari.maximum-pool_size}") int poolSize;
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(drName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(poolSize);
        LOGGER.info("Data source is created");
        return dataSource;
    }



    @Bean
    public PlatformTransactionManager transactionManager (EntityManagerFactory factory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(factory);
        return transactionManager;
    }




}
