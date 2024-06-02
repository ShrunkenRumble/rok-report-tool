package shrunken.rok.reportreader;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileCopier {
    private static final String SOURCE_FOLDER = "path/to/source/folder";
    private static final String DESTINATION_FOLDER = "path/to/destination/folder";
    private static final String LOG_FILE = "copied_files.log";

    public void copyFiles() {
        File sourceDir = new File(SOURCE_FOLDER);
        File destDir = new File(DESTINATION_FOLDER);

        // Create destination directory if it doesn't exist
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        // Load the log file
        Set<String> copiedFiles = loadLogFile();

        // List files in the source directory
        File[] files = sourceDir.listFiles();
        if (files == null) {
            System.out.println("No files found in the source directory.");
            return;
        }

        for (File file : files) {
            try {
                // Check if file was already copied
                if (!copiedFiles.contains(file.getName())) {
                    // Copy the file
                    Files.copy(file.toPath(), Paths.get(destDir.getAbsolutePath(), file.getName()),
                            StandardCopyOption.REPLACE_EXISTING);
                    // Log the copied file
                    logFile(file.getName());
                    System.out.println("Copied: " + file.getName());
                } else {
                    System.out.println("Skipped (already copied): " + file.getName());
                }
            } catch (IOException e) {
                System.err.println("Error copying file " + file.getName() + ": " + e.getMessage());
            }
        }
    }

    private Set<String> loadLogFile() {
        File logFile = new File(LOG_FILE);
        Set<String> fileSet = new HashSet<>();

        if (logFile.exists()) {
            try (Scanner scanner = new Scanner(logFile)) {
                while (scanner.hasNextLine()) {
                    fileSet.add(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                System.err.println("Log file not found: " + e.getMessage());
            }
        }

        return fileSet;
    }

    private void logFile(String fileName) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(fileName);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
