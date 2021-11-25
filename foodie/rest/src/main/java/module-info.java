module foodie.rest {
  requires com.fasterxml.jackson.databind;

  requires transitive foodie.core;
  requires spring.beans;
  requires spring.context;
  requires spring.web;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.core;
  requires java.net.http;

  opens foodie.rest to spring.beans, spring.context, spring.web, spring.core;

}
