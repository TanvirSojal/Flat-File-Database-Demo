package FlatFileTests;

import databaseoperations.flatfileoperations.CourseDAOFlatFileImplementation;
import databaseoperations.flatfileoperations.FacultyDAOFlatFileImplementation;
import databaseoperations.flatfileoperations.SectionDAOFlatFileImplementation;
import entities.Course;
import entities.Faculty;
import entities.Section;
import interfaces.CourseDAO;
import interfaces.FacultyDAO;
import interfaces.SectionDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SectionDAOFlatFileImplementationTest {
    private SectionDAO sectionDAO = new SectionDAOFlatFileImplementation();
    private FacultyDAO facultyDAO = new FacultyDAOFlatFileImplementation();
    private CourseDAO courseDAO = new CourseDAOFlatFileImplementation();

    @BeforeEach
    void setUp() {
        courseDAO.deleteAll();
        facultyDAO.deleteAll();
        sectionDAO.deleteAll();
    }

    @Test
    void testToCSV(){
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        Section section = new Section(1, 1, 10, 30, "CSE101", "ABC");
        String sectionCSV = section.toCSV();
        assertEquals("1,1,10,30,CSE101,ABC", sectionCSV);
    }

    @Test
    void testFromCSV(){
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        Section section = new Section(1, 1, 10, 30, "CSE101", "ABC");
        String sectionCSV = section.toCSV();
        Section parsedSection = Section.fromCSV(sectionCSV);
        assertEquals(section, parsedSection);
    }

    @Test
    void testCreate() {
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        Section section = new Section(1, 1, 10, 30, "CSE101", "ABC");
        Section createdSection = sectionDAO.create(section);
        assertEquals(section, createdSection);
    }

    @Test
    void testRetrieveById() {
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        Section section = new Section(1, 1, 10, 30, "CSE101", "ABC");
        sectionDAO.create(section);
        Section retrievedSection = sectionDAO.retrieve(1);
        assertEquals(section, retrievedSection);
    }

    @Test
    void testRetrieveAll() {
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));

        List<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section(1, 1, 10, 30, "CSE101", "ABC"));
        sectionList.add(new Section(2, 2, 10, 35, "CSE101", "ABC"));
        sectionList.add(new Section(3, 3, 10, 25, "CSE101", "ABC"));

        for (Section section : sectionList)
            sectionDAO.create(section);

        List<Section> retrievedSectionList = sectionDAO.retrieve();
        assertEquals(sectionList.size(), retrievedSectionList.size());
        for (int i = 0; i < sectionList.size(); i++)
            assertEquals(sectionList.get(i), retrievedSectionList.get(i));
    }

    @Test
    void testRetrieveByFilter() {
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));

        List<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section(1, 1, 10, 30, "CSE101", "ABC"));
        sectionList.add(new Section(2, 2, 10, 35, "CSE101", "ABC"));
        sectionList.add(new Section(3, 3, 10, 25, "CSE101", "ABC"));

        for (Section section : sectionList)
            sectionDAO.create(section);

        List<Section> retrievedSectionList = sectionDAO.retrieve(section -> section.getSeatLimit() == 35);
        assertEquals(1, retrievedSectionList.size());
        assertEquals(sectionList.get(1), retrievedSectionList.get(0));

    }

    @Test
    void testUpdate() {
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        Section section = new Section(1, 1, 10, 30, "CSE101", "ABC");
        sectionDAO.create(section);

        Section modifiedSection = new Section(1, 1, 12, 25, "CSE101", "ABC");
        Section updatedSection = sectionDAO.update(1, modifiedSection);
        assertEquals(modifiedSection, updatedSection);
    }

    @Test
    void testUpdateWithWrongId() {
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        Section section = new Section(1, 1, 10, 30, "CSE101", "ABC");
        sectionDAO.create(section);

        Section modifiedSection = new Section(1, 1, 12, 25, "CSE101", "ABC");
        Section updatedSection = sectionDAO.update(60, modifiedSection);
        assertNull(updatedSection);
    }

    @Test
    void testDelete() {
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));

        List<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section(1, 1, 10, 30, "CSE101", "ABC"));
        sectionList.add(new Section(2, 2, 10, 35, "CSE101", "ABC"));
        sectionList.add(new Section(3, 3, 10, 25, "CSE101", "ABC"));

        for (Section section : sectionList)
            sectionDAO.create(section);

        int deletedSections = sectionDAO.delete(2);
        assertEquals(1, deletedSections);
    }

    @Test
    void testDeleteNonExistent() {
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));

        List<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section(1, 1, 10, 30, "CSE101", "ABC"));
        sectionList.add(new Section(2, 2, 10, 35, "CSE101", "ABC"));
        sectionList.add(new Section(3, 3, 10, 25, "CSE101", "ABC"));

        for (Section section : sectionList)
            sectionDAO.create(section);

        int deletedSections = sectionDAO.delete(100);
        assertEquals(0, deletedSections);
    }

    @Test
    void testDeleteAll() {
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));

        List<Section> sectionList = new ArrayList<>();
        sectionList.add(new Section(1, 1, 10, 30, "CSE101", "ABC"));
        sectionList.add(new Section(2, 2, 10, 35, "CSE101", "ABC"));
        sectionList.add(new Section(3, 3, 10, 25, "CSE101", "ABC"));

        for (Section section : sectionList)
            sectionDAO.create(section);

        sectionDAO.deleteAll();
        assertEquals(0, sectionDAO.retrieve().size());
    }
}