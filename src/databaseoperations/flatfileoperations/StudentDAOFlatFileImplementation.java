package databaseoperations.flatfileoperations;

import com.google.gson.Gson;
import databases.flatfile.FlatFileConnection;
import entities.Student;
import interfaces.StudentDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDAOFlatFileImplementation implements StudentDAO {
    @Override
    public Student create(Student student) {
        String path = FlatFileConnection.getFilePath();
        Gson gson = new Gson();
        String studentJSON = gson.toJson(student); // json format of student object
        // System.out.println(studentJSON);

        if (retrieve(student.getId()) == null){ // if student does not exist already
            try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
                long fileLength = output.length();
                output.seek(fileLength);
                output.writeBytes(studentJSON + "\n");
            } catch (IOException ioe){
                System.err.println("File could not be accessed!");
            }
        }

        return  retrieve(student.getId());
    }

    @Override
    public Student retrieve(String studentId) {
        String path = FlatFileConnection.getFilePath();
        Gson gson = new Gson();
        Student student = null;

        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                if(gson.fromJson(element, Student.class).getId().equals(studentId)){ // parsing from json
                    student = gson.fromJson(element, Student.class);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
        }
        return student;
    }

    @Override
    public List<Student> retrieve() {
        List <Student> studentList = new ArrayList<>();
        String path = FlatFileConnection.getFilePath();
        Gson gson = new Gson();

        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                Student student = gson.fromJson(element, Student.class); // parsing from json
                studentList.add(student);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
        }
        return studentList;
    }

    @Override
    public List<Student> retrieve(Predicate<Student> filter) {
        List<Student> studentList = retrieve();
        return studentList.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public Student update(String studentId, Student student) {
        List<Student> studentList = retrieve();

        String path = FlatFileConnection.getFilePath();
        Gson gson = new Gson();

        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            for (Student s : studentList){
                if (s.getId().equals(studentId)){ // updating attribute(s)
                    s.setName(student.getName());
                }
                String studentJSON = gson.toJson(s);
                output.writeBytes(studentJSON + "\n");
            }
        } catch (IOException ioe){
            System.err.println("File could not be accessed!");
        }
        return retrieve(studentId);
    }

    @Override
    public int delete(String studentId) {
        List<Student> studentList = retrieve();
        int rowsAffected = 0;
        for (Student s : studentList){
            if (s.getId().equals(studentId)){
                studentList.remove(s);
                rowsAffected++;
                break;
            }
        }

        String path = FlatFileConnection.getFilePath();
        Gson gson = new Gson();

        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            for (Student s : studentList){
                String studentJSON = gson.toJson(s);
                output.writeBytes(studentJSON + "\n");
            }
            return rowsAffected;
        } catch (IOException ioe){
            System.err.println("File could not be accessed!");
        }
        return 0;
    }

    @Override
    public boolean deleteAll() {
        String path = FlatFileConnection.getFilePath();
        try(PrintWriter pw = new PrintWriter(path)){
            pw.close();
            return true;
        } catch (FileNotFoundException e1) {
            System.err.println("Some error occurred!");
            return false;
        }
    }
}
