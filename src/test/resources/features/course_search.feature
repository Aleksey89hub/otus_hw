Feature: Course Search

  Scenario: User finds a course by name
    Given the user is on the courses page
    When the user searches for "Java QA Engineer. Professional"
    Then the course title should be "Java QA Engineer. Professional"

  Scenario: Verify that the course cards for the earliest and latest courses display the correct titles
    Given the user is on the courses page
    When the user retrieves the earliest and latest course titles
    Then the course page should display the correct titles

  Scenario: Verify that selecting a random course category from the 'Обучение' menu opens the correct course
    Given the user is on the main page
    When the user selects a random course category from the 'Обучение' menu
    Then the correct course catalog should open