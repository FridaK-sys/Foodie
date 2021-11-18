module foodie.core {
  exports core;
  exports json;

  requires java.desktop;
  requires java.persistence;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires transitive com.fasterxml.jackson.databind;

}
