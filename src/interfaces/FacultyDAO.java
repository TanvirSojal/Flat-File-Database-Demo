package interfaces;

import entities.Faculty;

import java.util.List;
import java.util.function.Predicate;

public interface FacultyDAO {
    // CREATE
    Faculty create(Faculty faculty);

    // RETRIEVE
    Faculty retrieve(String initials);
    List<Faculty> retrieve();
    List<Faculty> retrieve(Predicate<Faculty> filter);

    // UPDATE
    Faculty update(String initials, Faculty faculty);

    // DELETE
    int delete(String initials);
    boolean deleteAll();
}
