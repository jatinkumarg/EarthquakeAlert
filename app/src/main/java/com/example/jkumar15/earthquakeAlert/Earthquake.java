package com.example.jkumar15.earthquakeAlert;

import java.util.Date;

public class Earthquake {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Earthquake(String title, String time, String url) {
        this.title = title;
        this.time = new Date(Long.parseLong(time));
        this.url = url;
        ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private Date time;
    private String url;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String toString(){
        return getTitle() + '\n' + getTime();
    }

}
