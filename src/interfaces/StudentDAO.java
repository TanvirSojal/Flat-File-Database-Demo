package interfaces;

import entities.Student;

import java.util.List;
import java.util.function.Predicate;

public interface StudentDAO {
    // CREATE
    Student create(Student student);

    // RETRIEVE
    Student retrieve(String studentId);
    List<Student> retrieve();
    List<Student> retrieve(Predicate<Student> filter);

    // UPDATE
    Student update(String studentId, Student student);

    // DELETE
    int delete(String studentId);
    boolean deleteAll();
}
