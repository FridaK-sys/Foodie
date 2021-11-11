module foodie.core {
  exports core;
  exports json;

  requires json.simple;
  requires java.desktop;
  requires java.persistence;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;

}
