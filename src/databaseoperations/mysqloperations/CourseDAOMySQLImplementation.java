package databaseoperations.mysqloperations;

import databases.mysql.DBConnection;
import entities.Course;
import interfaces.CourseDAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CourseDAOMySQLImplementation implements CourseDAO {
    private Connection connection;
    private PreparedStatement preparedStatementCreate;
    private PreparedStatement preparedStatementRetrieve;
    private PreparedStatement preparedStatementRetrieveAll;
    private PreparedStatement preparedStatementUpdate;
    private PreparedStatement preparedStatementDelete;
    private PreparedStatement preparedStatementDeleteAll;

    public CourseDAOMySQLImplementation(){
        try{
            connection = DBConnection.getConnection();

            InputStream inputStream = getClass().getResourceAsStream("resources/query_course.properties");
            InputStreamReader fileReader = new InputStreamReader(inputStream);

            Properties properties = new Properties();
            properties.load(fileReader);

            preparedStatementCreate = connection.prepareStatement(properties.getProperty("COURSE_INSERT_QUERY"));
            preparedStatementRetrieve = connection.prepareStatement(properties.getProperty("COURSE_RETRIEVE_QUERY"));
            preparedStatementRetrieveAll = connection.prepareStatement(properties.getProperty("COURSE_RETRIEVE_ALL_QUERY"));
            preparedStatementUpdate = connection.prepareStatement(properties.getProperty("COURSE_UPDATE_QUERY"));
            preparedStatementDelete = connection.prepareStatement(properties.getProperty("COURSE_DELETE_QUERY"));
            preparedStatementDeleteAll = connection.prepareStatement(properties.getProperty("COURSE_DELETE_ALL_QUERY"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Course create(Course course) {
        try{
            preparedStatementCreate.setString(1, course.getCode());
            preparedStatementCreate.setString(2, course.getTitle());
            preparedStatementCreate.setDouble(3, course.getCredit());
            preparedStatementCreate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrieve(course.getCode());
    }

    @Override
    public Course retrieve(String code) {
        Course course = null;
        try{
            preparedStatementRetrieve.setString(1, code);
            ResultSet resultSet = preparedStatementRetrieve.executeQuery();
            if(resultSet.next()){
                String title = resultSet.getString("title");
                double credit = resultSet.getDouble("credit");
                course = new Course(code, title, credit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    @Override
    public List<Course> retrieve() {
        List<Course> courseList = new ArrayList<>();

        try{
            ResultSet resultSet = preparedStatementRetrieveAll.executeQuery();

            while(resultSet.next()){
                String code = resultSet.getString("code");
                String title = resultSet.getString("title");
                double credit = resultSet.getDouble("credit");
                Course course = new Course(code, title, credit);
                courseList.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
        try{
            preparedStatementUpdate.setString(1, course.getTitle());
            preparedStatementUpdate.setDouble(2, course.getCredit());
            preparedStatementUpdate.setString(3, code);
            preparedStatementUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrieve(code);
    }

    @Override
    public int delete(String code) {
        try {
            preparedStatementDelete.setString(1, code);
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
