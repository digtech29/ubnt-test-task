package com.ubnt.testTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubnt.testTask.dao.RedditMessageDao;
import com.ubnt.testTask.entities.EventType;
import com.ubnt.testTask.entities.ResponseMessage;
import com.ubnt.testTask.entities.SubredditActivity;
import com.ubnt.testTask.entities.TimeRange;
import com.ubnt.testTask.redditReader.RedditMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RedditConrollerTestConfig.class })
public class RedditControllerTest {

    public static final String BASE_URL = "http://127.0.0.1:8081/api/";

    @Autowired
    public Client restClient;

    @Autowired
    private RedditMessageDao dao;

    private AtomicLong idCounter = new AtomicLong();

    @Before
    public void setUp() throws IOException {
        dao.truncate();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, -10);
        addActivity("top1", c.getTime(), EventType.SUBMISSION);
        c.add(Calendar.SECOND, -10);
        addActivity("top1", c.getTime(), EventType.COMMENT);
        c.add(Calendar.SECOND, -10);
        addActivity("top1", c.getTime(), EventType.COMMENT);
        c.add(Calendar.SECOND, -10);
        addActivity("top1", c.getTime(), EventType.COMMENT);

        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, -10);
        addActivity("top2", c.getTime(), EventType.SUBMISSION);
        c.add(Calendar.SECOND, -10);
        addActivity("top2", c.getTime(), EventType.SUBMISSION);
        c.add(Calendar.SECOND, -10);
        addActivity("top2", c.getTime(), EventType.COMMENT);

    }

    private void addActivity(String subredditId, Date time, EventType eventType) {
        RedditMessage msg = new RedditMessage(idCounter.incrementAndGet(), eventType.toString(), subredditId, "{}");
        msg.setTimestamp(time.getTime());
        dao.save(msg);

    }

    @Test
    public void testTop100() throws Exception {
        String url = BASE_URL + "top100?timeRange=" + TimeRange.MIN.name();
        WebTarget resource = restClient.target(url);
        Response serviceResponse = resource.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get(Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), serviceResponse.getStatus());
        ResponseMessage<ArrayList<SubredditActivity>> responseMessage = serviceResponse.readEntity(new GenericType<ResponseMessage<ArrayList<SubredditActivity>>>() {
        });
        Assert.assertEquals(ResponseMessage.STATUS_OK, responseMessage.getStatus());
        List<SubredditActivity> list = responseMessage.getData();
        Assert.assertEquals("top1", list.get(0).getSubredditId());
        Assert.assertEquals(4, list.get(0).getActivity());
        Assert.assertEquals("top2", list.get(1).getSubredditId());
        Assert.assertEquals(3, list.get(1).getActivity());

    }

    @Test
    public void testSubmissionsActivity() throws Exception {
        String url = BASE_URL + "activity?eventType=" + EventType.SUBMISSION.name() + "&timeRange=" + TimeRange.MIN.name();
        WebTarget resource = restClient.target(url);
        Response serviceResponse = resource.request(MediaType.APPLICATION_JSON).get(Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), serviceResponse.getStatus());
        ResponseMessage<Long> responseMessage = serviceResponse.readEntity(new GenericType<ResponseMessage<Long>>() {
        });
        long count = responseMessage.getData();
        Assert.assertEquals(count, 3l);
    }

    @Test
    public void testCommentsActivity() throws Exception {
        String url = BASE_URL + "activity?eventType=" + EventType.COMMENT.name() + "&timeRange=" + TimeRange.MIN.name();
        WebTarget resource = restClient.target(url);
        Response serviceResponse = resource.request(MediaType.APPLICATION_JSON).get(Response.class);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), serviceResponse.getStatus());
        ResponseMessage<Long> responseMessage = serviceResponse.readEntity(new GenericType<ResponseMessage<Long>>() {
        });
        long count = responseMessage.getData();
        Assert.assertEquals(count, 4l);
    }

    @Test
    public void testCommentsMostActive() throws Exception {
        String url = BASE_URL + "mostActive?eventType=" + EventType.COMMENT.name() + "&timeRange=" + TimeRange.MIN.name();
        WebTarget resource = restClient.target(url);
        Response serviceResponse = resource.request(MediaType.APPLICATION_JSON).get(Response.class);
        ResponseMessage<ArrayList<SubredditActivity>> responseMessage = serviceResponse.readEntity(new GenericType<ResponseMessage<ArrayList<SubredditActivity>>>() {
        });
        Assert.assertEquals(ResponseMessage.STATUS_OK, responseMessage.getStatus());
        List<SubredditActivity> list = responseMessage.getData();
        Assert.assertEquals(list.get(0).getSubredditId(), "top1");
        Assert.assertEquals(list.get(0).getActivity(), 3);
    }

    @Test
    public void testSubmissionsMostActive() throws Exception {
        String url = BASE_URL + "mostActive?eventType=" + EventType.SUBMISSION.name() + "&timeRange=" + TimeRange.MIN.name();
        WebTarget resource = restClient.target(url);
        Response serviceResponse = resource.request(MediaType.APPLICATION_JSON).get(Response.class);
        ResponseMessage<ArrayList<SubredditActivity>> responseMessage = serviceResponse.readEntity(new GenericType<ResponseMessage<ArrayList<SubredditActivity>>>() {
        });
        Assert.assertEquals(ResponseMessage.STATUS_OK, responseMessage.getStatus());
        List<SubredditActivity> list = responseMessage.getData();
        Assert.assertEquals(list.get(0).getSubredditId(), "top2");
        Assert.assertEquals(list.get(0).getActivity(), 2);
    }

}