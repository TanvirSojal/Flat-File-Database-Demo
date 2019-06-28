package FlatFileTests;

import databaseoperations.flatfileoperations.FacultyDAOFlatFileImplementation;
import entities.Course;
import entities.Faculty;
import interfaces.FacultyDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyDAOFlatFileImplementationTest {
    private FacultyDAO facultyDAO = new FacultyDAOFlatFileImplementation();

    @BeforeEach
    void setUp() {
        facultyDAO.deleteAll();
    }

    @Test
    void testToCSV(){
        Faculty faculty = new Faculty("ABC", "Another Boring Computer", "Lecturer");
        String facultyCSV = faculty.toCSV();
        assertEquals("ABC,Another Boring Computer,Lecturer", facultyCSV);
    }

    @Test
    void testFromCSV(){
        Faculty faculty = new Faculty("ABC", "Another Boring Computer", "Lecturer");
        String facultyCSV = faculty.toCSV();
        Faculty parsedFaculty = Faculty.fromCSV(facultyCSV);
        assertEquals(faculty, parsedFaculty);
    }

    @Test
    void testCreate() {
        Faculty faculty = new Faculty("ABC", "Another Boring Computer", "Lecturer");
        Faculty createdFaculty = facultyDAO.create(faculty);
        assertEquals(faculty, createdFaculty);
    }


    @Test
    void testRetrieveByInitials() {
        Faculty faculty = new Faculty("ABC", "Another Boring Computer", "Lecturer");
        facultyDAO.create(faculty);
        Faculty retrievedFaculty = facultyDAO.retrieve("ABC");
        assertEquals(faculty, retrievedFaculty);
    }

    @Test
    void testRetrieveAll() {
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty("AA", "Aaron Archer", "Senior Lecturer"));
        facultyList.add(new Faculty("BB", "Ben Boxer", "Professor"));
        facultyList.add(new Faculty("CC", "Cassy Catherine", "Lecturer"));

        for (Faculty faculty : facultyList)
            facultyDAO.create(faculty);

        List<Faculty> retrievedFacultyList = facultyDAO.retrieve();
        assertEquals(facultyList.size(), retrievedFacultyList.size());

        for (int i = 0; i < facultyList.size(); i++)
            assertEquals(facultyList.get(i), retrievedFacultyList.get(i));
    }

    @Test
    void testRetrieveByFilter() {
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty("AA", "Aaron Archer", "Senior Lecturer"));
        facultyList.add(new Faculty("BB", "Ben Boxer", "Professor"));
        facultyList.add(new Faculty("CC", "Cassy Catherine", "Lecturer"));

        for (Faculty faculty : facultyList)
            facultyDAO.create(faculty);

        List<Faculty> retrievedFacultyList = facultyDAO.retrieve(faculty -> faculty.getInitials().equals("BB"));
        assertEquals(1, retrievedFacultyList.size());
        assertEquals(facultyList.get(1), retrievedFacultyList.get(0));
    }

    @Test
    void testUpdate() {
        Faculty faculty = new Faculty("ABC", "Another Boring Computer", "Lecturer");
        facultyDAO.create(faculty);
        Faculty modifiedFaculty = new Faculty("ABC", "Awesome Big Computer", "Professor");
        Faculty updatedFaculty = facultyDAO.update("ABC", modifiedFaculty);
        assertEquals(modifiedFaculty, updatedFaculty);
    }

    @Test
    void testUpdateWithWrongId() {
        Faculty faculty = new Faculty("ABC", "Another Boring Computer", "Lecturer");
        facultyDAO.create(faculty);
        Faculty modifiedFaculty = new Faculty("ABC", "Awesome Big Computer", "Professor");
        Faculty updatedFaculty = facultyDAO.update("XYZ", modifiedFaculty);
        assertNull(updatedFaculty); // the modification will not affect, will return null
    }

    @Test
    void testDelete() {
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty("AA", "Aaron Archer", "Senior Lecturer"));
        facultyList.add(new Faculty("BB", "Ben Boxer", "Professor"));
        facultyList.add(new Faculty("CC", "Cassy Catherine", "Lecturer"));

        for (Faculty faculty : facultyList)
            facultyDAO.create(faculty);

        int deletedFaculties = facultyDAO.delete("AA");
        assertEquals(1, deletedFaculties);
    }

    @Test
    void testDeleteNonExistent() {
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty("AA", "Aaron Archer", "Senior Lecturer"));
        facultyList.add(new Faculty("BB", "Ben Boxer", "Professor"));
        facultyList.add(new Faculty("CC", "Cassy Catherine", "Lecturer"));

        for (Faculty faculty : facultyList)
            facultyDAO.create(faculty);

        int deletedFaculties = facultyDAO.delete("EE");
        assertEquals(0, deletedFaculties);
    }

    @Test
    void testDeleteAll() {
        List<Faculty> facultyList = new ArrayList<>();
        facultyList.add(new Faculty("AA", "Aaron Archer", "Senior Lecturer"));
        facultyList.add(new Faculty("BB", "Ben Boxer", "Professor"));
        facultyList.add(new Faculty("CC", "Cassy Catherine", "Lecturer"));

        for (Faculty faculty : facultyList)
            facultyDAO.create(faculty);

        facultyDAO.deleteAll();
        assertEquals(0, facultyDAO.retrieve().size());
    }
}