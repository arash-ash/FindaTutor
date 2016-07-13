package edu.bilkent.findatutor.model;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linus on 09.07.2016.
 */
public class Chat {

    private String title;
    private String sender;
    private Date lastMessageDate;
    private Date createdDate;

    public Chat() {
    }

    public Chat(String title, String sender) {
        this.title = title;
        this.sender = sender;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("sender", sender);

        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(Date lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
