package com.ubnt.testTask.entities;

/**
 * JSON Error response object. Represent errorCode and human readable message
 *
 */
public class ErrorResponseJson {
    private String errorCode;
    private String errorMessage;

    public ErrorResponseJson() {
	super();
    }

    /**
     * @param errorCode
     * @param errorMessage
     */
    public ErrorResponseJson(String errorCode, String errorMessage) {
	super();
	this.errorCode = errorCode;
	this.errorMessage = errorMessage;
    }

    /**
     * @return errorCode
     */
    public String getErrorCode() {
	return errorCode;
    }

    /**
     * @param errorCode
     */
    public void setErrorCode(String errorCode) {
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
}
