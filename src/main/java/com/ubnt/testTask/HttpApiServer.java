package com.ubnt.testTask;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.ubnt.testTask.utils.ApplicationContextHolder;

/**
 * Used for tests or for standalone application without servlet container
 *
 */
public class HttpApiServer {

    private static final String CONTROLLERS_PATH = com.ubnt.testTask.BaseConfig.class.getPackage().getName();

    private static final Logger log = Logger.getLogger(HttpApiServer.class);

    private Server jettyServer;

    @Value("${embedded-server-port:8080}")
    private int embeddedServerPort;

    @Autowired
    private ApplicationContext appContext;

    public void start() {
        try {
            log.info("---=== HttpApiServer Starting on port " + embeddedServerPort + " ===---");
            ApplicationContextHolder.setAppContext(appContext);
            final ResourceConfig rc = new ResourceConfig().packages(CONTROLLERS_PATH);
            rc.property("contextConfig", appContext);
            jettyServer = new Server(embeddedServerPort);
            ServletContextHandler restServicesContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
            restServicesContextHandler.setContextPath("/api/");
            jettyServer.setHandler(restServicesContextHandler);
            ServletHolder servletHolder = restServicesContextHandler.addServlet(ServletContainer.class, "/*");
            servletHolder.setInitOrder(0);
            servletHolder.setInitParameter("jersey.config.server.provider.packages", CONTROLLERS_PATH + ",org.codehaus.jackson.jaxrs");
            servletHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");
            servletHolder.setInitParameter(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, "true");
            servletHolder.setInitParameter(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, "true");
            jettyServer.start();
            log.info("HttpApiServer running!");
        } catch (Exception e) {
            log.error("Error while starting HttpApiServer: " + e.getMessage(), e);
            System.exit(0);
        }
    }

}
