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

    FOREIGN KEY (code) REFERENCES Course(code) ON DELETE SET NULL,
    FOREIGN KEY (initials) REFERENCES Faculty(initials) ON DELETE SET NULL
);

CREATE TABLE REGISTRATION(
    id INT PRIMARY KEY AUTO_INCREMENT,
    studentId VARCHAR(13),
    sectionId INT,

    FOREIGN KEY (studentId) REFERENCES Student(id) ON DELETE SET NULL,
    FOREIGN KEY (sectionId) REFERENCES Section(id) ON DELETE SET NULL
);