module foodie.ui {
  requires transitive foodie.core;
  requires javafx.controls;
  requires transitive javafx.base;
  requires javafx.fxml;
  requires json.simple;
  requires java.net.http;
  requires com.fasterxml.jackson.databind;

  exports ui;

  opens ui to javafx.graphics, javafx.fxml;
}
