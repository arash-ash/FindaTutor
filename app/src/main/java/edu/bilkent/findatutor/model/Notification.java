package edu.bilkent.findatutor.model;

import java.util.Date;

/**
 * Created by arash on 7/13/16.
 */
public class Notification {
    private String title;
    private String text;
    private String tag;
    private Date createdDate;

    public Notification(String title, Date date, String tag, String text) {
        this.title = title;
        this.createdDate = date;
        this.tag = tag;
        this.text = text;
    }

    public Date getDate() {
        return createdDate;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

}
