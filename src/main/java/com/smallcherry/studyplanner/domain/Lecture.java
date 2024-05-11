package com.smallcherry.studyplanner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
@Builder
public class Lecture {
    String section;
    String name;
    int time; // sec

    public int getTime() {
        return time;
    }

    public String getName() {
        return section + ". " + name;
    }

}
