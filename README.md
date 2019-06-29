# Flat-File-Database-Demo

This repository contains Flat-file CRUD operation implementation of 5 model classes along with MySQL implementation. The database required to run this project can be created by running the provided SQL script "database.sql".

## Software used:
 * Intellij IDEA Community Edition 2018.2

 * XAMPP Control Panel v3.2.2 (for monitoring the database)

 * MySQL Server 8.0

## Language used:
 * Java (for coding the backend of the software)

 * SQL (for querying from the database)

## Java Dependencies used:
 * JDK 1.8.0_181

 * MySQL JDBC Driver 5.1.23

## Databases

* MySQL: Edit the properties file located here “src\databases\mysql\resources/db.properties” with your credentials.
* Flat-File: The .csv files will be created here “src\databases\flatfile\files” after first time running the program.

# Model Classes

* Student (id, name)
* Faculty (initials, name, rank)
* Course (code, title, credit)
* Section (id, sectionNumber, semester, seatLimit)
* Registration (id, studentId, sectionId)

# Testing
There are total of 100 test cases in the “Test” package.

## Flat-File Implementation
The model test methods for Flat-File implementation of the DAOs are:
* testToCSV()
* testFromCSV()
* testCreate()
* testRetrieveById()
* testRetrieveAll()
* testRetrieveByFilter()
* testUpdate()
* testUpdateWithWrongId()
* testDelete()
* testDeleteNonExistent()
* testDeleteAll()

## MySQL Implementation
The model test methods for MySQL implementation of the DAOs are:
* testCreate()
* testRetrieveById()
* testRetrieveAll()
* testRetrieveByFilter()
* testUpdate()
* testUpdateWithWrongId()
* testDelete()
* testDeleteNonExistent()
* testDeleteAll()
