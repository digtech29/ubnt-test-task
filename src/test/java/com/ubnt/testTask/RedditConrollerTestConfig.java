package com.ubnt.testTask;

import javax.sql.DataSource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.commons.dbcp.BasicDataSource;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ubnt.testTask.dao.RedditMessageDao;

public class RedditConrollerTestConfig {

    public static final String BIND_ADDRESS = "0.0.0.0";

    @Value("${db.driver:org.hsqldb.jdbcDriver}")
    private String dbDriver;

    @Value("${db.url:jdbc:hsqldb:mem:testdb}")
    private String dbUrl;

    @Value("${db.username:sa}")
    private String dbLogin;

    @Value("${db.password:}")
    private String dbPassword;

    @Bean
    public static BeanFactoryPostProcessor getPP() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setLocations(new Resource[] { new ClassPathResource("embeded-server.properties") });
        return configurer;
    }

    @Bean(initMethod="start")
    public HttpApiServer httpApiServer() {
        return  new HttpApiServer();
    }

    @Bean
    public Client getRestClient() {
        ClientConfig clientConfig = new ClientConfig();
        Client restClient = ClientBuilder.newClient(clientConfig);
        return restClient;
    }

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

    @Bean
    public RedditMessageDao redditMessageDao() {
        return new RedditMessageDao();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
