package com.ubnt.testTask.entities;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ApiWebAppException extends WebApplicationException {

    /**
     * 
     */
    private static final long serialVersionUID = -4279038215558768164L;
    private String message;

    public ApiWebAppException(String message) {
        super(getErrorResponse(message));
        this.message = message;
    }

    private static Response getErrorResponse(String message) {
        ResponseMessage<String> responseEntity = new ResponseMessage<>(ResponseMessage.STATUS_ERROR);
        responseEntity.setErrorMessage(message);
        return Response.status(Response.Status.BAD_REQUEST).entity(responseEntity).type(MediaType.APPLICATION_JSON).build();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ApiWebAppException [getMessage()=" + message + "]";
    }

}
