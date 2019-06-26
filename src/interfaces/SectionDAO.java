package interfaces;

import entities.Section;

import java.util.List;
import java.util.function.Predicate;

public interface SectionDAO {
    // CREATE
    Section create(Section section);

    // RETRIEVE
    Section retrieve(int id);
    List<Section> retrieve();
    List<Section> retrieve(Predicate<Section> filter);

    // UPDATE
    Section update(int id, Section section);

    // DELETE
    int delete(int id);
    boolean deleteAll();
}
