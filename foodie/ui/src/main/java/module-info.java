module foodie.ui {
  requires foodie.core;
  requires transitive javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.net.http;
  requires com.fasterxml.jackson.databind;


  exports foodie.ui;

  opens foodie.ui to javafx.graphics, javafx.fxml;


}
