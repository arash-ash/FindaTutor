package edu.bilkent.findatutor.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Review {

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private String author;
    private String authorPhotoURL;
    private String text;
    private float rating;
    private Date createdDate;

    public Review() {
        // Default constructor required for calls to DataSnapshot.getValue(Review.class)
    }

    public Review(String uid, String author, String text, String url) {
        this.author = author;
        this.text = text;
        this.authorPhotoURL = url;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("authorName", author);
        result.put("text", text);
        result.put("authorPhotoURL", authorPhotoURL);

        return result;
    }


    public Date getDate() {
        return createdDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAuthorPhotoURL() {
        return authorPhotoURL;
    }
}

