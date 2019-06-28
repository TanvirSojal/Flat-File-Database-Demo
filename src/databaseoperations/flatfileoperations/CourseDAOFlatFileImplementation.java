package databaseoperations.flatfileoperations;

import com.google.gson.Gson;
import databases.flatfile.FlatFileConnection;
import entities.Course;
import interfaces.CourseDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CourseDAOFlatFileImplementation implements CourseDAO {
    private String path;

    public CourseDAOFlatFileImplementation(){
        path = FlatFileConnection.getCourseFilePath();
    }

    @Override
    public Course create(Course course) {
        String courseCSV = course.toCSV();

        if (retrieve(course.getCode()) == null){
            try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
                long fileLength = output.length();
                output.seek(fileLength);
                output.writeBytes(courseCSV + "\n");
            } catch (IOException ioe){
                System.err.println("File could not be accessed!");
            }
        }

        return  retrieve(course.getCode());
    }

    @Override
    public Course retrieve(String code) {
        Course course = null;
        try(RandomAccessFile input = new RandomAccessFile(path, "r")){
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                if(Course.fromCSV(element).getCode().equals(code)){ // parsing from csv
                    course = Course.fromCSV(element);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
        }
        return course;
    }

    @Override
    public List<Course> retrieve() {
        List<Course> courseList = new ArrayList<>();
        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                Course course = Course.fromCSV(element); // parsing from csv
                courseList.add(course);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
        }
        return courseList;
    }

    @Override
    public List<Course> retrieve(Predicate<Course> filter) {
        List<Course> courseList = retrieve();
        return courseList.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public Course update(String code, Course course) {
        List<Course> courseList = retrieve();
        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            for (Course c : courseList){
                if (c.getCode().equals(code)){ // updating attribute(s)
                    c.setTitle(course.getTitle());
                    c.setCredit(course.getCredit());
                }
                String courseCSV = c.toCSV();
                output.writeBytes(courseCSV + "\n");
            }
        } catch (IOException ioe){
            System.err.println("File could not be accessed!");
        }
        return retrieve(code);
    }

    @Override
    public int delete(String code) {
        List<Course> courseList = retrieve();
        int sizeBefore = courseList.size();
        for (Course c : courseList){
            if (c.getCode().equals(code)){
                courseList.remove(c);
                break;
            }
        }

        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            int sizeAfter = 0;
            for (Course c : courseList){
                String courseCSV = c.toCSV();
                output.writeBytes(courseCSV + "\n");
                sizeAfter++;
            }
            return sizeBefore - sizeAfter;
        } catch (IOException ioe){
            System.err.println("File could not be accessed!");
        }
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
