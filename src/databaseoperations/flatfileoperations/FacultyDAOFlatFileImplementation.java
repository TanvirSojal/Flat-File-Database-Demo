package databaseoperations.flatfileoperations;

import databases.flatfile.FlatFileConnection;
import entities.Faculty;
import interfaces.FacultyDAO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Predicate;

public class FacultyDAOFlatFileImplementation implements FacultyDAO {
    @Override
    public Faculty create(Faculty faculty) {
        return null;
    }

    @Override
    public Faculty retrieve(String initials) {
        return null;
    }

    @Override
    public List<Faculty> retrieve() {
        return null;
    }

    @Override
    public List<Faculty> retrieve(Predicate<Faculty> filter) {
        return null;
    }

    @Override
    public Faculty update(String initials, Faculty faculty) {
        return null;
    }

    @Override
    public int delete(String initials) {
        return 0;
    }

    @Override
    public boolean deleteAll() {
        String path = FlatFileConnection.getFacultyFilePath();
        try(PrintWriter pw = new PrintWriter(path)){
            pw.close();
            return true;
        } catch (FileNotFoundException e1) {
            System.err.println("Some error occurred!");
            return false;
        }
    }
}
