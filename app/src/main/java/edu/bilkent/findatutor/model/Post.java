package edu.bilkent.findatutor.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
    public boolean isRequested;
    public String uid;
    public String author;
    public String title;
    public String body;
    public String subject;
    public String language;
    public String school;
    public String price;
    public String sessionDate;
    public int viewsCount = 0;
    public int sessionsCount = 0;
    public float rating;
    private String createdDate;


    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String uid, String author, String title, String body, String subject,
                String language, String school, String price, String date) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.subject = subject;
        this.language = language;
        this.school = school;
        this.price = price;
        this.sessionDate = date;
        createdDate = sDateFormat.format(new Date());
        this.isRequested = false;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("body", body);
        result.put("viewsCount", viewsCount);
        result.put("subject", subject);
        result.put("language", language);
        result.put("school", school);
        result.put("price", price);
        result.put("sessionDate", sessionDate);
        result.put("createdDate", createdDate);
        result.put("isRequested", isRequested);

        return result;
    }


    public Session requestSession(Date sessionDate) {
        return null;
    }

}

