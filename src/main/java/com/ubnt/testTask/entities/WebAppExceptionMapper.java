package com.ubnt.testTask.entities;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebAppExceptionMapper extends Throwable implements ExceptionMapper<Throwable> {

    /**
     * 
     */
    private static final long serialVersionUID = -5427287262361525349L;

    @Override
    public Response toResponse(Throwable exception) {
        ResponseMessage<String> responseEntity = new ResponseMessage<>(ResponseMessage.STATUS_ERROR);
        responseEntity.setErrorMessage(exception.getMessage());
        responseEntity.setErrorCode(0);
        return Response.status(Response.Status.BAD_REQUEST).entity(responseEntity).type(MediaType.APPLICATION_JSON).build();
    }

}
