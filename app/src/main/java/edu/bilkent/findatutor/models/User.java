package edu.bilkent.findatutor.models;

import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String name;
    public String school;
    public String photoURL;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}

