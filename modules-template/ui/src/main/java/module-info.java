module calc.ui {
  requires calc.core;
  requires javafx.controls;
  requires javafx.fxml;
  requires json.simple;
  exports foodie.ui;

  opens foodie.ui to javafx.graphics, javafx.fxml;
}
