package interfaces;

import entities.Course;

import java.util.List;
import java.util.function.Predicate;

public interface CourseDAO {
    // CREATE
    Course create(Course course);

    // RETRIEVE
    Course retrieve(String code);
    List<Course> retrieve();
    List<Course> retrieve(Predicate<Course> filter);

    // UPDATE
    Course update(String code, Course course);

    // DELETE
    int delete(String code);
    boolean deleteAll();
}
