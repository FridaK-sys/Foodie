package restapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RecipeNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(RecipeNotFoundException.class) // kjører bare hvis RecipeNotFoundException kastes
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(RecipeNotFoundException ex) {
    return ex.getMessage();
  }

}
