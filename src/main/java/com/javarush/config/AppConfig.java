package com.javarush.config;


import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.EntityManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Environment;
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


    @Value("${session-factory.package.scan}") String packageToScan;

    @Bean
    public LocalSessionFactoryBean sessionFactoryBean(DataSource dataSource, Properties hibernatePro){
        LOGGER.info("Factory Bean is about to be created");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        LOGGER.info("Factory Bean is created");
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(packageToScan);
        sessionFactory.setHibernateProperties(hibernatePro);
        return sessionFactory;
    }


    @Bean(name = "hibernatePro")
    public Properties hibernateProperties(@Value("${hibernate.dialect}") String dialect,
                                          @Value("${hibernate.p6spy.engine.spy}") String driver,
                                          @Value("${hibernate.HBM2DDL_AUTO}") String hbm2ddl){
        Properties properties = new Properties();
        LOGGER.info("Hibernate Properties Bean is created");
        properties.put(Environment.DIALECT, dialect);
        properties.put(Environment.DRIVER, driver);
        properties.put(Environment.HBM2DDL_AUTO, hbm2ddl);
        return properties;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource(@Value("${spring.datasource.hikari.driver-class-name}") String drName,
                                 @Value("${spring.datasource.hikari.jdbc-url}") String url,
                                 @Value("${spring.datasource.hikari.username}") String userName,
                                 @Value("${spring.datasource.hikari.password}") String password,
                                 @Value("${spring.datasource.hikari.maximum-pool_size}") int poolSize) {
        HikariDataSource dataSource = new HikariDataSource();
        LOGGER.info("HikariDataSource Bean is created");
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
