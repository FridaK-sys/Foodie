module foodie.ui {
  requires transitive foodie.core;
  requires transitive javafx.base;
  requires transitive javafx.graphics;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.net.http;
  requires com.fasterxml.jackson.databind;


  exports foodie.ui;
  exports foodie.ui.utils;

  opens foodie.ui to javafx.graphics, javafx.fxml;


}
