package databases.flatfile;

import java.io.File;
import java.io.IOException;

public class FlatFileConnection {
    private static String filename = "Student.json";
    private static String path = "src" + File.separator + "databases" + File.separator + "flatfile" + File.separator + filename;

    private final static FlatFileConnection INSTANCE = new FlatFileConnection();
    private FlatFileConnection(){
        File file = new File(path);
        try {
            if (file.createNewFile()){
                System.out.println("Created Student File.");
            }
        } catch (IOException e) {
            System.err.println("File exists/could not create!");
        }
    }

    public static String getFilePath(){
        return path;
    }
}
