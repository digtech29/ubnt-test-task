package com.ubnt.testTask.controllers;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.ubnt.testTask.dao.RedditMessageDao;
import com.ubnt.testTask.entities.ApiWebAppException;
import com.ubnt.testTask.entities.EnumValidator;
import com.ubnt.testTask.entities.EventType;
import com.ubnt.testTask.entities.ResponseMessage;
import com.ubnt.testTask.entities.SubredditActivity;
import com.ubnt.testTask.entities.TimeRange;
import com.ubnt.testTask.utils.ApplicationContextHolder;

@Path("/")
public class RedditController {

    private static final Logger log = Logger.getLogger(RedditController.class);

    @Context
    private ContainerRequestContext ctx;

    @Context
    private ServletContext servletContext;

    @GET
    @Path("/activity")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseMessage<Long> getActivity( //
            @Context HttpServletRequest request, //
            @NotNull(message = "Query parameter 'timeRange' is required") //
            @EnumValidator(enumClass = TimeRange.class, message = "Valid timeRange is  MIN, MIN_5, HOUR, DAY, ALL_TIME") //
            @QueryParam("timeRange") String timeRange, //
            @NotNull(message = "Query parameter 'eventType' is required") //
            @EnumValidator(enumClass = EventType.class, message = "Valid eventType is SUBMISSION, COMMENT") //
            @QueryParam("eventType") String eventType) {
        log.info("getActivity " + timeRange + " eventType=" + eventType);
        try {
            RedditMessageDao dao = ApplicationContextHolder.getRedditMessageDao();
            ResponseMessage<Long> result = new ResponseMessage<>(ResponseMessage.STATUS_OK);
            result.setData(dao.getActivity(TimeRange.valueOf(timeRange), EventType.valueOf(eventType)));
            return result;
        } catch (Exception e) {
            log.error("Error ", e);
            throw new ApiWebAppException(e.getMessage());
        }
    }

    @GET
    @Path("/top100")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseMessage<List<SubredditActivity>> getTop100( //
            @Context HttpServletRequest request, //
            @NotNull(message = "Query parameter 'timeRange' is required") //
            @EnumValidator(enumClass = TimeRange.class, message = "Valid timeRange is  MIN, MIN_5, HOUR, DAY, ALL_TIME") //
            @QueryParam("timeRange") String timeRange) {
        log.info("getTop100 " + timeRange);
        try {
            TimeRange range = TimeRange.valueOf(timeRange);
            RedditMessageDao dao = ApplicationContextHolder.getRedditMessageDao();
            ResponseMessage<List<SubredditActivity>> result = new ResponseMessage<>(ResponseMessage.STATUS_OK);
            result.setData(dao.getTop100(range));
            return result;
        } catch (Exception e) {
            log.error("Error ", e);
            throw new ApiWebAppException(e.getMessage());
        }
    }

    @GET
    @Path("/mostActive")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseMessage<List<SubredditActivity>> getMostActive( //
            @Context HttpServletRequest request, //
            @NotNull(message = "Query parameter 'timeRange' is required") //
            @EnumValidator(enumClass = TimeRange.class, message = "Valid timeRange is  MIN, MIN_5, HOUR, DAY, ALL_TIME") //
            @QueryParam("timeRange") String timeRange, //
            @NotNull(message = "Query parameter 'eventType' is required") //
            @EnumValidator(enumClass = EventType.class, message = "Valid eventType is SUBMISSION, COMMENT") //
            @QueryParam("eventType") String eventType //
    ) {
        log.info("getMostActive " + eventType + " " + timeRange);
        try {
            RedditMessageDao dao = ApplicationContextHolder.getRedditMessageDao();
            ResponseMessage<List<SubredditActivity>> result = new ResponseMessage<>(ResponseMessage.STATUS_OK);
            result.setData(dao.getMostActive(TimeRange.valueOf(timeRange), EventType.valueOf(eventType)));
            return result;
        } catch (Exception e) {
            log.error("Error ", e);
            throw new ApiWebAppException(e.getMessage());
        }
    }

}
