package edu.bilkent.findatutor.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arash on 7/13/16.
 */
public class Schedule {

    private String schedule;

    public Schedule(String day, String time) {
        schedule = day + "-" + time;

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("schedule", schedule);
        return result;
    }

    public void add(String day, String time) {

        schedule += " , " + day + "-" + time;
    }

    public ArrayList<String> getSchedule() {
        ArrayList<String> list;
        list = new ArrayList<String>(Arrays.asList(schedule.split(" , ")));
        return list;
    }


}
