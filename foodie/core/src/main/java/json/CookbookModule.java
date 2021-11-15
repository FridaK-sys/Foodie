package json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Cookbook;

/**
 * A Jackson module for configuring JSON serialization of TodoModel instances.
 */
@SuppressWarnings("serial")
public class CookbookModule extends SimpleModule {

  private static final String NAME = "TodoModule";

  /**
   * Initializes this TodoModule with appropriate serializers and deserializers.
   */
  public CookbookModule() {
    super(NAME, Version.unknownVersion());
    addSerializer(Cookbook.class, new CookbookWriter());
    addDeserializer(Cookbook.class, new CookbookReader());
  }
}
