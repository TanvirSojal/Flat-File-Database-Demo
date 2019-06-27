package databaseoperations.flatfileoperations;

import com.google.gson.Gson;
import databases.flatfile.FlatFileConnection;
import entities.Registration;
import interfaces.RegistrationDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RegistrationDAOFlatFileImplementation implements RegistrationDAO {
    private String path;
    private Gson gson;

    public RegistrationDAOFlatFileImplementation(){
        path = FlatFileConnection.getRegistrationFilePath();
        gson = new Gson();
    }

    @Override
    public Registration create(Registration registration) {
        String registrationJSON = gson.toJson(registration);

        if (retrieve(registration.getId()) == null){ // if student does not exist already
            try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
                long fileLength = output.length();
                output.seek(fileLength);
                output.writeBytes(registrationJSON + "\n");
            } catch (FileNotFoundException e) {
                System.err.println("File could not be found!");
            } catch (IOException e) {
                System.err.println("File could not be accessed!");
            }
        }

        return  retrieve(registration.getId());
    }

    @Override
    public Registration retrieve(int registrationId) {
        Registration registration = null;

        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                if(gson.fromJson(element, Registration.class).getId() == registrationId){ // parsing from json
                    registration = gson.fromJson(element, Registration.class);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
        }
        return registration;
    }

    @Override
    public List<Registration> retrieve() {
        List<Registration> registrationList = new ArrayList<>();
        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                Registration registration = gson.fromJson(element, Registration.class); // parsing from json
                registrationList.add(registration);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
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
        List<Registration> registrationList = retrieve();
        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            for (Registration r : registrationList){
                if (r.getId() == registrationId){ // updating attribute(s)
                    r.setStudentId(registration.getStudentId());
                    r.setSectionId(registration.getSectionId());
                }
                String registrationJSON = gson.toJson(r);
                output.writeBytes(registrationJSON + "\n");
            }
        } catch (IOException ioe){
            System.err.println("File could not be accessed!");
        }
        return retrieve(registrationId);
    }

    @Override
    public int delete(int id) {
        List<Registration> registrationList = retrieve();
        int sizeBefore = registrationList.size();
        for (Registration r : registrationList){
            if (r.getId() == id){
                registrationList.remove(r);
                break;
            }
        }

        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            int sizeAfter = 0;
            for (Registration r : registrationList){
                String registrationJSON = gson.toJson(r);
                output.writeBytes(registrationJSON + "\n");
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
        String path = FlatFileConnection.getRegistrationFilePath();
        try(PrintWriter pw = new PrintWriter(path)){
            pw.close();
            return true;
        } catch (FileNotFoundException e1) {
            System.err.println("Some error occurred!");
            return false;
        }
    }
}
