package databaseoperations.flatfileoperations;

import databases.flatfile.FlatFileConnection;
import entities.Course;
import interfaces.CourseDAO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Predicate;

public class CourseDAOFlatFileImplementation implements CourseDAO {
    @Override
    public Course create(Course course) {
        return null;
    }

    @Override
    public Course retrieve(String code) {
        return null;
    }

    @Override
    public List<Course> retrieve() {
        return null;
    }

    @Override
    public List<Course> retrieve(Predicate<Course> filter) {
        return null;
    }

    @Override
    public Course update(String code, Course course) {
        return null;
    }

    @Override
    public int delete(String code) {
        return 0;
    }

    @Override
    public boolean deleteAll() {
        String path = FlatFileConnection.getCourseFilePath();
        try(PrintWriter pw = new PrintWriter(path)){
            pw.close();
            return true;
        } catch (FileNotFoundException e1) {
            System.err.println("Some error occurred!");
            return false;
        }
    }
}
