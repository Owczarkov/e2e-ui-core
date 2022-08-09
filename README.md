# Selenium UI components

## Overview

This is standalone project for selenium with basic configuration and components which could be imported and extended in
java projects.

## General

### How To Use

In order to use Selenium UI components you will need to:

#### Steps

1. Add selenium-ui-components as a dependency according to you project management tool (maven/gradle).
2. Reload project.
3. Set up envURL (DriverFactor.setEnvUrl(_${urlGoesHere}_)
4. Import selenium-ui-components components and use them directly or extend them if needed.

#### Features

1. Sets and opens browser
2. Implementation of most common UI components
3. Whole page screenshot
4. Timeouts
5. Custom Expected Conditions with generic approach
6. Component Factory
7. Extended selenium functionalities

### Technologies used

1. Java 11
2. Gradle
3. Selenium
4. WebdriverManager
5. Lombok
6. Log4J
7. AssertJ
8. Spotbugs
9. Allure

## Contribution

### Testing

1. Unit tests - to be created
2. For testing that your changes does not break implementation run "ImplementationTests" class.

## Versioning

1. Set up version which you would like to publish (build.gradle -> `version` variable)
    1. ⚠️`Publishing without changing version will override existing version` ⚠️
        1. Given a version number MAJOR.MINOR.PATCH, increment the:
            1. MAJOR version when you make incompatible changes
            2. MINOR version when you add functionality in a backwards compatible manner
            3. PATCH version when you make backwards compatible bug fixes.
2. deploy.yaml file will be triggered upon merging PR to master branch
    1. build.gradle has been configured to create `.jar` file and publishing it to artifactory automatically
