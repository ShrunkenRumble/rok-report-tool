package shrunken.rok.reportreader;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;

public class MailCacheMonitor implements AutoCloseable{
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(
        r -> {
            Thread thread = new Thread(r, "Monitor");
            thread.setDaemon(true);
            return thread;
        }
    );
    private final Path mailFolder;

    public MailCacheMonitor(Path mailFolder) {
        this.mailFolder = mailFolder;
    }

    public void start() {
        executor.scheduleWithFixedDelay(this::scan, 0, 5, TimeUnit.SECONDS);
    }

    private void scan() {
        try (DirectoryStream<Path> files = Files.newDirectoryStream(mailFolder)) {
            for (Path file : files) {
                if (!Files.isRegularFile(file)) {
                    continue;
                }

                // Check whether this file has already been imported.
                // parse it here on the background thread.

                System.out.println("Found: " + file.getFileName());

                Platform.runLater(() -> {
                    // Add the parsed Report to an ObservableList.
                    // updateStatus("Imported " + file.getFileName());
                });
            }
        } catch (IOException e) {
            e.printStackTrace();

            Platform.runLater(() -> {
                // statusLabel.setText("Scan failed: " + e.getMessage());
            });
        }
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }
}
