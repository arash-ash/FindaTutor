package edu.bilkent.findatutor.model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arash on 7/13/16.
 */
public class Notification {
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private String title;
    private String text;
    private String tag;
    private String createdDate;

    public Notification(String title, String tag, String text) {
        this.title = title;
        this.createdDate = (new Date()).toString();
        this.tag = tag;
        this.text = text;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("text", text);
        result.put("tag", tag);
        result.put("created-date", createdDate);
//        result.put("createdDate", sDateFormat.format(createdDate));
//        result.put("lastMessageDate", sDateFormat.format(lastMessageDate));

        return result;
    }

    public String getDate() {
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
