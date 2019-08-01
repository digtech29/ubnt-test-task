package com.ubnt.testTask;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Spring configuration file for embeded server
 *
 */
public class EmbededServerConfig extends BaseConfig {

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
        configurer.setLocations(new Resource[] { new ClassPathResource("embeded-server.properties") });
        return configurer;
    }

    /**
     * @return HttpApi server bean
     */
    @Bean
    public HttpApiServer httpApiServer() {
        return new HttpApiServer();
    }

}
