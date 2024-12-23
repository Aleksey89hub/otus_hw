package com.otus.testframework.framework.framework.utils;

import java.time.LocalDate;
import java.util.Collections;
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

        LocalDate earliestDate = groupedByDate.keySet().stream()
                .min(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("No courses found"));

        LocalDate latestDate = groupedByDate.keySet().stream()
                .max(LocalDate::compareTo)
                .orElseThrow(() -> new RuntimeException("No courses found"));

        List<CourseInfo> earliestCourses = groupedByDate.getOrDefault(earliestDate, Collections.emptyList());
        List<CourseInfo> latestCourses = groupedByDate.getOrDefault(latestDate, Collections.emptyList());

        Map<String, List<CourseInfo>> result = new HashMap<>();
        result.put("Earliest", earliestCourses);
        result.put("Latest", latestCourses);

        return result;
    }
}