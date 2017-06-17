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

Typical UseCase. In POSTMAN for example.

1.Submit __POST request  like:
http://localhost:8080/task?taskId=8&duration=100

Got immediate acknowledge response:

{
    "taskName": "8",
    "status": "OK_FOR_EXECUTION",
    "errorMsg": ""
}

2. After few seconds less then 100 hit __GET request like:
http://localhost:8080/task?taskId=8

Got in process response. Note that actual duration is null.

{
    "taskName": "8",
    "duration": 100,
    "actualDuration": null,
    "executionStatus": "IN_PROCESS"
}

2. After 100 seconds hit __GET request again like:
http://localhost:8080/task?taskId=8

Got in process response. Note that actual duration is null.

{
    "taskName": "8",
    "duration": 100,
    "actualDuration": 100,
    "executionStatus": "DONE"
}


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://spring.io/) - Spring Boot, Core, REST, Test 
* [JUnit](https://spring.io/) - Testing framework

## Authors

* **Alex Gerasimenko** - *Initial work* - [AlexGera](https://github.com/zobarov)
