package src;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLoader {
    //Nevím jestli je lepší tady tvořít plnění listu, nebo si to řešit až v samostatné třídě.
    String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public FileLoader(String filePath){
        this.filePath=filePath;
    }

    public List<String> listWithRowsOfCsvFolder() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        List<String> toReturn= new ArrayList<>();
        while (scanner.hasNext()){
            toReturn.add(scanner.nextLine());
        }
        scanner.close();
        return toReturn;
    }
}