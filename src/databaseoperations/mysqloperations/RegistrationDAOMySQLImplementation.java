package databaseoperations.mysqloperations;

import databases.mysql.DBConnection;
import entities.Registration;
import interfaces.RegistrationDAO;

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

public class RegistrationDAOMySQLImplementation implements RegistrationDAO {
    private Connection connection;
    private PreparedStatement preparedStatementCreate;
    private PreparedStatement preparedStatementRetrieve;
    private PreparedStatement preparedStatementRetrieveAll;
    private PreparedStatement preparedStatementUpdate;
    private PreparedStatement preparedStatementDelete;
    private PreparedStatement preparedStatementDeleteAll;

    public RegistrationDAOMySQLImplementation(){
        try{
            connection = DBConnection.getConnection();

            InputStream inputStream = getClass().getResourceAsStream("resources/query_student.properties");
            InputStreamReader fileReader = new InputStreamReader(inputStream);

            Properties properties = new Properties();
            properties.load(fileReader);

            preparedStatementCreate = connection.prepareStatement(properties.getProperty("REGISTRATION_INSERT_QUERY"));
            preparedStatementRetrieve = connection.prepareStatement(properties.getProperty("REGISTRATION_RETRIEVE_QUERY"));
            preparedStatementRetrieveAll = connection.prepareStatement(properties.getProperty("REGISTRATION_RETRIEVE_ALL_QUERY"));
            preparedStatementUpdate = connection.prepareStatement(properties.getProperty("REGISTRATION_UPDATE_QUERY"));
            preparedStatementDelete = connection.prepareStatement(properties.getProperty("REGISTRATION_DELETE_QUERY"));
            preparedStatementDeleteAll = connection.prepareStatement(properties.getProperty("REGISTRATION_DELETE_ALL_QUERY"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Registration create(Registration registration) {
        try{
            preparedStatementCreate.setInt(1, registration.getId());
            preparedStatementCreate.setString(2, registration.getStudentId());
            preparedStatementCreate.setInt(3, registration.getSectionId());
            preparedStatementCreate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrieve(registration.getId());
    }

    @Override
    public Registration retrieve(int registrationId) {
        Registration registration = null;
        try{
            preparedStatementRetrieve.setInt(1, registrationId);
            ResultSet resultSet = preparedStatementRetrieve.executeQuery();
            if (resultSet.next()){
                String studentId = resultSet.getString("studentId");
                int sectionId = resultSet.getInt("sectionId");
                registration = new Registration(registrationId, studentId, sectionId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registration;
    }

    @Override
    public List<Registration> retrieve() {
        List<Registration> registrationList = new ArrayList<>();
        try{
            ResultSet resultSet = preparedStatementRetrieveAll.executeQuery();
            while(resultSet.next()){
                int registrationId = resultSet.getInt("id");
                String studentId = resultSet.getString("studentId");
                int sectionId = resultSet.getInt("sectionId");
                Registration registration = new Registration(registrationId, studentId, sectionId);
                registrationList.add(registration);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrationList;
    }

    @Override
    public List<Registration> retrieve(Predicate<Registration> filter) {
        List<Registration> registrationList = retrieve();
        return registrationList.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public Registration update(int registrationId, Registration registration) {
        try{
            preparedStatementUpdate.setString(1, registration.getStudentId());
            preparedStatementUpdate.setInt(2, registration.getSectionId());
            preparedStatementUpdate.setInt(3, registrationId);
            preparedStatementUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrieve(registrationId);
    }

    @Override
    public int delete(int registrationId) {
        try {
            preparedStatementDelete.setInt(1, registrationId);
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
