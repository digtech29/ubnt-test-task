package com.ubnt.testTask.utils;

import org.springframework.context.ApplicationContext;

import com.ubnt.testTask.dao.RedditMessageDao;

/**
 * Helper class for hold applicationContext object, because of integration problem with Jersey.
 *
 */
public class ApplicationContextHolder {

    private static ApplicationContext appContext;

    /**
     * @return Spring ApplicationContext
     */
    public static ApplicationContext getAppContext() {
        return appContext;
    }

    /**
     * @param appContext
     */
    public static void setAppContext(ApplicationContext appContext) {
        ApplicationContextHolder.appContext = appContext;
    }

    /**
     * @return RedditMessageDao
     */
    public static RedditMessageDao getRedditMessageDao() {
        return appContext.getBean(RedditMessageDao.class);
    }

}
