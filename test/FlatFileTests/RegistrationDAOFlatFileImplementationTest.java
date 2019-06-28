package FlatFileTests;

import databaseoperations.flatfileoperations.*;
import entities.*;
import interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationDAOFlatFileImplementationTest {
    private StudentDAO studentDAO = new StudentDAOFlatFileImplementation();
    private CourseDAO courseDAO = new CourseDAOFlatFileImplementation();
    private FacultyDAO facultyDAO = new FacultyDAOFlatFileImplementation();
    private SectionDAO sectionDAO = new SectionDAOFlatFileImplementation();
    private RegistrationDAO registrationDAO = new RegistrationDAOFlatFileImplementation();

    @BeforeEach
    void setUp() {
        studentDAO.deleteAll();
        courseDAO.deleteAll();
        facultyDAO.deleteAll();
        sectionDAO.deleteAll();
        registrationDAO.deleteAll();
    }

    @Test
    void testCreate() {
        studentDAO.create(new Student("123", "test"));
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        sectionDAO.create(new Section(1, 1, 10, 30, "CSE101", "ABC"));

        Registration registration = new Registration(1, "123", 1);
        Registration createdRegistration = registrationDAO.create(registration);
        assertEquals(registration, createdRegistration);
    }

    @Test
    void testRetrieveById() {
        studentDAO.create(new Student("123", "test"));
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        sectionDAO.create(new Section(1, 1, 10, 30, "CSE101", "ABC"));

        Registration registration = new Registration(1, "123", 1);
        registrationDAO.create(registration);
        Registration retrievedRegistration = registrationDAO.retrieve(1);
        assertEquals(registration, retrievedRegistration);
    }

    @Test
    void testRetrieveAll() {
        studentDAO.create(new Student("111", "test0"));
        studentDAO.create(new Student("222", "test1"));
        studentDAO.create(new Student("333", "test2"));
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        sectionDAO.create(new Section(1, 1, 10, 30, "CSE101", "ABC"));

        List<Registration> registrationList = new ArrayList<>();
        registrationList.add(new Registration(1, "111", 1));
        registrationList.add(new Registration(2, "222", 1));
        registrationList.add(new Registration(3, "333", 1));

        for (Registration registration : registrationList)
            registrationDAO.create(registration);

        List<Registration> retrievedRegistrationList = registrationDAO.retrieve();

        assertEquals(registrationList.size(), retrievedRegistrationList.size());
        for (int i = 0; i < registrationList.size(); i++)
            assertEquals(registrationList.get(i), retrievedRegistrationList.get(i));
    }

    @Test
    void testRetrieveByFilter() {
        studentDAO.create(new Student("111", "test0"));
        studentDAO.create(new Student("222", "test1"));
        studentDAO.create(new Student("333", "test2"));
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        sectionDAO.create(new Section(1, 1, 10, 30, "CSE101", "ABC"));

        List<Registration> registrationList = new ArrayList<>();
        registrationList.add(new Registration(1, "111", 1));
        registrationList.add(new Registration(2, "222", 1));
        registrationList.add(new Registration(3, "333", 1));

        for (Registration registration : registrationList)
            registrationDAO.create(registration);

        List<Registration> retrievedRegistrationList = registrationDAO.retrieve(registration -> registration.getStudentId().equals("222"));
        assertEquals(1, retrievedRegistrationList.size());
        assertEquals(registrationList.get(1), retrievedRegistrationList.get(0));
    }

    @Test
    void testUpdate() {
        studentDAO.create(new Student("123", "test"));
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        sectionDAO.create(new Section(1, 1, 10, 30, "CSE101", "ABC"));
        sectionDAO.create(new Section(2, 1, 10, 35, "CSE101", "ABC"));

        Registration registration = new Registration(1, "123", 1);
        registrationDAO.create(registration);

        Registration modifiedRegistration = new Registration(1, "123", 2);
        Registration updatedRegistration = registrationDAO.update(1, modifiedRegistration);
        assertEquals(modifiedRegistration, updatedRegistration);
    }

    @Test
    void testUpdateWithWrongId() {
        studentDAO.create(new Student("123", "test"));
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        sectionDAO.create(new Section(1, 1, 10, 30, "CSE101", "ABC"));
        sectionDAO.create(new Section(2, 1, 10, 35, "CSE101", "ABC"));

        Registration registration = new Registration(1, "123", 1);
        registrationDAO.create(registration);

        Registration modifiedRegistration = new Registration(1, "123", 2);
        Registration updatedRegistration = registrationDAO.update(50, modifiedRegistration);
        assertNull(updatedRegistration);
    }

    @Test
    void testDelete() {
        studentDAO.create(new Student("111", "test0"));
        studentDAO.create(new Student("222", "test1"));
        studentDAO.create(new Student("333", "test2"));
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        sectionDAO.create(new Section(1, 1, 10, 30, "CSE101", "ABC"));

        List<Registration> registrationList = new ArrayList<>();
        registrationList.add(new Registration(1, "111", 1));
        registrationList.add(new Registration(2, "222", 1));
        registrationList.add(new Registration(3, "333", 1));

        for (Registration registration : registrationList)
            registrationDAO.create(registration);

        int deletedRegistration = registrationDAO.delete(2);
        assertEquals(1, deletedRegistration);
    }

    @Test
    void testDeleteNonExistent() {
        studentDAO.create(new Student("111", "test0"));
        studentDAO.create(new Student("222", "test1"));
        studentDAO.create(new Student("333", "test2"));
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        sectionDAO.create(new Section(1, 1, 10, 30, "CSE101", "ABC"));

        List<Registration> registrationList = new ArrayList<>();
        registrationList.add(new Registration(1, "111", 1));
        registrationList.add(new Registration(2, "222", 1));
        registrationList.add(new Registration(3, "333", 1));

        for (Registration registration : registrationList)
            registrationDAO.create(registration);

        int deletedRegistration = registrationDAO.delete(10);
        assertEquals(0, deletedRegistration);
    }

    @Test
    void testDeleteAll() {
        studentDAO.create(new Student("111", "test0"));
        studentDAO.create(new Student("222", "test1"));
        studentDAO.create(new Student("333", "test2"));
        courseDAO.create(new Course("CSE101", "Computer Fundamentals", 3.0));
        facultyDAO.create(new Faculty("ABC", "Another Boring Computer", "Lecturer"));
        sectionDAO.create(new Section(1, 1, 10, 30, "CSE101", "ABC"));

        List<Registration> registrationList = new ArrayList<>();
        registrationList.add(new Registration(1, "111", 1));
        registrationList.add(new Registration(2, "222", 1));
        registrationList.add(new Registration(3, "333", 1));

        for (Registration registration : registrationList)
            registrationDAO.create(registration);

        registrationDAO.deleteAll();
        assertEquals(0, registrationDAO.retrieve().size());

    }
}