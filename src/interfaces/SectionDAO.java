package interfaces;

import entities.Section;

import java.util.List;
import java.util.function.Predicate;

public interface SectionDAO {
    // CREATE
    Section create(Section section);

    // RETRIEVE
    Section retrieve(int sectionId);
    List<Section> retrieve();
    List<Section> retrieve(Predicate<Section> filter);

    // UPDATE
    Section update(int sectionId, Section section);

    // DELETE
    int delete(int sectionId);
    boolean deleteAll();
}
