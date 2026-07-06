package shrunken.rok.reportreader.gui;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private MailCacheMonitor cacheMonitor;

    @FXML
    private void openExplorer() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Mailcache Folder");
        File selectedDir = dirChooser.showDialog(App.getStage());
        if (selectedDir != null) folderPathField.setText(selectedDir.getAbsolutePath());
    }

    @FXML
    private void readFiles() {
        try {
            Path mailFolder = Path.of(folderPathField.getText().trim());
            if (!Files.isDirectory(mailFolder)) {
                log("Path is not a directory: " + mailFolder);
                return;
            }
            stopMonitoring();
            startButton.disableProperty().set(true);
            cacheMonitor = new MailCacheMonitor(mailFolder);
            cacheMonitor.start();
        } catch (Exception ex) {
            log("Start error: " + ex.getMessage());
        }
    }

    @FXML
    private void stopMonitoring() {
        if (cacheMonitor != null) {
            cacheMonitor.close();
            cacheMonitor = null;
        }
    }

    private void onStableFileCopyReady(Path copiedFile) {
        try {
            String name = copiedFile.getFileName().toString();
            if (App.getReportLog().getFilesRead().contains(name)) return;

            ArrayList<Report> reports = App.getParser().extractData(copiedFile, name);
            if (reports != null && !reports.isEmpty()) {
                App.getReportLog().addReport(reports);
                log("Imported: " + name + " (" + reports.size() + ")");
            }
        } catch (Exception e) {
            log("Import error for " + copiedFile.getFileName() + ": " + e.getMessage());
        }
    }

    private void log(String msg) {
        Platform.runLater(() -> {
            String cur = screen.getText();
            screen.setText((cur == null || cur.isEmpty()) ? msg : (cur + "\n" + msg));
        });
    }

    /** Call from Application.stop() to clean up. */
    public void shutdown() { stopMonitoring(); }
}
