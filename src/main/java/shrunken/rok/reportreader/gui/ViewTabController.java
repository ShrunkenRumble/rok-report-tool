package shrunken.rok.reportreader.gui;

import shrunken.rok.reportreader.Report;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewTabController {
    @FXML
    Button refresh;
    @FXML
    TableView<Report> table;
    @FXML
    TableColumn<Report, String> dateCol;
    @FXML
    TableColumn<Report, String> myCmdrPairCol;
    @FXML
    TableColumn<Report, Integer> myDeadCol;
    @FXML
    TableColumn<Report, Integer> mySevCol;
    @FXML
    TableColumn<Report, Integer> myKPCol;
    @FXML
    TableColumn<Report, String> oppCmdrPairCol;
    @FXML
    TableColumn<Report, Integer> oppDeadCol;
    @FXML
    TableColumn<Report, Integer> oppSevCol;
    @FXML
    TableColumn<Report, Integer> oppKPCol;


    private boolean tableInitialized = false;

    private void initTable() {
        dateCol.setCellValueFactory(new PropertyValueFactory<Report, String>("date"));
        myCmdrPairCol.setCellValueFactory(new PropertyValueFactory<Report, String>("myCmdrPair"));
        myDeadCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("myDead"));
        mySevCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("mySev"));
        myKPCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("myKP"));
        oppCmdrPairCol.setCellValueFactory(new PropertyValueFactory<Report, String>("oppCmdrPair"));
        oppDeadCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppDead"));
        oppSevCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppSev"));
        oppKPCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppKP"));

        tableInitialized = true;
    }

    @FXML
    private void setTable() {
        if (!tableInitialized) {
            initTable();
        }
        table.setItems(App.getReportLog().getReports());

    }


}
