package com.smallcherry.studyplanner.service;

import com.smallcherry.studyplanner.domain.Lecture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class PlannerSerivce {
    public void getPlan(String input) {
        int MIN = 25;

        String[] s = input.split("\n\r");
        List<String> list = Arrays.stream(s).toList();

        System.out.println("Hello world!");
        List<Lecture> lectures = new ArrayList<>();
        stringListToLectures(list, lectures);

        Lecture lecture1 = lectures.get(0);
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
                log.info("DAY" + day);
                log.info("\t" + lectures.get(index - lecCount).getName());
                log.info("\t" + lecture.getName());
                log.info("\t" + (sum / 60) + "분 " + (sum % 60) + "초");
                sum = 0;
                day++;
                lecCount = 0;
            } else {
                if (index == lectures.size()) {
                    log.info("DAY" + day);
                    log.info("\t" + lectures.get(index - lecCount - 1).getName());
                    log.info("\t" + lecture.getName());
                    log.info("\t" + (sum / 60) + "분 " + (sum % 60) + "초");
                }
                lecCount++;
            }
        }
    }

    private static void stringListToLectures(List<String> list, List<Lecture> lectures) {
        int sectionNum = 0;
        int index = 0;
        while (true) {
            String curr = list.get(index);
            if (curr.startsWith("섹션")) {
                index += 2;
                sectionNum++;
            } else {
                // lecName = curr;
                index++;
                if (list.get(index).startsWith("미리보기")) {
                    index++;
                }
                String timeStr = list.get(index);
//                System.out.println("timeStr = " + timeStr);
                String[] arr = timeStr.split(":");
                int min = Integer.parseInt(arr[0].trim());
                int sec = Integer.parseInt(arr[1].trim());
                int time = min * 60 + sec;
//                System.out.println("lecName = " + curr);
                int tenMinutes = 10 * 60 + 59;
                if (time > tenMinutes) {
                    int dividedLecNum = time / tenMinutes + 1;
                    time /= dividedLecNum;
                    for (int i = 0; i < dividedLecNum; i++) {
                        if (i == dividedLecNum - 1) {
                            lectures.add(new Lecture("섹션 " + sectionNum, curr, time));
//                            System.out.println("lectures.get(lectures.size()-1).getName() = " + lectures.get(lectures.size()-1).getName());
                        } else {
                            lectures.add(new Lecture("섹션 " + sectionNum, curr + " (" + (i + 1) + "/" + dividedLecNum + ")", time));
                        }
                    }
                } else {
                    lectures.add(new Lecture("섹션 " + sectionNum, curr, time));
                }
                if (curr.trim().endsWith("다음으로")) {
                    break;
                }
                index ++;
            }
        }
    }
}
