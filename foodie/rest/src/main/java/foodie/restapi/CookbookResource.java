package foodie.restapi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import todolist.core.AbstractTodoList;
import todolist.core.TodoList;
import todolist.core.TodoModel;
import todolist.json.TodoPersistence;

/**
 * Used for all requests referring to a Cookbook by name.
 */
@Produces(MediaType.APPLICATION_JSON)
public class CookbookResource {
  private static final Logger LOG = LoggerFactory.getLogger(CookbookResource.class);
  private final Cookbook cookbook;
  private final String name;
  
  @Context
  private TodoPersistence todoPersistence;

  public void setTodoPersistence(TodoPersistence todoPersistence) {
    this.todoPersistence = todoPersistence;
  }
}
