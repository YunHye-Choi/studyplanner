package com.smallcherry.studyplanner.service;

import com.smallcherry.studyplanner.domain.Lecture;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PlannerSerivce {
    public void getPlan(String input) {
        int MIN = 25;

        String[] s = input.split("\n");
        List<String> list = Arrays.stream(s).toList();

        System.out.println("Hello world!");
        List<Lecture> lectures = new ArrayList<>();
        stringListToLectures(list, lectures);

        int sum = 0;
        int minSeconds = 60 * MIN;
        int index = 0;
        int day = 1;
        int lecCount = 0;
        for (Lecture lecture : lectures) {
            index++;
            sum += lecture.getTime();
            if (sum >= minSeconds) {
                lecCount++;
                System.out.print("DAY" + day);
                System.out.print("\t" + lectures.get(index - lecCount).getName());
                System.out.print("\t" + lecture.getName());
                System.out.print("\t" + (sum / 60) + "분 " + (sum % 60) + "초 \n");
                sum = 0;
                day++;
                lecCount = 0;
            } else {
                if (index == lectures.size()) {
                    System.out.print("DAY" + day);
                    System.out.print("\t" + lectures.get(index - lecCount - 1).getName());
                    System.out.print("\t" + lecture.getName());
                    System.out.print("\t" + (sum / 60) + "분 " + (sum % 60) + "초 \n");
                }
                lecCount++;
            }
        }
    }

    private static void stringListToLectures(List<String> list, List<Lecture> lectures) {
        int sectionNum = 0;
        int index = 0;
        while (true) {
            String next = list.get(index++);
            if (next.startsWith("섹션")) {
                for (int i = 0; i < 2; i++)
                    index++;
                sectionNum++;
            } else {
                String timeStr = list.get(index++);
                String[] arr = timeStr.split(":");
                int min = Integer.parseInt(arr[0].trim());
                int sec = Integer.parseInt(arr[1].trim());
                int time = min * 60 + sec;

                int tenMinutes = 10 * 60 + 59;
                if (time > tenMinutes) {
                    int dividedLecNum = time / tenMinutes + 1;
                    time /= dividedLecNum;
                    for (int i = 0; i < dividedLecNum; i++) {
                        if (i == dividedLecNum - 1) {
                            lectures.add(new Lecture("섹션 " + sectionNum, next, time));
                        } else {
                            lectures.add(new Lecture("섹션 " + sectionNum, next + " (" + (i + 1) + "/" + dividedLecNum + ")", time));
                        }
                    }
                } else {
                    lectures.add(new Lecture("섹션 " + sectionNum, next, time));
                }
                if (next.trim().endsWith("다음으로")) {
                    break;
                }
            }
        }
    }
}
