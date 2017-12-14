package com.example.examinationsystem.model;

/**
 * Created by Shailesh on 14-Dec-17.
 */

public class Result {

    String timestamp;
    int score;

    public Result() {
    }

    public Result(String timestamp, int score) {
        this.timestamp = timestamp;
        this.score = score;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
