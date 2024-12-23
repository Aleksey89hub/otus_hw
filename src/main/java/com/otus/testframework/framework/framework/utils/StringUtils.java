package com.otus.testframework.framework.framework.utils;

/**
 * Utility class for performing common string operations.
 *
 */

public class StringUtils {

    public static String stripNumbersInParentheses(String str) {
        return str.replaceAll("\\s*\\(\\d+\\)", "").trim();
    }
}
