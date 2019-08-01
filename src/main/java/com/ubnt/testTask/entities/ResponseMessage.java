package com.ubnt.testTask.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * JSON response object. Represent data for 'ok' status and errorCode and human
 * readable message for 'error' status
 *
 */
@JsonInclude(value = Include.NON_NULL)
public class ResponseMessage<O> {

    public static final String STATUS_OK = "ok";
    public static final String STATUS_ERROR = "error";

    private String status = STATUS_OK;
    private Integer errorCode = null;
    private String errorMessage;
    private O data;

    public ResponseMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ResponseMessage(String status) {
        super();
        this.status = status;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return human readable message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the data
     */
    public O getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(O data) {
        this.data = data;
    }

}
