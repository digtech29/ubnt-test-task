package com.ubnt.testTask.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.ubnt.testTask.entities.EventType;
import com.ubnt.testTask.entities.SubredditActivity;
import com.ubnt.testTask.entities.TimeRange;
import com.ubnt.testTask.redditReader.RedditMessage;

/**
 * Data access object for RedditMessages
 *
 */
public class RedditMessageDao {

    public static Logger log = LoggerFactory.getLogger(RedditMessageDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        try {
            log.info("Initializing RedditMessageDao");
            jdbcTemplate.execute("CREATE TABLE reddit_message "//
                    + "(id BIGINT not null PRIMARY KEY, " //
                    + "created_utc bigint not null , " //
                    + "event_type varchar(12) not null , " //
                    + "subreddit_id varchar(128) not null , " //
                    + "payload LONGVARCHAR );"); //
            jdbcTemplate.execute("CREATE INDEX subreddit_id_idx on reddit_message (event_type,subreddit_id) "); //
        } catch (Exception e) {
            log.info("Look like table already created");
        }
    }

    /**
     * Clean database
     */
    public void truncate() {
        jdbcTemplate.execute("TRUNCATE TABLE reddit_message");
    }

    /**
     * @param RedditMessage
     */
    public void save(RedditMessage message) {
        try {
            jdbcTemplate.update(
                    "insert into reddit_message " //
                            + "(id,created_utc,event_type,subreddit_id,payload) " //
                            + "values " //
                            + "(?,?,?,?,?)", //
                    new Object[] { message.getId(), message.getTimestamp(), message.getEvent(), message.getSubredditId(), message.getData() });
        } catch (Exception e) {
            log.error("Error persist message", e);
        }
    }

    /**
     * @param timeRange
     *            (MIN, MIN_5, HOUR, DAY, ALL_TIME)
     * @param eventType
     *            (EventType.SUBMISSION, EventType.COMMENT)
     * @return activity count
     */
    public Long getActivity(TimeRange timeRange, EventType eventType) {
        long afterTime = System.currentTimeMillis() - timeRange.getValue();
        if (timeRange == TimeRange.ALL_TIME) {
            afterTime = 0;
        }
        return jdbcTemplate.query( //
                "Select count(id) from reddit_message WHERE event_type = ? AND created_utc >= ? ", //
                new Object[] { eventType.toString(), afterTime }, //
                new ActivityResultSetExtractor());

    }

    /**
     * @param timeRange (MIN, MIN_5, HOUR, DAY, ALL_TIME)
     * @return List of top100 SubredditActivity
     */
    public List<SubredditActivity> getTop100(TimeRange timeRange) {
        long afterTime = System.currentTimeMillis() - timeRange.getValue();
        if (timeRange == TimeRange.ALL_TIME) {
            afterTime = 0;
        }
        return jdbcTemplate.query( //
                "Select count(id) activity,subreddit_id from reddit_message WHERE created_utc >= ? group by subreddit_id order by activity DESC LIMIT 100", //
                new Object[] { afterTime }, //
                new SubredditActivityResultSetExtractor());

    }

    /**
     * @param timeRange (MIN, MIN_5, HOUR, DAY, ALL_TIME)
     * @param eventType (EventType.SUBMISSION, EventType.COMMENT)
     * @return
     */
    public List<SubredditActivity> getMostActive(TimeRange timeRange, EventType eventType) {
        long afterTime = System.currentTimeMillis() - timeRange.getValue();
        if (timeRange == TimeRange.ALL_TIME) {
            afterTime = 0;
        }
        return jdbcTemplate.query( //
                "Select count(id) activity,subreddit_id from reddit_message WHERE  event_type = ? AND created_utc >= ? group by subreddit_id order by activity DESC LIMIT 10", //
                new Object[] { eventType.toString(), afterTime }, //
                new SubredditActivityResultSetExtractor());

    }

    class ActivityResultSetExtractor implements ResultSetExtractor<Long> {
        @Override
        public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
            Long count = 0L;
            if (rs.next()) {
                count = rs.getLong(1);
            }
            return count;
        }
    }

    class SubredditActivityResultSetExtractor implements ResultSetExtractor<List<SubredditActivity>> {
        @Override
        public List<SubredditActivity> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<SubredditActivity> top = new ArrayList<>();
            while (rs.next()) {
                top.add(new SubredditActivity(rs.getString(2), rs.getLong(1)));
            }
            return top;
        }
    }

}
