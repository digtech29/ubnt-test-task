package com.ubnt.testTask.redditReader;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author dmitogib
 *
 */
public class RedditMessagePayload {

    private String body;

    private String permalink;

    @JsonProperty("created_utc")
    private Long createdUtc;

    @JsonProperty("total_awards_received")
    private int totalAwardsReceived;

    @JsonProperty("subreddit_id")
    private String subredditId;

    @JsonProperty("subreddit")
    private String subreddit;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public int getTotalAwardsReceived() {
        return totalAwardsReceived;
    }

    public void setTotalAwardsReceived(int totalAwardsReceived) {
        this.totalAwardsReceived = totalAwardsReceived;
    }

    public Long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(Long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    @Override
    public String toString() {
        return "RedditMessagePayload [body=" + body + ", permalink=" + permalink + ", totalAwardsReceived=" + totalAwardsReceived + "]";
    }

}
