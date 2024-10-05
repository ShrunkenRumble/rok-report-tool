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
    TableColumn<Report, String> myPrimCmdrCol;
    @FXML
    TableColumn<Report, String> mySecCmdrCol;
    @FXML
    TableColumn<Report, Integer> myUnitsCol;
    @FXML
    TableColumn<Report, Integer> myHealsCol;
    @FXML
    TableColumn<Report, Integer> myDeadCol;
    @FXML
    TableColumn<Report, Integer> mySevCol;
    @FXML
    TableColumn<Report, Integer> mySlightCol;
    @FXML
    TableColumn<Report, Integer> myRemainingCol;
    @FXML
    TableColumn<Report, Integer> myKPCol;
    @FXML
    TableColumn<Report, Integer> myPowerLossCol;
    @FXML
    TableColumn<Report, String> oppPrimCmdrCol;
    @FXML
    TableColumn<Report, String> oppSecCmdrCol;
    @FXML
    TableColumn<Report, Integer> oppUnitsCol;
    @FXML
    TableColumn<Report, Integer> oppHealsCol;
    @FXML
    TableColumn<Report, Integer> oppDeadCol;
    @FXML
    TableColumn<Report, Integer> oppSevCol;
    @FXML
    TableColumn<Report, Integer> oppSlightCol;
    @FXML
    TableColumn<Report, Integer> oppRemainingCol;
    @FXML
    TableColumn<Report, Integer> oppKPCol;
    @FXML
    TableColumn<Report, Integer> oppPowerLossCol;
    @FXML
    TableColumn<Report, String> idCol;

    private boolean tableInitialized = false;

    private void initTable() {
        myPrimCmdrCol.setCellValueFactory(new PropertyValueFactory<Report, String>("myPrimCmdr"));
        mySecCmdrCol.setCellValueFactory(new PropertyValueFactory<Report, String>("mySecCmdr"));
        myUnitsCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("myUnits"));
        myHealsCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("myHeals"));
        myDeadCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("myDead"));
        mySevCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("mySev"));
        mySlightCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("mySlight"));
        myRemainingCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("myRemaining"));
        myKPCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("myKP"));
        myPowerLossCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("myPowerLoss"));
        oppPrimCmdrCol.setCellValueFactory(new PropertyValueFactory<Report, String>("oppPrimCmdr"));
        oppSecCmdrCol.setCellValueFactory(new PropertyValueFactory<Report, String>("oppSecCmdr"));
        oppUnitsCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppUnits"));
        oppHealsCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppHeals"));
        oppDeadCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppDead"));
        oppSevCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppSev"));
        oppSlightCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppSlight"));
        oppRemainingCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppRemaining"));
        oppKPCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppKP"));
        oppPowerLossCol.setCellValueFactory(new PropertyValueFactory<Report, Integer>("oppPowerLoss"));
        idCol.setCellValueFactory(new PropertyValueFactory<Report, String>("id"));
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
