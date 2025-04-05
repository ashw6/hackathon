import java.io.File;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Main{

    static class RecoveredFile {
        String filePath;
        String creationTime;
        String lastAccessTime;
        String lastModifiedTime;
        long fileSize;

        public RecoveredFile(String filePath, String creationTime, String lastAccessTime, String lastModifiedTime, long fileSize) {
            this.filePath = filePath;
            this.creationTime = creationTime;
            this.lastAccessTime = lastAccessTime;
            this.lastModifiedTime = lastModifiedTime;
            this.fileSize = fileSize;
        }

        @Override
        public String toString() {
            return "Recovered File: " + filePath + "\n" +
                   "Created: " + creationTime + "\n" +
                   "Last Accessed: " + lastAccessTime + "\n" +
                   "Last Modified: " + lastModifiedTime + "\n" +
                   "Size: " + fileSize + " bytes\n";
        }
    }

    public static List<RecoveredFile> recoverFiles(String directoryPath) {
        List<RecoveredFile> recoveredFiles = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path.");
            return recoveredFiles;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                try {
                    BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                    recoveredFiles.add(new RecoveredFile(
                        file.getAbsolutePath(),
                        attrs.creationTime().toString(),
                        attrs.lastAccessTime().toString(),
                        attrs.lastModifiedTime().toString(),
                        file.length()
                    ));
                } catch (Exception e) {
                    System.out.println("Error reading file attributes: " + e.getMessage());
                }
            }
        }
        return recoveredFiles;
    }

    public static void main(String[] args) {
        String directoryPath = "."; // Default to current directory for OnlineGDB
        System.out.println("Scanning directory: " + directoryPath);

        List<RecoveredFile> recoveredFiles = recoverFiles(directoryPath);
        for (RecoveredFile file : recoveredFiles) {
            System.out.println(file);
        }
    }
}
