package edu.bilkent.findatutor.model;

import java.util.ArrayList;

/**
 * Created by arash on 7/13/16.
 */
public class Statistics {

    private int reviewsCount;
    private int viewsCount;
    private int[] earnedMoney;
    private int[] spentMoney;
    private ArrayList<User> subscriptionList;


    public Statistics() {
        reviewsCount = 0;
        viewsCount = 0;
        subscriptionList = new ArrayList<User>();
        earnedMoney = new int[10000];
        spentMoney = new int[10000];
    }


    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public void addSubscriber(User user) {
        subscriptionList.add(user);
    }

    public User getSubscriber(int index) {
        return subscriptionList.get(index);
    }


    public void setEarnedMoney(int day, int money) {
        earnedMoney[day] = money;
    }

    public void setSpentMoney(int day, int money) {
        spentMoney[day] = money;
    }

    public int getEarnedMoney(int day) {
        return earnedMoney[day];
    }

    public int getSpentMoney(int day) {
        return spentMoney[day];
    }


    public int getTotalEarnedMoney(int daysNumber) {
        return 0;
    }

    public int getTotalSpentMoney(int daysNumber) {
        return 0;
    }
}
