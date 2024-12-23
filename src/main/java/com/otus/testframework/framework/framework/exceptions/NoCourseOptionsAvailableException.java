package com.otus.testframework.framework.framework.exceptions;

/**
 * Custom exception to indicate that no course options are available.
 * This exception extends the `RuntimeException` class and is thrown when an operation
 * involving course options encounters a situation where no options are available.
 */

public class NoCourseOptionsAvailableException extends RuntimeException {
    public NoCourseOptionsAvailableException(String message) {
        super(message);
    }
}
