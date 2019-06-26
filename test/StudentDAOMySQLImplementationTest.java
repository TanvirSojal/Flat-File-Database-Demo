import databaseoperations.mysqloperations.StudentDAOMySQLImplementation;
import entities.Student;
import interfaces.StudentDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOMySQLImplementationTest {
    private StudentDAO studentDAO = new StudentDAOMySQLImplementation();;

    @BeforeEach
    void setUp() {
        studentDAO.deleteAll();
    }

    @AfterEach
    void tearDown() {
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

        studentDAO.create(studentList.get(0));
        studentDAO.create(studentList.get(1));
        studentDAO.create(studentList.get(2));

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

        studentDAO.create(studentList.get(0));
        studentDAO.create(studentList.get(1));
        studentDAO.create(studentList.get(2));

        List<Student> retrievedStudentList = studentDAO.retrieve(s -> s.getId().equals("222"));
        assertEquals(1, retrievedStudentList.size());
        assertEquals("test1", retrievedStudentList.get(0).getName());
    }

    @Test
    void testUpdate() {
        Student student = new Student("123", "test");
        studentDAO.create(student);

        Student modifiedStudent = new Student("123", "John");
        studentDAO.update(student.getId(), modifiedStudent);
        assertEquals(modifiedStudent, studentDAO.retrieve(student.getId()));
    }

    @Test
    void testDelete() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("111", "test0"));
        studentList.add(new Student("222", "test1"));
        studentList.add(new Student("333", "test2"));

        studentDAO.create(studentList.get(0));
        studentDAO.create(studentList.get(1));
        studentDAO.create(studentList.get(2));

        int deletedStudents = studentDAO.delete("111");
        assertEquals(1, deletedStudents);
    }

    @Test
    void testDeleteAll() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("111", "test0"));
        studentList.add(new Student("222", "test1"));
        studentList.add(new Student("333", "test2"));

        studentDAO.create(studentList.get(0));
        studentDAO.create(studentList.get(1));
        studentDAO.create(studentList.get(2));

        studentDAO.deleteAll();
        assertEquals(0, studentDAO.retrieve().size());
    }
}