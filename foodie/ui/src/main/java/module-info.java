module foodie.ui {
  requires transitive foodie.core;
  requires javafx.controls;
  requires javafx.fxml;
  requires json.simple;

  exports ui;

  opens ui to javafx.graphics, javafx.fxml;
}
