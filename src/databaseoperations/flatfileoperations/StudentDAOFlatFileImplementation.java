package databaseoperations.flatfileoperations;

import databaseoperations.Exceptions.DataFormatException;
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
    private String path;

    public StudentDAOFlatFileImplementation(){
         path = FlatFileConnection.getStudentFilePath();
    }

    @Override
    public Student create(Student student) {
        try{
            if (student.getId().length() > 13){
                throw new DataFormatException("Student ID length can not be more than 13 digits long.");
            }
        } catch (DataFormatException e) {
            System.err.println(e.getMessage());
        }

        String studentCSV = student.toCSV(); // CSV format of Student object

        if (retrieve(student.getId()) == null){ // if student does not exist already
            try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
                long fileLength = output.length();
                output.seek(fileLength);
                output.writeBytes(studentCSV + "\n");
            } catch (FileNotFoundException e) {
                System.err.println("File could not be found!");
            } catch (IOException e) {
                System.err.println("File could not be accessed!");
            }
        }

        return  retrieve(student.getId());
    }

    @Override
    public Student retrieve(String studentId) {
        Student student = null;
        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                if(Student.fromCSV(element).getId().equals(studentId)){ // parsing from CSV
                    student = Student.fromCSV(element);
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
        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                Student student = Student.fromCSV(element); // parsing from CSV
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
        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            for (Student s : studentList){
                if (s.getId().equals(studentId)){ // updating attribute(s)
                    s.setName(student.getName());
                }
                String studentCSV = s.toCSV();
                output.writeBytes(studentCSV + "\n");
            }
        } catch (IOException ioe){
            System.err.println("File could not be accessed!");
        }
        return retrieve(studentId);
    }

    @Override
    public int delete(String studentId) {
        List<Student> studentList = retrieve();
        int sizeBefore = studentList.size();
        for (Student s : studentList){
            if (s.getId().equals(studentId)){
                studentList.remove(s);
                break;
            }
        }

        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            int sizeAfter = 0;
            for (Student s : studentList){
                String studentCSV = s.toCSV();
                output.writeBytes(studentCSV + "\n");
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
        try(PrintWriter pw = new PrintWriter(path)){
            pw.close();
            return true;
        } catch (FileNotFoundException e1) {
            System.err.println("Some error occurred!");
            return false;
        }
    }
}
