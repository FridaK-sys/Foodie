package foodie.rest;

import foodie.core.Cookbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {CookbookController.class, CookbookService.class, CookbookApplication.class})
class IntegrationTest {

        @Autowired
        private TestRestTemplate restTemplate;

        @Test
        void testGetCookbook() {
                ResponseEntity<Cookbook> response = restTemplate.getForEntity("/cookbook", Cookbook.class);
                assertEquals(HttpStatus.OK, response.getStatusCode(), "HTTP-status was not ok");
                assertNotNull(response.getBody(), "Response-body was null");
        }

}
