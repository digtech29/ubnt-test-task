package com.ubnt.testTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Embedded HTTP server launcher class
 *
 */
public class EmbededServer {

    public static Logger log = LoggerFactory.getLogger(EmbededServer.class);

    private ApplicationContext context;

    public EmbededServer() {
        super();
        context = new AnnotationConfigApplicationContext(EmbededServerConfig.class);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdownHook()));
    }
    
    public void start(){
        context.getBean(HttpApiServer.class).start();
    }

    public void shutdownHook() {
        log.info("Server is shuting down ...");
    }

    public static void main(String... params) {
        EmbededServer server = new EmbededServer();
        server.start();
    }

}
