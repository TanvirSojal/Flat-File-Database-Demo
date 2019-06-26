import databaseoperations.mysqloperations.RegistrationDAOMySQLImplementation;
import interfaces.RegistrationDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationDAOMySQLImplementationTest {
    RegistrationDAO registrationDAO = new RegistrationDAOMySQLImplementation();

    @BeforeEach
    void setUp() {
        registrationDAO.deleteAll();
    }

    @Test
    void testCreate() {
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