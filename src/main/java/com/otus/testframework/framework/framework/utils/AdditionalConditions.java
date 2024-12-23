package com.otus.testframework.framework.framework.utils;

import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.function.Supplier;

/**
 * Utility class for custom ExpectedConditions used in WebDriver waits.
 */

public class AdditionalConditions {

    public static ExpectedCondition<Boolean> isTrue(Supplier<Boolean> isTrue) {
        return driver -> isTrue.get();
    }
}
