import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    static List<String> files = new ArrayList<>();

    public static void main(String[] args) {
        GameProgress gameProgress = new GameProgress(50, 8, 1, 1.2);
        GameProgress gameProgress2 = new GameProgress(100, 2, 89, 5.5);
        GameProgress gameProgress3 = new GameProgress(74, 4, 20, 3.0);

        saveGame("D://javaHomeworksTemp/Games/savegames/save1.dat", gameProgress);
        saveGame("D://javaHomeworksTemp/Games/savegames/save2.dat", gameProgress2);
        saveGame("D://javaHomeworksTemp/Games/savegames/save3.dat", gameProgress3);

        zipFiles("D://javaHomeworksTemp/Games/savegames/saves.zip", files);

        deleteFiles(files);
    }

    public static void saveGame(String path, GameProgress gameProgress){
        files.add(path);
        try (FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }


    }

    public static void zipFiles(String archiveAddress, List<String> pathsZip){
        for (int i = 0; i < pathsZip.size(); i++) {
            String path = pathsZip.get(i);
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archiveAddress));
                 FileInputStream fis = new FileInputStream(path)) {

                ZipEntry entry = new ZipEntry(path);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void deleteFiles(List<String> files){
        for (int i = 0; i < files.size(); i++) {
            try {
                Files.delete(Path.of(files.get(i)));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}