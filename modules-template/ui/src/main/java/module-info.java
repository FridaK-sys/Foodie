module calc.ui {
    requires calc.core;
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    

    opens foodie.ui to javafx.graphics, javafx.fxml, json.simple;
}
