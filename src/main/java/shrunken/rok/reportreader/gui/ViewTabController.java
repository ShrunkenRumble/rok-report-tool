package shrunken.rok.reportreader.gui;

import shrunken.rok.reportreader.Battle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewTabController {
    @FXML
    Button refresh;
    @FXML
    TableView<Battle> table;
    @FXML
    TableColumn<Battle, String> dateCol;
    @FXML
    TableColumn<Battle, String> myCmdrPairCol;
    @FXML
    TableColumn<Battle, Integer> myDeadCol;
    @FXML
    TableColumn<Battle, Integer> mySevCol;
    @FXML
    TableColumn<Battle, Integer> myKPCol;
    @FXML
    TableColumn<Battle, String> oppCmdrPairCol;
    @FXML
    TableColumn<Battle, Integer> oppDeadCol;
    @FXML
    TableColumn<Battle, Integer> oppSevCol;
    @FXML
    TableColumn<Battle, Integer> oppKPCol;


    private boolean tableInitialized = false;

    private void initTable() {
        dateCol.setCellValueFactory(new PropertyValueFactory<Battle, String>("date"));
        myCmdrPairCol.setCellValueFactory(new PropertyValueFactory<Battle, String>("myCmdrPair"));
        myDeadCol.setCellValueFactory(new PropertyValueFactory<Battle, Integer>("myDead"));
        mySevCol.setCellValueFactory(new PropertyValueFactory<Battle, Integer>("mySev"));
        myKPCol.setCellValueFactory(new PropertyValueFactory<Battle, Integer>("myKP"));
        oppCmdrPairCol.setCellValueFactory(new PropertyValueFactory<Battle, String>("oppCmdrPair"));
        oppDeadCol.setCellValueFactory(new PropertyValueFactory<Battle, Integer>("oppDead"));
        oppSevCol.setCellValueFactory(new PropertyValueFactory<Battle, Integer>("oppSev"));
        oppKPCol.setCellValueFactory(new PropertyValueFactory<Battle, Integer>("oppKP"));

        tableInitialized = true;
    }

    @FXML
    private void setTable() {
        if (!tableInitialized) {
            initTable();
        }
        table.setItems(App.getBattleLog().getBattles());

    }


}
