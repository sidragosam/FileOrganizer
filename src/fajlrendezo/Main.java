package fajlrendezo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("File organizer by Sidragosam");
        Scanner beolvaso = new Scanner(System.in);
        System.out.println("Type or copy the directory's path:");
        String mappahely =  beolvaso.nextLine();
        System.out.println("The path is: " + mappahely);
        Path path = Paths.get(mappahely);
        File file = new File(mappahely);
        if(!Files.exists(file.toPath())){
            System.err.println("No such directory exists.");
        }
        if(!Files.isDirectory(path)){
            System.err.println("This is not a directory.");
        }
        if(Files.isDirectory(path))
        {
            List<String> eredmeny = new ArrayList<>();
            search(".*", file, eredmeny);
            System.out.println("Files in the directory:");
            for (String s : eredmeny) {
                System.out.println(s);
            }

        }
    }
    public static void search(final String pattern, final File folder, List<String> result) throws IOException {
        for (final File f : folder.listFiles()) {

            if (f.isDirectory()) {
                //search(pattern, f, result);
            }
            if (f.isFile()) {
                if (f.getName().matches(pattern)) {
                    BasicFileAttributes attr = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
                    String datum = attr./*creationTime()*/lastModifiedTime().toString();
                    String[] parts = datum.split("-");
                    result.add(f.getAbsolutePath() + " Year: " + parts[0] + " Month: " + parts[1]);
                    File evMappa = new File(folder + "/"+parts[0]);
                    if (!evMappa.exists()){
                        evMappa.mkdirs();
                        System.out.println(parts[0] + " directory created. Path: " + evMappa.getPath());
                    }
                    File honapMappa = new File(folder + "/"+parts[0]+"/"+parts[1]);
                    if (!honapMappa.exists()) {
                        honapMappa.mkdirs();
                        System.out.println(parts[1] + " directory created. Path: " + honapMappa.getPath());
                    }
                    //Files.move(f.toPath(), folder + "/"+parts[0]+"/"+parts[1]+f.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    f.renameTo(new File(folder + "/"+parts[0]+"/"+parts[1]+"/"+f.getName()));
                    System.out.println(f + " moved. New path: " + folder + "/"+parts[0]+"/"+parts[1]+"/"+f.getName());
                }
            }

        }
    }
}
