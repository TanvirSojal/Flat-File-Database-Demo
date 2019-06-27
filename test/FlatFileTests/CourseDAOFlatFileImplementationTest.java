package FlatFileTests;

import databaseoperations.flatfileoperations.CourseDAOFlatFileImplementation;
import entities.Course;
import interfaces.CourseDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOFlatFileImplementationTest {
    private CourseDAO courseDAO = new CourseDAOFlatFileImplementation();

    @BeforeEach
    void setUp() {
        courseDAO.deleteAll();
    }

    @Test
    void testCreate() {
        Course course = new Course("CSE101", "Computer Fundamentals", 3.0);
        Course createdCourse = courseDAO.create(course);
        assertEquals(course, createdCourse);
    }

    @Test
    void testRetrieveByCode() {
        Course course = new Course("CSE101", "Computer Fundamentals", 3.0);
        courseDAO.create(course);
        Course retrievedCourse = courseDAO.retrieve("CSE101");
        assertEquals(course, retrievedCourse);
    }

    @Test
    void testRetrieveAll() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("CSE101", "Computer Fundamentals", 3.0));
        courseList.add(new Course("CSE102", "Electrical Brain", 1.5));
        courseList.add(new Course("CSE301", "Compiler Construction", 3.5));

        for (Course course : courseList)
            courseDAO.create(course);

        List<Course> retrievedCourseList = courseDAO.retrieve();
        assertEquals(courseList.size(), retrievedCourseList.size());
        for (int i = 0; i < courseList.size(); i++)
            assertEquals(courseList.get(i), retrievedCourseList.get(i));
    }

    @Test
    void testRetrieveByFilter() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("CSE101", "Computer Fundamentals", 3.0));
        courseList.add(new Course("CSE102", "Electrical Brain", 1.5));
        courseList.add(new Course("CSE301", "Compiler Construction", 3.5));

        for (Course course : courseList)
            courseDAO.create(course);

        List<Course> retrievedCourseList = courseDAO.retrieve(course -> course.getTitle().contains("Brain"));
        assertEquals(1, retrievedCourseList.size());
        assertEquals(courseList.get(1), retrievedCourseList.get(0));

    }

    @Test
    void testUpdate() {
        Course course = new Course("CSE101", "Computer Fundamentals", 3.0);
        courseDAO.create(course);
        Course modifiedCourse = new Course("CSE101", "Introduction to CS", 3.5);
        Course updatedCourse = courseDAO.update("CSE101", modifiedCourse);
        assertEquals(modifiedCourse, updatedCourse);
    }

    @Test
    void testDelete() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("CSE101", "Computer Fundamentals", 3.0));
        courseList.add(new Course("CSE102", "Electrical Brain", 1.5));
        courseList.add(new Course("CSE301", "Compiler Construction", 3.5));

        for (Course course : courseList)
            courseDAO.create(course);

        int deletedCourses = courseDAO.delete("CSE102");
        assertEquals(1, deletedCourses);
    }

    @Test
    void testDeleteAll() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("CSE101", "Computer Fundamentals", 3.0));
        courseList.add(new Course("CSE102", "Electrical Brain", 1.5));
        courseList.add(new Course("CSE301", "Compiler Construction", 3.5));

        for (Course course : courseList)
            courseDAO.create(course);
        courseDAO.deleteAll();
        assertEquals(0, courseDAO.retrieve().size());
    }
}