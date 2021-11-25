module foodie.ui {
  requires transitive foodie.core;
  requires transitive javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires java.net.http;
  requires com.fasterxml.jackson.databind;


  // requires javafx.media;
  // opens com.acme.treefx to javafx.graphics;

  exports foodie.ui;

  opens foodie.ui to javafx.graphics, javafx.fxml;


}
