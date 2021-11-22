package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class LocalAppControllerTest {

  LocalAppController localController = new LocalAppController();

  @Test
  void setUpStorage(){
    assertNull(localController.dataAccess);
    localController.setUpStorage();
    assertNotNull(localController.dataAccess);
  }
  
}
