package com.otus.testframework.framework.enums;

/**
 * Represents various categories of courses in the OTUS platform.
 * Each category is associated with a display name that is used for user-friendly representation.
 */

public enum CourseCategory {
    PROGRAMMING("Программирование"),
    ARCHITECTURE("Архитектура"),
    INFRASTRUCTURE("Инфраструктура"),
    SECURITY("Безопасность"),
    DATA_SCIENCE("Data Science"),
    GAME_DEV("GameDev"),
    MANAGEMENT("Управление"),
    ANALYTICS("Аналитика и анализ"),
    TESTING("Тестирование"),
    CORPORATE_COURSES("Корпоративные курсы"),
    IT_WITHOUT_PROGRAMMING("IT без программирования"),
    SPECIALIZATIONS("Специализации"),
    COURSE_SUBSCRIPTION("Подписка на курсы");

    private final String displayName;

    CourseCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CourseCategory fromString(String displayName) {
        for (CourseCategory category : values()) {
            if (category.getDisplayName().equalsIgnoreCase(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No enum constant for display name: " + displayName);
    }
}
