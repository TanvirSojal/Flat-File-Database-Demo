import databaseoperations.mysqloperations.SectionDAOMySQLImplementation;
import entities.Section;
import interfaces.SectionDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectionDAOMySQLImplementationTest {
    SectionDAO sectionDAO = new SectionDAOMySQLImplementation();

    @BeforeEach
    void setUp() {
        sectionDAO.deleteAll();
    }

    @Test
    void testCreate() {
        Section section = new Section(1, 1, 10, 30, "CSE101", "ABC");

    }

    @Test
    void testRetrieveById() {
    }

    @Test
    void testRetrieveAll() {
    }

    @Test
    void testRetrieveByFilter() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testDelete() {
    }

    @Test
    void testDeleteAll() {
    }
}