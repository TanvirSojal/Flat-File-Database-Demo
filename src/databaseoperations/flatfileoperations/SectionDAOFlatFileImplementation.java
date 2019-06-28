package databaseoperations.flatfileoperations;

import com.google.gson.Gson;
import databases.flatfile.FlatFileConnection;
import entities.Section;
import interfaces.SectionDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SectionDAOFlatFileImplementation implements SectionDAO {
    private String path;

    public SectionDAOFlatFileImplementation(){
        path = FlatFileConnection.getSectionFilePath();
    }

    @Override
    public Section create(Section section) {
        String sectionCSV = section.toCSV();

        if (retrieve(section.getId()) == null){
            try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
                long fileLength = output.length();
                output.seek(fileLength);
                output.writeBytes(sectionCSV + "\n");
            } catch (FileNotFoundException e) {
                System.err.println("File could not be found!");
            } catch (IOException e) {
                System.err.println("File could not be accessed!");
            }
        }
        return retrieve(section.getId());
    }

    @Override
    public Section retrieve(int sectionId) {
        Section section = null;
        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                if(Section.fromCSV(element).getId() == sectionId){ // parsing from csv
                    section = Section.fromCSV(element);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
        }
        return section;
    }

    @Override
    public List<Section> retrieve() {
        List<Section> sectionList = new ArrayList<>();
        try(RandomAccessFile input = new RandomAccessFile(path, "r")) {
            String element;
            while(true){
                element = input.readLine();
                if (element == null)
                    break;
                Section section = Section.fromCSV(element); // parsing from csv
                sectionList.add(section);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File could not be found!");
        } catch (IOException e) {
            System.err.println("File could not be accessed!");
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
        List<Section> sectionList = retrieve();
        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            for (Section s : sectionList){
                if (s.getId() == sectionId){ // updating attribute(s)
                    s.setSectionNumber(section.getSectionNumber());
                    s.setSemester(section.getSemester());
                    s.setSeatLimit(section.getSeatLimit());
                    s.setCourseCode(section.getCourseCode());
                    s.setInitials(section.getInitials());
                }
                String sectionCSV = s.toCSV();
                output.writeBytes(sectionCSV + "\n");
            }
        } catch (IOException ioe){
            System.err.println("File could not be accessed!");
        }
        return retrieve(sectionId);
    }

    @Override
    public int delete(int sectionId) {
        List<Section> sectionList = retrieve();
        int sizeBefore = sectionList.size();
        for (Section s : sectionList){
            if (s.getId() == sectionId){
                sectionList.remove(s);
                break;
            }
        }

        try(RandomAccessFile output = new RandomAccessFile(path, "rw")){
            deleteAll(); // clearing the file
            int sizeAfter = 0;
            for (Section s : sectionList){
                String sectionCSV = s.toCSV();
                output.writeBytes(sectionCSV + "\n");
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
