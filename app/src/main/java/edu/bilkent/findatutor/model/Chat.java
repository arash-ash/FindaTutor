package edu.bilkent.findatutor.model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linus on 09.07.2016.
 */
public class Chat {

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private String title;
    private String sender;
    private Date createdDate;
    private Date lastMessageDate;
    private String senderUID;
    private String senderURL;
    private String postUID;


    public Chat() {
    }

    public Chat(String title, String sender, String senderUID, String postUID, String senderURL) {
        this.title = title;
        this.sender = sender;
        this.senderUID = senderUID;
        this.createdDate = new Date();
        this.postUID = postUID;
        this.senderURL = senderURL;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("sender", sender);
        result.put("senderUID", senderUID);
        result.put("postUID", postUID);
        result.put("senderURL", senderURL);

//        result.put("createdDate", sDateFormat.format(createdDate));
//        result.put("lastMessageDate", sDateFormat.format(lastMessageDate));

        return result;
    }

    public String getSenderURL() {
        return senderURL;
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

    public String getSenderUID() {
        return senderUID;
    }

    public String getPostUID() {
        return postUID;
    }
}
