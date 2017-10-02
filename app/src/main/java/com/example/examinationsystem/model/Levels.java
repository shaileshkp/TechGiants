package com.example.examinationsystem.model;

/**
 * Created by Shailesh on 10/2/2017.
 */

public class Levels {

    private String level;
    private String duration;
    private String description;

    public Levels() {
    }

    public Levels(String level, String duration, String description) {
        this.level = level;
        this.duration = duration;
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
