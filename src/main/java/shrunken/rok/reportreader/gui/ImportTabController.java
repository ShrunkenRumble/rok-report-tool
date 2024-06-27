package shrunken.rok.reportreader.gui;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

public class ImportTabController {
    @FXML
    Label folderPathLabel;
    @FXML
    TextField folderPathField;

    @FXML
    private void openExplorer() throws IOException {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Reports Folder");
        File selectedDir = dirChooser.showDialog(App.getStage());

        if (selectedDir != null) {
            folderPathField.setText(selectedDir.getAbsolutePath());
        }

    }

    @FXML
    private void readFiles() {
        String DIRECTORY_PATH = folderPathField.getText();

        File folder = new File(DIRECTORY_PATH);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                    App.getReader().extractData(file.getPath(), file.getName());
                }
            }
        } else {
            System.out.println("No files found or folder does not exist.");
        }

        App.getReader().printTable();
    }
}
