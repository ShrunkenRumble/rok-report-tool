module shrunken.rok.reportreader.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires tablesaw.core;

    opens shrunken.rok.reportreader.gui to javafx.fxml;
    exports shrunken.rok.reportreader.gui;
}
