module shrunken.rok.reportreader.gui {
    requires javafx.controls;
    requires javafx.fxml;

    opens shrunken.rok.reportreader.gui to javafx.fxml;
    exports shrunken.rok.reportreader.gui;
}
