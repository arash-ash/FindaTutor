package edu.bilkent.findatutor.models;

/**
 * Created by linus on 25.06.2016.
 */
public class Tutor extends User {

    public float rating;


    public Tutor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Tutor(String username, String email) {
    super(username, email);

    }

}
