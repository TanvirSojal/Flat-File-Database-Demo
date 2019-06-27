package databaseoperations.flatfileoperations;

import databases.flatfile.FlatFileConnection;
import entities.Registration;
import interfaces.RegistrationDAO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Predicate;

public class RegistrationDAOFlatFileImplementation implements RegistrationDAO {
    @Override
    public Registration create(Registration registration) {
        return null;
    }

    @Override
    public Registration retrieve(int registrationId) {
        return null;
    }

    @Override
    public List<Registration> retrieve() {
        return null;
    }

    @Override
    public List<Registration> retrieve(Predicate<Registration> filter) {
        return null;
    }

    @Override
    public Registration update(int registrationId, Registration registration) {
        return null;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    @Override
    public boolean deleteAll() {
        String path = FlatFileConnection.getRegistrationFilePath();
        try(PrintWriter pw = new PrintWriter(path)){
            pw.close();
            return true;
        } catch (FileNotFoundException e1) {
            System.err.println("Some error occurred!");
            return false;
        }
    }
}
