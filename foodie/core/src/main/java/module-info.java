module foodie.core {
  exports foodie.core;
  exports foodie.json;

  requires java.desktop;
  requires com.fasterxml.jackson.annotation;
  requires com.fasterxml.jackson.core;
  requires transitive com.fasterxml.jackson.databind;
}


