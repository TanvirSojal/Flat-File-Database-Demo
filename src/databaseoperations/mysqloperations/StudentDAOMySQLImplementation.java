package databaseoperations.mysqloperations;

import databaseoperations.Exceptions.DataFormatException;
import databases.mysql.DBConnection;
import entities.Student;
import interfaces.StudentDAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDAOMySQLImplementation implements StudentDAO {
    private Connection connection;
    private PreparedStatement preparedStatementCreate;
    private PreparedStatement preparedStatementRetrieve;
    private PreparedStatement preparedStatementRetrieveAll;
    private PreparedStatement preparedStatementUpdate;
    private PreparedStatement preparedStatementDelete;
    private PreparedStatement preparedStatementDeleteAll;

    public StudentDAOMySQLImplementation(){
        try{
            connection = DBConnection.getConnection();

            InputStream inputStream = getClass().getResourceAsStream("resources/query_student.properties");
            InputStreamReader fileReader = new InputStreamReader(inputStream);

            Properties properties = new Properties();
            properties.load(fileReader);

            preparedStatementCreate = connection.prepareStatement(properties.getProperty("STUDENT_INSERT_QUERY"));
            preparedStatementRetrieve = connection.prepareStatement(properties.getProperty("STUDENT_RETRIEVE_QUERY"));
            preparedStatementRetrieveAll = connection.prepareStatement(properties.getProperty("STUDENT_RETRIEVE_ALL_QUERY"));
            preparedStatementUpdate = connection.prepareStatement(properties.getProperty("STUDENT_UPDATE_QUERY"));
            preparedStatementDelete = connection.prepareStatement(properties.getProperty("STUDENT_DELETE_QUERY"));
            preparedStatementDeleteAll = connection.prepareStatement(properties.getProperty("STUDENT_DELETE_ALL_QUERY"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Student create(Student student) {
        try{
            if (student.getId().length() > 13){
                System.out.println("here");
                throw new DataFormatException("Student ID length can not be more than 13 digits long.");
            }

            preparedStatementCreate.setString(1, student.getId());
            preparedStatementCreate.setString(2, student.getName());
            preparedStatementCreate.executeUpdate();

        } catch (SQLException sqle){
            sqle.printStackTrace();
        } catch (DataFormatException e) {
            System.err.println(e.getMessage());
        }

        return retrieve(student.getId());
    }

    @Override
    public Student retrieve(String studentId) {
        Student student = null; // Object to return
        try{
            preparedStatementRetrieve.setString(1, studentId);
            ResultSet resultSet = preparedStatementRetrieve.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                student = new Student(studentId, name);
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> retrieve() {
        List<Student> studentList = new ArrayList<>(); // Object to return

        try {
            ResultSet resultSet = preparedStatementRetrieveAll.executeQuery();

            while(resultSet.next()){
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                Student student = new Student(id, name);
                studentList.add(student);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
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
        try {
            preparedStatementUpdate.setString(1, student.getName());
            preparedStatementUpdate.setString(2, studentId);
            preparedStatementUpdate.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return retrieve(studentId); // returning the student stored in database
    }

    @Override
    public int delete(String studentId) {
        try {
            preparedStatementDelete.setString(1, studentId);
            int rowsAffected = preparedStatementDelete.executeUpdate();
            return rowsAffected;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean deleteAll() {
        try {
            preparedStatementDeleteAll.executeUpdate();
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }
}
