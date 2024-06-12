package org.wikipedia.model;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Latest {
    private int id;
    @SerializedName("timestamp")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Latest latest = (Latest) o;
        return id == latest.id && Objects.equals(timeStamp, latest.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeStamp);
    }

    @Override
    public String toString() {
        return "Latest{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
