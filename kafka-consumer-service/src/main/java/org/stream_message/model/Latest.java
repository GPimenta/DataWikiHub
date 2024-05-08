package org.stream_message.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Latest {
    private int id;
    private LocalDateTime timeStamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = LocalDateTime.parse(timeStamp, DateTimeFormatter.ISO_DATE_TIME);
    }
}
