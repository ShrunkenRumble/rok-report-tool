package shrunken.rok.reportreader.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
    Label screen;

    String display = "";

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

        ArrayList<String> filesRead = new ArrayList<String>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    //if (!filesRead.contains(file.getName())) {

                    //}
                    System.out.println(file.getName());
                    filesRead.add(file.getName());
                    if (screen.getText().equals("")) {
                        screen.setText("Importing Files...");
                        screen.setText(screen.getText() + "\n" + file.getName());
                    } else {
                        screen.setText(screen.getText() + "\n" + file.getName());
                    }
                    App.getReader().extractData(file.getPath(), file.getName());
                }
            }

        } else {
            System.out.println("No files found or folder does not exist.");
        }

        //App.getReader().printTable();
    }
}
