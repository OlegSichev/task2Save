import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.awt.FileDialog.SAVE;

public class Main {

    static ArrayList<String> files = new ArrayList<>();
    static StringBuilder SB = new StringBuilder();
    static final String workPath = "D://javaHomeworksTemp/Games/";

    public static void main(String[] args) {
        GameProgress gameProgress = new GameProgress(50, 8, 1, 1.2);
        GameProgress gameProgress2 = new GameProgress(100, 2, 89, 5.5);
        GameProgress gameProgress3 = new GameProgress(74, 4, 20, 3.0);

        saveGame("save1.dat", gameProgress);
        saveGame("save2.dat", gameProgress2);
        saveGame("save3.dat", gameProgress3);

        zipFiles(files, "saves.zip");

        deleteFiles(files);

        System.out.println("Успешно завершено!");
    }

    public static void saveGame(String path, GameProgress gameProgress){
        files.add(path);
        try (FileOutputStream fos = new FileOutputStream(workPath + path);
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }


    }

    public static void zipFiles(ArrayList<String> list, String zipName){
        try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(workPath + zipName))){
            for(String s : list){
                FileInputStream fis = new FileInputStream(workPath + s);
                zos.putNextEntry(new ZipEntry(s));
                byte[] buffer = new byte[fis.available()];
                int a;
                if ((a = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, a);
                    SB.append(s).append(" - файл успешно записан в архив\n");
                }
                fis.close();
                zos.closeEntry();
                }
            deleteFiles(list);
            } catch (IOException e){
            e.printStackTrace();
        }
    }

//    public static void zipFiles(String archiveAddress, List<String> pathsZip){
//        for (int i = 0; i < pathsZip.size(); i++) {
//            String path = pathsZip.get(i);
//            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archiveAddress));
//                 FileInputStream fis = new FileInputStream(path)) {
//
//                ZipEntry entry = new ZipEntry(path);
//                zout.putNextEntry(entry);
//                byte[] buffer = new byte[fis.available()];
//                fis.read(buffer);
//                zout.write(buffer);
//                zout.closeEntry();
//            } catch (Exception ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }

    public static void deleteFiles(List<String> files){
        for (String temp : files) {
            try {
                Files.delete(Path.of(workPath + temp));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}