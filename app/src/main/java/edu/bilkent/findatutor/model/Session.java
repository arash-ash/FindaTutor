package edu.bilkent.findatutor.model;

import com.google.firebase.database.Exclude;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arash on 7/13/16.
 */
public class Session {

    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private String tutorUID;
    private String studentUID;
    private String postUID;
    private Review review;
    private Chat messages;
    private String sessionDate;
    private String createdDate;
    private boolean isTerminated;
    private boolean isWithdrawn;
    private String dayTime;
    private String preferedLocation;
    private String postTitle;
    private String tutorName;
    private String authorURL;


    public Session() {
    }

    public Session(String tutorUID, String studentUID, String postUID, String sessionDate,
                   String dayTime, String preferedLocation, String postTitle, String tutorName, String authorURL) {


        this.tutorUID = tutorUID;
        this.studentUID = studentUID;
        this.postUID = postUID;
        this.sessionDate = sessionDate;
        this.createdDate = sDateFormat.format(new Date());
        this.isTerminated = false;
        this.isWithdrawn = false;
        this.dayTime = dayTime;
        this.preferedLocation = preferedLocation;
        this.postTitle = postTitle;
        this.tutorName = tutorName;
        this.authorURL = authorURL;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("tutorUID", tutorUID);
        result.put("studentUID", studentUID);
        result.put("postUID", postUID);
        result.put("sessionDate", sessionDate);
        result.put("createdDate", createdDate);
        result.put("isTerminated", isTerminated);
        result.put("isWithdrawn", isWithdrawn);
        result.put("dayTime", dayTime);
        result.put("preferedLocation", preferedLocation);
        result.put("postTitle", postTitle);
        result.put("tutorName", tutorName);
        result.put("authorURL", authorURL);

        return result;
    }

    public void terminate() {
        isTerminated = true;
    }

    public void withdraw() {
        isTerminated = true;
        isWithdrawn = true;

    }

    public String getAuthorURL() {
        return authorURL;
    }

    public String getDayTime() {
        return dayTime;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPreferedLocation() {
        return preferedLocation;
    }

    public boolean isWithdrawn() {
        return isWithdrawn;
    }

    public boolean isTerminated() {
        return isTerminated;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public Chat getMessages() {
        return messages;
    }

    public Review getReview() {
        return review;
    }

    public String getPostUID() {
        return postUID;
    }

    public String getStudentUID() {
        return studentUID;
    }

    public String getTutorUID() {
        return tutorUID;
    }

    public void review(String text, float rating) {
        review.setText(text);
        review.setRating(rating);
    }

    public String getTutorName() {
        return tutorName;
    }
}
