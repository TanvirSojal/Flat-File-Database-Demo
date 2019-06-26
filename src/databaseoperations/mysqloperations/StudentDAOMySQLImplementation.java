package databaseoperations.mysqloperations;

import databases.mysql.DBConnection;
import entities.Student;
import interfaces.StudentDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentDAOMySQLImplementation implements StudentDAO {
    @Override
    public Student create(Student student) {
        String query = String.format("INSERT INTO STUDENT VALUES(?,?)");
        Connection connection = DBConnection.getConnection();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        return retrieve(student.getId());
    }

    @Override
    public Student retrieve(String studentId) {
        Student student = null; // Object to return

        String query = String.format("SELECT name FROM Student WHERE id=?");
        Connection connection = DBConnection.getConnection();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
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

        String query = String.format("SELECT * FROM Student");
        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

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
        String query = String.format("UPDATE Student SET name=? WHERE id=?");
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, studentId);
            preparedStatement.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return retrieve(studentId); // returning the student stored in database
    }

    @Override
    public int delete(String studentId) {
        String query = String.format("DELETE FROM Student WHERE id=?");
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean deleteAll() {
        String query = String.format("DELETE FROM Student");
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }
}
