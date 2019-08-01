package com.ubnt.testTask.redditReader;

/**
 * @author dmitogib
 *
 */
public class RedditMessage {

    private Long id;

    private String eventType;

    private Long timestamp = System.currentTimeMillis();

    private String payload;

    private String subredditId;

    public RedditMessage() {
        super();
    }

    /**
     * @param id
     * @param eventType
     * @param subredditId
     * @param payload
     */
    public RedditMessage(Long id, String eventType, String subredditId, String payload) {
        super();
        this.id = id;
        this.eventType = eventType;
        this.subredditId = subredditId;
        this.payload = payload;    
    }

    /**
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return
     */
    public String getEvent() {
        return eventType;
    }

    /**
     * @param event
     */
    public void setEvent(String event) {
        this.eventType = event;
    }

    /**
     * @return
     */
    public String getData() {
        return payload;
    }

    /**
     * @param data
     */
    public void setData(String data) {
        this.payload = data;
    }

    /**
     * @return
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * @return
     */
    public String getPayload() {
        return payload;
    }

    /**
     * @param payload
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }

    /**
     * @return
     */
    public String getSubredditId() {
        return subredditId;
    }

    /**
     * @param subredditId
     */
    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    @Override
    public String toString() {
        return "RedditMessage [id=" + id + ", eventType=" + eventType + ", payload=" + payload + "]";
    }

}
