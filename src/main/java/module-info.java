module shrunken.rok.reportreader.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires tablesaw.core;
    requires javafx.graphics;

    opens shrunken.rok.reportreader.gui to javafx.fxml;
    opens shrunken.rok.reportreader to javafx.base;
    exports shrunken.rok.reportreader.gui;
}
