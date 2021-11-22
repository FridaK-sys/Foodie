package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Test class for LocalAppController
 */

public class LocalAppControllerTest {

  LocalAppController localController = new LocalAppController();

  @Test
  void setUpStorage(){
    assertNull(localController.dataAccess, "dataAccess is not null");
    localController.setUpStorage();
    assertNotNull(localController.dataAccess,"dataAccess is null");
  }
  
}
