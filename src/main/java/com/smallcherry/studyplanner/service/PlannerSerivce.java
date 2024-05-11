package com.smallcherry.studyplanner.service;

import com.smallcherry.studyplanner.domain.Lecture;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.swing.text.AttributeSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Service
@Slf4j
public class PlannerSerivce {
    public String getPlan(String url) {
        int MIN = 25;
        log.info("==== studyplanner started ====");

        StringBuilder sb = new StringBuilder();
        List<Lecture> lectureList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).get();

            Elements sectionCoverList = doc.select("div.cd-accordion__section-cover");
            int sectionNum = 0;
            for (Element sectionCover : sectionCoverList) {
                if (sectionCover.select("span.cd-accordion__section-title").get(0).text().startsWith("섹션 0")) {
                    continue;
                }
                sectionNum++;
                Elements titleElements = sectionCover.select("span.ac-accordion__unit-title");
                Elements timeElements = sectionCover.select("span.ac-accordion__unit-info--time");
                if (titleElements.size() != timeElements.size()) {
                    Elements section = sectionCover.select("span.cd-accordion__section-title");
                    String errorMessage = "파싱 문제 있음 - title, time 안맞는 문제";
                    log.error(errorMessage);
                    throw new RuntimeException(errorMessage);
                }
                for (int index = 0; index < titleElements.size(); index++) {
                    String title = titleElements.get(index).text();
                    String timeStr = timeElements.get(index).text();
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
                                lectureList.add(
                                        Lecture.builder()
                                                .section("섹션 " + sectionNum)
                                                .name(title)
                                                .time(time)
                                                .build()
                                );
                            } else {
                                lectureList.add(
                                        Lecture.builder()
                                                .section("섹션 " + sectionNum)
                                                .name(title + " (" + (i + 1) + "/" + dividedLecNum + ")")
                                                .time(time)
                                                .build()
                                );
                            }
                        }
                    } else {
                        lectureList.add(
                                Lecture.builder()
                                        .section("섹션 " + sectionNum)
                                        .name(title)
                                        .time(time)
                                        .build()
                        );
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error fetching the document: " + e.getMessage());
        }
        int sum = 0;
        int minSeconds = 60 * MIN;
        int index = 0;
        int day = 1;
        int lecCount = 0;
        for (Lecture lecture : lectureList) {
            index++;
            sum += lecture.getTime();
            if (sum >= minSeconds) {
                lecCount++;
                sb.append(format("DAY%d", day))
                        .append(format("\t%s", lectureList.get(index - lecCount).getName()))
                        .append(format("\t%s", lecture.getName()))
                        .append(format("\t%d분 %d초\n", (sum / 60), (sum % 60)));
                sum = 0;
                day++;
                lecCount = 0;
            } else {
                if (index == lectureList.size()) {
                    sb.append(format("DAY%d", day))
                            .append(format("\t%s", lectureList.get(index - lecCount - 1).getName()))
                            .append(format("\t%s", lecture.getName()))
                            .append(format("\t%d분 %d초\n", (sum / 60), (sum % 60)));

                }
                lecCount++;
            }
        }
        String resultString = sb.toString();
        return resultString;
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
                            lectures.add(new Lecture("섹션 " + sectionNum, curr, time));
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
                index++;
            }
        }
    }
}
