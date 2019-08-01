package com.ubnt.testTask.entities;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper extends Throwable implements ExceptionMapper<ConstraintViolationException> {

    /**
     * 
     */
    private static final long serialVersionUID = 3335409575407224375L;

    public Response toResponse(final ConstraintViolationException exception) {
        ResponseMessage<String> responseEntity = new ResponseMessage<>(ResponseMessage.STATUS_ERROR);
        responseEntity.setErrorMessage(prepareMessage(exception));
        responseEntity.setErrorCode(1);
        return Response.status(Response.Status.BAD_REQUEST).entity(responseEntity).type(MediaType.APPLICATION_JSON).build();
    }

    private String prepareMessage(ConstraintViolationException exception) {
        String msg = "";
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            msg += cv.getPropertyPath() + " " + cv.getMessage() + "\n";
        }
        return msg;
    }

}
