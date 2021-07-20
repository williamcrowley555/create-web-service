package com.william.createwebservice.ui.model.response;

import java.util.Date;

public class SuccessMessage {
    private Date timestamp;
    private String message;

    public SuccessMessage() {
    }

    public SuccessMessage(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
