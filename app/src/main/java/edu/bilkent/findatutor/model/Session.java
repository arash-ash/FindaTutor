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

    public Session(String tutorUID, String studentUID, String postUID, String sessionDate,
                   String dayTime, String preferedLocation) {
        this.tutorUID = tutorUID;
        this.studentUID = studentUID;
        this.postUID = postUID;
        this.sessionDate = sessionDate;
        this.createdDate = sDateFormat.format(new Date());
        this.isTerminated = false;
        this.isWithdrawn = false;
        this.dayTime = dayTime;
        this.preferedLocation = preferedLocation;
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
        return result;
    }

    public void terminate() {
        isTerminated = true;
    }

    public void withdraw() {
        isTerminated = true;
        isWithdrawn = true;

    }

    public void review(String text, float rating) {
        review.setText(text);
        review.setRating(rating);
    }
}
