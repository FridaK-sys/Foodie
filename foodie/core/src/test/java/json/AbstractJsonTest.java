package json;

import static org.junit.Assert.assertEquals;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract class for JsonTests
 */
public abstract class AbstractJsonTest {
  protected static ObjectMapper mapper = new ObjectMapper().registerModule(new CookbookModule());

  protected void assertEqualsIgnoreWhitespace(String expected, String actual) {
    assertEquals(expected.replaceAll("\\s+", ""), actual.replaceAll("\\s+", ""));
  }

  protected abstract void testSerialization();

  protected abstract void testDeserialization();

  protected abstract void testSerializersDeserializers();

}
