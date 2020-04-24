# Spring State Machine

[![CircleCI](https://circleci.com/gh/Carla-de-Beer/spring-state-machine.svg?style=svg)](https://circleci.com/gh/Carla-de-Beer/spring-state-machine)

A Spring State Machine demo project using the credit card authorisation process to demonstrate the different state changes.

The card payment process transitions through the following states:
* NEW
* PRE_AUTH
* PRE_AUTH_ERROR
* AUTH
* AUTH_ERROR

The events triggering the state changes are as follows:

* PRE_AUTHORIZE
*  PRE_AUTH_APPROVED
* PRE_AUTH_DECLINED
* AUTHORIZE
* AUTH_APPROVED
* AUTH_DECLINED

Based on the Spring Framework Guru [example](https://www.udemy.com/course/spring-boot-microservices-with-spring-cloud-beginner-to-guru/learn/practice/1132104?start=start-page#overview) on Udemy.


## Requirements
* Java 11
* Spring Boot 2.2.6.RELEASE
* Maven 3.6.2
