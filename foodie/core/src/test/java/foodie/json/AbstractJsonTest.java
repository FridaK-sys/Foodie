package foodie.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Abstract class for JsonTests
 */
public abstract class AbstractJsonTest {
  protected static ObjectMapper mapper = new ObjectMapper().registerModule(new CookbookModule());

  protected void assertEqualsIgnoreWhitespace(String expected, String actual, String errorMessage) {
    assertEquals(expected.replaceAll("\\s+", ""), actual.replaceAll("\\s+", ""), errorMessage);
  }

  protected abstract void testSerialization();

  protected abstract void testDeserialization();

  protected abstract void testSerializersDeserializers();

}
