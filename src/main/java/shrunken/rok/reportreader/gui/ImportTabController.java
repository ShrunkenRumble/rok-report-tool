package shrunken.rok.reportreader.gui;

import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import shrunken.rok.reportreader.MailCacheMonitor;
import shrunken.rok.reportreader.Report;

public class ImportTabController {
    @FXML Label folderPathLabel;
    @FXML TextField folderPathField;
    @FXML Label screen;
    @FXML Button startButton;
    @FXML Button stopButton;

    private MailCacheMonitor monitor;
    private boolean monitorActive;

    @FXML
    private void openExplorer() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Reports Folder");
        File selectedDir = dirChooser.showDialog(App.getStage());
        if (selectedDir != null) folderPathField.setText(selectedDir.getAbsolutePath());
    }

    @FXML
    private void readFiles() {
        try {
            Path src = Path.of(folderPathField.getText().trim());
            if (!src.toFile().isDirectory()) {
                log("Path is not a directory: " + src);
                return;
            }
            Path dst = src.resolve("reports"); // keep your existing convention
            stopMonitoring(); // in case it's already running

            monitor = new MailCacheMonitor(
                src,
                dst,
                Duration.ofMillis(300),
                this::log,
                this::onStableFile // callback when a file is safe to process
            );
            monitorActive = true;
            startButton.disableProperty();
            monitor.start();
        } catch (Exception ex) {
            log("Start error: " + ex.getMessage());
        }
    }

    @FXML
    private void stopMonitoring() {
        if (monitor != null) {
            monitor.stop();
            startButton.disableProperty().set(true);
            monitorActive = false;
            monitor = null;
        }
    }

    /** Called on a background thread when a file is stable and has been copied. */
    private void onStableFile(Path file) {
        try {
            // Avoid re-importing files we already logged
            if (App.getReportLog().getFilesRead().contains(file.getFileName().toString())) return;

            ArrayList<Report> reports = App.getParser().extractData(file, file.getFileName().toString());
            if (reports != null && !reports.isEmpty()) {
                App.getReportLog().addReport(reports);
                log("Imported: " + file.getFileName() + " (" + reports.size() + ")");
            }
        } catch (Exception e) {
            log("Import error for " + file.getFileName() + ": " + e.getMessage());
        }
    }

    /** Safe UI logging from any thread. */
    private void log(String msg) {
        Platform.runLater(() -> {
            String cur = screen.getText();
            if (cur == null || cur.isEmpty()) screen.setText(msg);
            else screen.setText(cur + "\n" + msg);
        });
    }

    /** Call from your Application.stop() to ensure clean shutdown. */
    public void shutdown() {
        stopMonitoring();
    }
}
