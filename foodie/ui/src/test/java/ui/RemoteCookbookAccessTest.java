package ui;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Recipe;
import ui.utils.RemoteCookbookAccess;


/**
 * Test class for RemoteCookbookAccess
 */
public class RemoteCookbookAccessTest {

  private WireMockConfiguration config;
  private WireMockServer wireMockServer;
  private RemoteCookbookAccess cookbookAccess;

  private static final String GET_COOKBOOK_RESPONSE = """
      {
        "name": "Cookbook",
        "recipes" :[
          {
            "name": "Brownies",
            "description": "Den beste oppskriften på brownies!",
            "portions": 1,
            "fav": true,
            "label": "breakfast",
            "ingredients" :[
              {
                "name": "Egg",
                "amount": 2.0,
                "unit": "stk"
              },
              {
                "name": "Kakao",
                "amount": 1.0,
                "unit": "dl"
              }
            ]
          }]}""";

  
  @BeforeEach
  public void startWireMockServerAndSetup() throws URISyntaxException {
    config = WireMockConfiguration.wireMockConfig().port(8081);
    wireMockServer = new WireMockServer(config.portNumber());
    wireMockServer.start();
    WireMock.configureFor("localhost", config.portNumber());
    cookbookAccess = new RemoteCookbookAccess(new URI("http://localhost:" + wireMockServer.port() + "/cookbook"));
  }

  @Test
  void testGetCookbook() {
    stubFor(get(urlEqualTo("/cookbook")).withHeader("Accept", equalTo("application/json")).willReturn(
        aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(GET_COOKBOOK_RESPONSE)));
  
    List<Recipe> recipes = cookbookAccess.getCookbook().getRecipes();
    assertEquals(1, recipes.size(),"Numbers of recipes was wrong");
    assertEquals("Brownies", recipes.get(0).getName(), "The name was incorrect");

  }

  @AfterEach
  public void stopWireMockServer() {
    wireMockServer.stop();
  }

}
