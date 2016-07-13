package edu.bilkent.findatutor.model;

import java.util.Date;

/**
 * Created by arash on 7/13/16.
 */
public class Session {

    private User tutor;
    private User student;
    private Post post;
    private Review review;
    private Chat messages;
    private Date sessionDate;
    private Date createdDate;
    private boolean isTerminated;
    private boolean isWithdrawn;

    public Session(User tutor, User student, Post post, Date sessionDate, Date createdDate) {
        this.tutor = tutor;
        this.student = student;
        this.post = post;
        this.sessionDate = sessionDate;
        this.createdDate = createdDate;
        this.isTerminated = false;
        this.isWithdrawn = false;
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
