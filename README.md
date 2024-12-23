Project: OTUS HM

This project is a Java-based application that utilizes Selenium WebDriver for UI automation testing. It includes modern dependencies and plugins for efficient testing, logging, and configuration management.

Requirements:

Java 17
Maven 3.x

Features

Selenium WebDriver: For UI automation.

TestNG: For writing and running tests.

WebDriverManager: For automatic WebDriver binaries management.

Log4j: For logging.

Jsoup: For working with HTML documents.

Getting Started

Prerequisites

Install Java 17 and set the JAVA_HOME environment variable.

Install Maven and ensure it's added to your PATH.

Setup

Clone the repository:

git clone <repository_url>
cd otus_hm

Build the project:

mvn clean install

Running Tests

To execute tests, use the Maven Surefire Plugin:

mvn test

Configuration

Configuration is managed using the Owner library. Update configuration properties in the src/main/resources directory.

Plugins Used

Maven Compiler Plugin

Ensures Java 17 is used for compiling the project.

Maven Surefire Plugin

Executes all test classes matching the pattern **/*Test.java.

SpotBugs Maven Plugin

Detects potential bugs in your code during the verify phase.

Maven Checkstyle Plugin

Ensures code adheres to defined coding standards. You can customize rules in src/main/resources/checkstyle.xml.

Dependencies

Runtime Dependencies

Selenium Java: Core library for Selenium WebDriver.

WebDriverManager: Simplifies driver management.

Log4j: For logging purposes.

Owner: Easy configuration management.

Jackson Databind: JSON serialization/deserialization.

Guava: Handy utilities.

Jsoup: HTML parsing and manipulation.

Test Dependencies

TestNG: Test framework.