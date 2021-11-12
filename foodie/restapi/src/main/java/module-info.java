module foodie.restapi {
  requires com.fasterxml.jackson.databind;

  requires transitive foodie.core;
  requires spring.beans;
  requires spring.context;
  requires spring.web;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.core;

  opens foodie.restapi to spring.beans, spring.context, spring.web, spring.core;

}
