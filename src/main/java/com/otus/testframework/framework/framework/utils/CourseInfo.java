package com.otus.testframework.framework.framework.utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents information about a course, including its title, start date, and link.
 */


public class CourseInfo {
    private String title;
    private LocalDate startDate;
    private String link;

    public CourseInfo(String title, LocalDate startDate, String link) {
        this.title = title;
        this.startDate = startDate;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "CourseInfo{" +
                "title='" + title + '\'' +
                ", startDate=" + startDate +
                ", link='" + link + '\'' +
                '}';
    }


    public static Map<String, List<CourseInfo>> findEarliestAndLatestCourses(Map<String, CourseInfo> courses) {

        Map<LocalDate, List<CourseInfo>> groupedByDate = courses.values().stream()
                .collect(Collectors.groupingBy(CourseInfo::getStartDate));

        Map<String, List<CourseInfo>> result = groupedByDate.entrySet().stream()
                .reduce(
                        new HashMap<String, List<CourseInfo>>() {{
                            put("Earliest", null);
                            put("Latest", null);
                        }},
                        (accumulator, entry) -> {
                            LocalDate date = entry.getKey();
                            List<CourseInfo> coursesList = entry.getValue();

                            if (accumulator.get("Earliest") == null || date.isBefore(accumulator.get("Earliest")
                                    .get(0).getStartDate())) {
                                accumulator.put("Earliest", coursesList);
                            }

                            if (accumulator.get("Latest") == null || date.isAfter(accumulator.get("Latest")
                                    .get(0).getStartDate())) {
                                accumulator.put("Latest", coursesList);
                            }

                            return accumulator;
                        },
                        (map1, map2) -> {
                            throw new UnsupportedOperationException("Parallel reduction is not supported");
                        }
                );

        return result;
    }
}