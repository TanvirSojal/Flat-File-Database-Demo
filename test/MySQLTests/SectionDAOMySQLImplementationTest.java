package MySQLTests;

import databaseoperations.mysqloperations.CourseDAOMySQLImplementation;
import databaseoperations.mysqloperations.FacultyDAOMySQLImplementation;
import databaseoperations.mysqloperations.SectionDAOMySQLImplementation;
import entities.Course;
import entities.Faculty;
import entities.Section;
import interfaces.CourseDAO;
import interfaces.FacultyDAO;
import interfaces.SectionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SectionDAOMySQLImplementationTest {
    private SectionDAO sectionDAO = new SectionDAOMySQLImplementation();
    private FacultyDAO facultyDAO = new FacultyDAOMySQLImplementation();
    private CourseDAO courseDAO = new CourseDAOMySQLImplementation();

    @BeforeEach
    void setUp() {
        courseDAO.deleteAll();
        facultyDAO.deleteAll();
        sectionDAO.deleteAll();
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