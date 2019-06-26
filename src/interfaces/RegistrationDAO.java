package interfaces;

import entities.Registration;

import java.util.List;
import java.util.function.Predicate;

public interface RegistrationDAO {
    // CREATE
    Registration create(Registration registration);

    // RETRIEVE
    Registration retrieve(int registrationId);
    List<Registration> retrieve();
    List<Registration> retrieve(Predicate<Registration> filter);

    // UPDATE
    Registration update(int registrationId, Registration registration);

    // DELETE
    int delete(int id);
    boolean deleteAll();
}
