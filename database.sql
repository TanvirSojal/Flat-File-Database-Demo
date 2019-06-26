CREATE DATABASE RegistrationDB;
USE RegistrationDB;

CREATE TABLE Student(
    id VARCHAR(13) PRIMARY KEY,
    name VARCHAR(60) NOT NULL
);

CREATE TABLE Faculty(
    initials VARCHAR(4) PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    rank VARCHAR(20)
);

CREATE TABLE Course(
    code VARCHAR(8) PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    credit DOUBLE
);

CREATE TABLE Section(
    id INT PRIMARY KEY AUTO_INCREMENT,
    sectionNumber INT,
    semester INT,
    seatLimit INT,
    code VARCHAR(8),
    initials VARCHAR(4),

    FOREIGN KEY (code) REFERENCES Course(code),
    FOREIGN KEY (initials) REFERENCES Faculty(initials)
);

CREATE TABLE REGISTRATION(
    id INT PRIMARY KEY AUTO_INCREMENT,
    studentId VARCHAR(13) NOT NULL,
    sectionId INT NOT NULL,

    FOREIGN KEY (studentId) REFERENCES Student(id),
    FOREIGN KEY (sectionId) REFERENCES Section(id)
);