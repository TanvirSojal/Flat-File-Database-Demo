package databaseoperations.mysqloperations;

import databases.mysql.DBConnection;
import entities.Section;
import interfaces.SectionDAO;

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

public class SectionDAOMySQLImplementation implements SectionDAO {
    private Connection connection;
    private PreparedStatement preparedStatementCreate;
    private PreparedStatement preparedStatementRetrieve;
    private PreparedStatement preparedStatementRetrieveAll;
    private PreparedStatement preparedStatementUpdate;
    private PreparedStatement preparedStatementDelete;
    private PreparedStatement preparedStatementDeleteAll;

    public SectionDAOMySQLImplementation(){
        try{
            connection = DBConnection.getConnection();

            InputStream inputStream = getClass().getResourceAsStream("resources/query_section.properties");
            InputStreamReader fileReader = new InputStreamReader(inputStream);

            Properties properties = new Properties();
            properties.load(fileReader);

            preparedStatementCreate = connection.prepareStatement(properties.getProperty("SECTION_INSERT_QUERY"));
            preparedStatementRetrieve = connection.prepareStatement(properties.getProperty("SECTION_RETRIEVE_QUERY"));
            preparedStatementRetrieveAll = connection.prepareStatement(properties.getProperty("SECTION_RETRIEVE_ALL_QUERY"));
            preparedStatementUpdate = connection.prepareStatement(properties.getProperty("SECTION_UPDATE_QUERY"));
            preparedStatementDelete = connection.prepareStatement(properties.getProperty("SECTION_DELETE_QUERY"));
            preparedStatementDeleteAll = connection.prepareStatement(properties.getProperty("SECTION_DELETE_ALL_QUERY"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Section create(Section section) {
        try{
            preparedStatementCreate.setInt(1, section.getId());
            preparedStatementCreate.setInt(2, section.getSectionNumber());
            preparedStatementCreate.setInt(3, section.getSemester());
            preparedStatementCreate.setInt(4, section.getSeatLimit());
            preparedStatementCreate.setString(5, section.getCourseCode());
            preparedStatementCreate.setString(6, section.getInitials());
            preparedStatementCreate.executeUpdate();

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        return retrieve(section.getId());
    }

    @Override
    public Section retrieve(int sectionId) {
        Section section = null;
        try{
            preparedStatementRetrieve.setInt(1, sectionId);
            ResultSet resultSet = preparedStatementRetrieve.executeQuery();
            if (resultSet.next()){
                int sectionNumber = resultSet.getInt("sectionNumber");
                int semester = resultSet.getInt("semester");
                int seatLimit = resultSet.getInt("seatLimit");
                String courseCode = resultSet.getString("code");
                String facultyInitials = resultSet.getString("initials");
                section = new Section(sectionId, sectionNumber, semester, seatLimit, courseCode, facultyInitials);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return section;
    }

    @Override
    public List<Section> retrieve() {
        List<Section> sectionList = new ArrayList<>();
        try{
            ResultSet resultSet = preparedStatementRetrieveAll.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                int sectionNumber = resultSet.getInt("sectionNumber");
                int semester = resultSet.getInt("semester");
                int seatLimit = resultSet.getInt("seatLimit");
                String courseCode = resultSet.getString("code");
                String facultyInitials = resultSet.getString("initials");
                Section section = new Section(id, sectionNumber, semester, seatLimit, courseCode, facultyInitials);
                sectionList.add(section);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectionList;
    }

    @Override
    public List<Section> retrieve(Predicate<Section> filter) {
        List<Section> sectionList = retrieve();
        return sectionList.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public Section update(int sectionId, Section section) {
        try{
            preparedStatementUpdate.setInt(1, section.getSectionNumber());
            preparedStatementUpdate.setInt(2, section.getSemester());
            preparedStatementUpdate.setInt(3, section.getSeatLimit());
            preparedStatementUpdate.setString(4, section.getCourseCode());
            preparedStatementUpdate.setString(5, section.getInitials());
            preparedStatementUpdate.setInt(6, sectionId);
            preparedStatementUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retrieve(sectionId);
    }

    @Override
    public int delete(int sectionId) {
        try {
            preparedStatementDelete.setInt(1, sectionId);
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
