package ui;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RestAppControllerTest {
  RestAppController restController = new RestAppController();

  @Test
  void setUpStorage(){
    assertNull(restController.dataAccess);
    restController.setUpStorage();
    assertNotNull(restController.dataAccess);
    
  }
  
}
