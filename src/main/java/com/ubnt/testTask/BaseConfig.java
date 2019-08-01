package com.ubnt.testTask;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ubnt.testTask.dao.RedditMessageDao;
import com.ubnt.testTask.redditReader.RedditStreamReader;

/**
 * @author
 * Spring configuration file
 *
 */
@Configuration
@ComponentScan(basePackages="com.ubnt.testTask.controllers")
public class BaseConfig {

    @Value("${db.driver:org.hsqldb.jdbcDriver}")
    private String dbDriver;

    @Value("${db.url:jdbc:hsqldb:./db/redditdb}")
    private String dbUrl;

    @Value("${db.username:sa}")
    private String dbLogin;

    @Value("${db.password:}")
    private String dbPassword;

    /**
     * @return Property placeholder configurer, for configuration from wabApp context file
     */
    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        return configurer;
    }


    /**
     * @return Reddit stream reader 
     */
    @Bean
    public RedditStreamReader redditStreamReader() {
        return new RedditStreamReader();
    }

    /**
     * @return Data source
     */
    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds = new BasicDataSource();
        ds.setDriverClassName(dbDriver);
        ds.setUrl(dbUrl);
        ds.setUsername(dbLogin);
        ds.setPassword(dbPassword);
        ds.setInitialSize(5);
        ds.setMaxActive(5);
        ds.setMaxIdle(5);
        return ds;
    }

    /**
     * @return RedditMessageDao
     */
    @Bean
    public RedditMessageDao redditMessageDao() {
        return new RedditMessageDao();
    }

    /**
     * @return Spring JDBC template
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
