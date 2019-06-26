package databaseoperations.mysqloperations;

import databases.mysql.DBConnection;
import entities.Faculty;
import interfaces.FacultyDAO;

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

public class FacultyDAOMySQLImplementation implements FacultyDAO {

    private Connection connection;
    private PreparedStatement preparedStatementCreate;
    private PreparedStatement preparedStatementRetrieve;
    private PreparedStatement preparedStatementRetrieveAll;
    private PreparedStatement preparedStatementUpdate;
    private PreparedStatement preparedStatementDelete;
    private PreparedStatement preparedStatementDeleteAll;

    public FacultyDAOMySQLImplementation(){
        try{
            connection = DBConnection.getConnection();

            InputStream inputStream = getClass().getResourceAsStream("resources/query_faculty.properties");
            InputStreamReader fileReader = new InputStreamReader(inputStream);

            Properties properties = new Properties();
            properties.load(fileReader);

            preparedStatementCreate = connection.prepareStatement(properties.getProperty("FACULTY_INSERT_QUERY"));
            preparedStatementRetrieve = connection.prepareStatement(properties.getProperty("FACULTY_RETRIEVE_QUERY"));
            preparedStatementRetrieveAll = connection.prepareStatement(properties.getProperty("FACULTY_RETRIEVE_ALL_QUERY"));
            preparedStatementUpdate = connection.prepareStatement(properties.getProperty("FACULTY_UPDATE_QUERY"));
            preparedStatementDelete = connection.prepareStatement(properties.getProperty("FACULTY_DELETE_QUERY"));
            preparedStatementDeleteAll = connection.prepareStatement(properties.getProperty("FACULTY_DELETE_ALL_QUERY"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Faculty create(Faculty faculty) {
        try{
            preparedStatementCreate.setString(1, faculty.getInitials());
            preparedStatementCreate.setString(2, faculty.getName());
            preparedStatementCreate.setString(3, faculty.getRank());
            preparedStatementCreate.executeUpdate();

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        return retrieve(faculty.getInitials());
    }

    @Override
    public Faculty retrieve(String initials) {
        Faculty faculty = null; // Object to return
        try{
            preparedStatementRetrieve.setString(1, initials);
            ResultSet resultSet = preparedStatementRetrieve.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String rank = resultSet.getString("rank");
                faculty = new Faculty(initials, name, rank);
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return faculty;
    }

    @Override
    public List<Faculty> retrieve() {
        List<Faculty> facultyList = new ArrayList<>();

        try{
            ResultSet resultSet = preparedStatementRetrieveAll.executeQuery();
            while(resultSet.next()){
                String initials = resultSet.getString("initials");
                String name = resultSet.getString("name");
                String rank = resultSet.getString("rank");
                Faculty faculty = new Faculty(initials, name, rank);
                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facultyList;
    }

    @Override
    public List<Faculty> retrieve(Predicate<Faculty> filter) {
        List<Faculty> facultyList = retrieve();
        return facultyList.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public Faculty update(String initials, Faculty faculty) {
        try{
            preparedStatementUpdate.setString(1, faculty.getName());
            preparedStatementUpdate.setString(2, faculty.getRank());
            preparedStatementUpdate.setString(3, initials);
            preparedStatementUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrieve(initials);
    }

    @Override
    public int delete(String initials) {
        try{
            preparedStatementDelete.setString(1, initials);
            int rowsAffected = preparedStatementDelete.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
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
