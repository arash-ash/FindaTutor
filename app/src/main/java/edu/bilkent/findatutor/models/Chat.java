package edu.bilkent.findatutor.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linus on 09.07.2016.
 */
public class Chat {

    public String title;
    public String sender;

    public Chat(){};

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


}
