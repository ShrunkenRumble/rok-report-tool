package shrunken.rok.reportreader.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shrunken.rok.reportreader.BattleLog;
import shrunken.rok.reportreader.Reader;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    private static Reader reportReader;
    private static BattleLog battleLog;

    @Override
    public void start(Stage stage) throws IOException {
        reportReader = new Reader();
        battleLog = new BattleLog();

        scene = new Scene(loadFXML("main"), 1120, 719);
        App.stage = stage;
        App.stage.setScene(scene);
        App.stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Stage getStage() {
        return stage;
    }

    public static Reader getReader() {
        return App.reportReader;
    }

    public static BattleLog getBattleLog() {
        return App.battleLog;
    }

    public static void main(String[] args) {
        launch();
    }

}