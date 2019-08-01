package com.ubnt.testTask.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SubredditActivity {

    private String subredditId;

    private long activity;

    public SubredditActivity() {
        super();
    }

    /**
     * @param subredditId
     * @param activity
     */
    public SubredditActivity(String subredditId, long activity) {
        super();
        this.subredditId = subredditId;
        this.activity = activity;
    }

    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public long getActivity() {
        return activity;
    }

    public void setActivity(long activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "SubredditActivity [subredditId=" + subredditId + ", activity=" + activity + "]";
    }
    
    

}
