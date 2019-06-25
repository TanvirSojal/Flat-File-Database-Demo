import databaseoperations.flatfileoperations.StudentDAOFlatFileImplementation;
import databaseoperations.mysqloperations.StudentDAOMySQLImplementation;
import entities.Student;
import interfaces.StudentDAO;

import java.util.List;
import java.util.function.Predicate;

public class Main {
    public void studentTableDemo(){
        System.out.println("MySQL operations of Student Data Access Object");
        System.out.println("----------------------------------------------");

        StudentDAO studentDAOMySQL = new StudentDAOMySQLImplementation(); // MySQL implementation of StudentDAO
        studentDAOMySQL.deleteAll(); // deleting all students if exist

        Student studentMySQL = studentDAOMySQL.create(new Student("2013000000005", "Raju"));
        studentMySQL = studentDAOMySQL.create(new Student("2013100000007", "Kosaraju"));
        studentMySQL = studentDAOMySQL.create(new Student("2013000000015", "Tarjan"));
        studentMySQL = studentDAOMySQL.create(new Student("2013200000105", "Dijkstra"));
        studentMySQL = studentDAOMySQL.create(new Student("2013000000029", "Knuth"));
        studentMySQL = studentDAOMySQL.create(new Student("2013200000055", "Morris"));

        studentMySQL = studentDAOMySQL.update("2013100000007", new Student("2013100000007", "Magician"));

        boolean b = studentDAOMySQL.delete("2013200000055");

        // printing all students
        System.out.println("\nPrinting All students:");
        List<Student> studentListMysQL = studentDAOMySQL.retrieve();
        studentListMysQL.forEach(System.out::println);

        // Printing filtered students
        System.out.println("\nPrinting filtered students:");
        Predicate<Student> p = s -> s.getId().contains("2013");
        List<Student> filteredStudentListMySQL = studentDAOMySQL.retrieve(p);
        filteredStudentListMySQL.forEach(System.out::println);


        System.out.println();
        System.out.println("Flat File operations of Student Data Access Object");
        System.out.println("--------------------------------------------------");

        StudentDAO studentDAOFlatFile = new StudentDAOFlatFileImplementation(); // FlatFile implementation of StudentDAO
        studentDAOFlatFile.deleteAll(); // deleting all students if exist

        Student studentFlatFile = studentDAOFlatFile.create(new Student("567", "dummy"));
        studentFlatFile = studentDAOFlatFile.create(new Student("234", "Black"));
        studentFlatFile = studentDAOFlatFile.create(new Student("2013000000005", "Raju"));
        studentFlatFile = studentDAOFlatFile.create(new Student("2013100000007", "Kosaraju"));
        studentFlatFile = studentDAOFlatFile.create(new Student("2013000000015", "Tarjan"));
        studentFlatFile = studentDAOFlatFile.create(new Student("2013200000105", "Dijkstra"));
        studentFlatFile = studentDAOFlatFile.create(new Student("2013000000029", "Knuth"));
        studentFlatFile = studentDAOFlatFile.create(new Student("2013200000055", "Morris"));
        studentFlatFile = studentDAOFlatFile.update("2013100000007", new Student("2013100000007", "Mosa"));
        studentFlatFile = studentDAOFlatFile.update("2013200000105", new Student("2013200000105", "Duke"));

        boolean d = studentDAOFlatFile.delete("234");

        // printing all students
        System.out.println("\nPrinting All students:");
        List<Student> studentListFile = studentDAOFlatFile.retrieve();
        studentListFile.forEach(System.out::println);

        // Printing filtered students
        System.out.println("\nPrinting filtered students:");
        Predicate<Student> q = s -> s.getId().contains("2013");
        List<Student> filteredStudentListFile = studentDAOFlatFile.retrieve(q);
        filteredStudentListFile.forEach(System.out::println);
    }
    public Main(){
        System.out.println("Hello GitHub!");
    }

    public static void main(String[] args) {
        new Main();
    }
}
