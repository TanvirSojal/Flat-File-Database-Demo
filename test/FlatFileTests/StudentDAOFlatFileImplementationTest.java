package FlatFileTests;

import databaseoperations.flatfileoperations.StudentDAOFlatFileImplementation;
import entities.Student;
import interfaces.StudentDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOFlatFileImplementationTest {
    private StudentDAO studentDAO = new StudentDAOFlatFileImplementation();

    @BeforeEach
    void setUp() {
        studentDAO.deleteAll();
    }

    @Test
    void testCreate() {
        Student student = new Student("123", "test");
        Student createdStudent = studentDAO.create(student);
        assertEquals(student, createdStudent);
    }

    @Test
    void testRetrieveById() {
        Student student = new Student("123", "test");
        studentDAO.create(student);
        Student retrievedStudent = studentDAO.retrieve("123");
        assertEquals(student, retrievedStudent);
    }

    @Test
    void testRetrieveAll() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("111", "test0"));
        studentList.add(new Student("222", "test1"));
        studentList.add(new Student("333", "test2"));

        for (Student student : studentList)
            studentDAO.create(student);

        List<Student> retrievedStudentList = studentDAO.retrieve();
        assertEquals(studentList.size(), retrievedStudentList.size());
        for (int i = 0; i < studentList.size(); i++)
            assertEquals(studentList.get(i), retrievedStudentList.get(i));
    }

    @Test
    void testRetrieveByFilter() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("111", "test0"));
        studentList.add(new Student("222", "test1"));
        studentList.add(new Student("333", "test2"));

        for (Student student : studentList)
            studentDAO.create(student);

        List<Student> retrievedStudentList = studentDAO.retrieve(student -> student.getId().equals("222"));
        assertEquals(1, retrievedStudentList.size());
        assertEquals(studentList.get(1).getName(), retrievedStudentList.get(0).getName());
    }

    @Test
    void testUpdate() {
        Student student = new Student("123", "test");
        studentDAO.create(student);

        Student modifiedStudent = new Student("123", "John");
        Student updatedStudent = studentDAO.update(student.getId(), modifiedStudent);
        assertEquals(modifiedStudent, updatedStudent);
    }

    @Test
    void testDelete() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("111", "test0"));
        studentList.add(new Student("222", "test1"));
        studentList.add(new Student("333", "test2"));

        for (Student student : studentList)
            studentDAO.create(student);

        int deletedStudents = studentDAO.delete("111");
        assertEquals(1, deletedStudents);
    }

    @Test
    void testDeleteAll() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("111", "test0"));
        studentList.add(new Student("222", "test1"));
        studentList.add(new Student("333", "test2"));

        for (Student student : studentList)
            studentDAO.create(student);

        studentDAO.deleteAll();
        assertEquals(0, studentDAO.retrieve().size());
    }
}