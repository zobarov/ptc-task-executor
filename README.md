# Hourglass Tasks

Test assessment project

## Problem

Create a restful micro service which will continuously calculate the average time it takes to
perform a named task. There are n tasks we are tracking, each with a unique id. You need to
implement two methods: 

Task performed; accepts task id and duration in milliseconds; performs necessary calculations and data storing; returns ok or error
Get average time; accepts task id; returns task id and average duration in milliseconds.
Write service in Java; Use data store of your choice but make sure it can later be easily replaced with an alternative data store.

### Test Examples

http://localhost:8080/task?taskId=1&duration=2

## Running and usage

### From IDE:
1. Import as Maven Project
2. Maven Install
3. Run HourglassTaskApplication

### From CommandLine:

Follow one of the following items:
http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html

## Usage

When it's ran without any errors hit your browser with following URL pattern:
http://localhost:8080/task?taskId=1&duration=2

__NOTE: The default port has been switched to 8090 to prevent any conflicts__

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://spring.io/) - Spring Boot, Core, REST, Test 
* [JUnit](https://spring.io/) - Testing framework

## Authors

* **Alex Gerasimenko** - *Initial work* - [AlexGera](https://github.com/zobarov)
