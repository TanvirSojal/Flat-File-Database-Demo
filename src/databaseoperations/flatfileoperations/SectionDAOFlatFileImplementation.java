package databaseoperations.flatfileoperations;

import databases.flatfile.FlatFileConnection;
import entities.Section;
import interfaces.SectionDAO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Predicate;

public class SectionDAOFlatFileImplementation implements SectionDAO {
    @Override
    public Section create(Section section) {
        return null;
    }

    @Override
    public Section retrieve(int sectionId) {
        return null;
    }

    @Override
    public List<Section> retrieve() {
        return null;
    }

    @Override
    public List<Section> retrieve(Predicate<Section> filter) {
        return null;
    }

    @Override
    public Section update(int sectionId, Section section) {
        return null;
    }

    @Override
    public int delete(int sectionId) {
        return 0;
    }

    @Override
    public boolean deleteAll() {
        String path = FlatFileConnection.getSectionFilePath();
        try(PrintWriter pw = new PrintWriter(path)){
            pw.close();
            return true;
        } catch (FileNotFoundException e1) {
            System.err.println("Some error occurred!");
            return false;
        }
    }
}
