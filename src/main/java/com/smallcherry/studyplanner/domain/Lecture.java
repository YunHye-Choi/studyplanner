package com.smallcherry.studyplanner.domain;

public class Lecture {
    String section;
    String name;
    int time; // sec

    public Lecture(String s, String n, int time) {
        this.section = s;
        this.name = n;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return section + ". " + name;
    }

}
