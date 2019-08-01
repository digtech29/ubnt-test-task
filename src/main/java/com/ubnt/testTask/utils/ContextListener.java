package com.ubnt.testTask.utils;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class ContextListener extends ContextLoaderListener {

    /**
     * Initialize the root web application context.
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
            super.contextInitialized(event);
            ApplicationContextHolder.setAppContext(getCurrentWebApplicationContext());
    }

}