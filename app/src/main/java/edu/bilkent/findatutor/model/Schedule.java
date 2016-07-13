package edu.bilkent.findatutor.model;

/**
 * Created by arash on 7/13/16.
 */
public class Schedule {

    final static int MON = 1;
    final static int TUE = 2;
    final static int WED = 3;
    final static int THU = 4;
    final static int FRI = 5;
    final static int SAT = 6;
    final static int SUN = 7;

    final static int MORNING_SESSION = 1;
    final static int AFTERNOON_SESSION = 2;
    final static int EVENING_SESSION = 3;

    private boolean[][] schedule;

    public Schedule() {
        schedule = new boolean[7][3];
    }

    public void setSchedule(int day, int time, boolean setTrue) {
        schedule[day][time] = setTrue;
    }

    public boolean getSchedule(int day, int time) {
        return schedule[day][time];
    }


}
