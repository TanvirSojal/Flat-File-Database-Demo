package databaseoperations.flatfileoperations;

import com.google.gson.Gson;
import databases.flatfile.FlatFileConnection;
import entities.Faculty;
import interfaces.FacultyDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FacultyDAOFlatFileImplementation implements FacultyDAO {
    private String path;
    private Gson gson;

    public FacultyDAOFlatFileImplementation(){
        path = FlatFileConnection.getFacultyFilePath();
        gson = new Gson();
    }

    @Override
    public Faculty create(Faculty faculty) {
        String facultyJson = gson.toJson(faculty);

        if (retrieve(faculty.getInitials()) == null){
            try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
                long fileLength = output.length();
                output.seek(fileLength);
                output.writeBytes(facultyJson + "\n");
            } catch (FileNotFoundException e) {
                System.err.println("File could not be found!");
            } catch (IOException e) {
                System.err.println("File could not be accessed!");
            }
        }
        return retrieve(faculty.getInitials());
    }

    @Override
    public Faculty retrieve(String initials) {
        Faculty faculty = null;

        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                if(gson.fromJson(element, Faculty.class).getInitials().equals(initials)){ // parsing from json
                    faculty = gson.fromJson(element, Faculty.class);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
        }
        return faculty;
    }

    @Override
    public List<Faculty> retrieve() {
        List<Faculty> facultyList = new ArrayList<>();

        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                Faculty faculty = gson.fromJson(element, Faculty.class); // parsing from json
                facultyList.add(faculty);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
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
        List<Faculty> facultyList = retrieve();

        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            for (Faculty f : facultyList){
                if (f.getInitials().equals(initials)){ // updating attribute(s)
                    f.setName(faculty.getName());
                    f.setRank(faculty.getRank());
                }
                String facultyJson = gson.toJson(f);
                output.writeBytes(facultyJson + "\n");
            }
        } catch (IOException ioe){
            System.err.println("File could not be accessed!");
        }
        return retrieve(initials);
    }

    @Override
    public int delete(String initials) {
        List<Faculty> facultyList = retrieve();
        int sizeBefore = facultyList.size();
        for (Faculty f : facultyList){
            if (f.getInitials().equals(initials)){
                facultyList.remove(f);
                break;
            }
        }

        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            int sizeAfter = 0;
            for (Faculty s : facultyList){
                String facultyJson = gson.toJson(s);
                output.writeBytes(facultyJson + "\n");
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
