package databases.flatfile;

import java.io.File;
import java.io.IOException;

public class FlatFileConnection {
    private static String studentFile = "student.csv";
    private static String facultyFile = "faculty.csv";
    private static String courseFile = "course.csv";
    private static String sectionFile = "section.csv";
    private static String registrationFile = "registration.csv";

    private static String studentFilePath = "src" + File.separator + "databases" + File.separator + "flatfile" + File.separator + "files" + File.separator + studentFile;
    private static String facultyFilePath = "src" + File.separator + "databases" + File.separator + "flatfile" + File.separator + "files" + File.separator + facultyFile;
    private static String courseFilePath = "src" + File.separator + "databases" + File.separator + "flatfile" + File.separator + "files" + File.separator + courseFile;
    private static String sectionFilePath = "src" + File.separator + "databases" + File.separator + "flatfile" + File.separator + "files" + File.separator + sectionFile;
    private static String registrationFilePath = "src" + File.separator + "databases" + File.separator + "flatfile" + File.separator + "files" + File.separator + registrationFile;

    private final static FlatFileConnection INSTANCE = new FlatFileConnection();
    private FlatFileConnection(){

        try {
            File file = new File(studentFilePath);
            if (file.createNewFile()){
                System.out.println("Created Student File.");
            }
            file = new File(facultyFilePath);
            if (file.createNewFile()){
                System.out.println("Created Faculty File.");
            }
            file = new File(courseFilePath);
            if (file.createNewFile()){
                System.out.println("Created Course File.");
            }
            file = new File(sectionFilePath);
            if (file.createNewFile()){
                System.out.println("Created Section File.");
            }
            file = new File(registrationFilePath);
            if (file.createNewFile()){
                System.out.println("Created Registration File.");
            }
        } catch (IOException e) {
            System.err.println("File exists/could not create!");
        }
    }

    public static String getStudentFilePath(){
        return studentFilePath;
    }

    public static String getFacultyFilePath() {
        return facultyFilePath;
    }

    public static String getCourseFilePath() {
        return courseFilePath;
    }

    public static String getSectionFilePath() {
        return sectionFilePath;
    }

    public static String getRegistrationFilePath() {
        return registrationFilePath;
    }
}
