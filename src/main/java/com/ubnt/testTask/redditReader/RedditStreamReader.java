package com.ubnt.testTask.redditReader;

import java.io.InputStreamReader;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.ubnt.testTask.dao.RedditMessageDao;

/**
 * @author dmitogib
 *
 */
public class RedditStreamReader extends Thread {

    public static Logger log = LoggerFactory.getLogger(RedditStreamReader.class);

    @Value("${reddit-stream-url:http://stream.pushshift.io/}")
    private String redditStreamUrl;

    @Autowired
    private RedditMessageDao storage;

    private boolean terminated = false;

    @PostConstruct
    public void init() {
        log.info("Reddit stream reader started. URL " + redditStreamUrl);
        setDaemon(true);
        this.start();
    }

    @PreDestroy
    public void destroy() {
        terminated = true;
        log.info("Reddit stream reader teminated.");
    }

    @Override
    public void run() {
        while (!terminated) {

            try {
                URL url = new URL(redditStreamUrl);
                InputStreamReader isReader = new InputStreamReader(url.openStream());
                RedditMessageReader in = new RedditMessageReader(isReader);
                RedditMessage message;
                while ((message = in.readMessage()) != null) {
                    log.debug("Message received: " + message);
                    storage.save(message);
                }
                in.close();
            } catch (Exception e) {
                log.error("Error read input stream, Reconnection after 5 sec", e);
            }
            // Sleep and reconnect after 5 sec
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
               // ignored
            }
        }
    }

}
