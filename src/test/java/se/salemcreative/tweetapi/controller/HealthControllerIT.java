package se.salemcreative.tweetapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class HealthControllerIT extends AbstractControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void getHello() {
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Greetings from the Creative Tweet API!", response.getBody());
    }

    @Test
    public void getProfileInfo() {
        final String SECURED_URL = base.toString() + "/profile";
        final HttpEntity httpEntity = new HttpEntity<>(buildHeadersWithAuth());

        ResponseEntity<String> response = template.exchange(SECURED_URL, HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("applicationName"));
        assertTrue(response.getBody().contains("applicationVersion"));
        assertTrue(response.getBody().contains("defaultProfile"));
        assertTrue(response.getBody().contains("activeProfile"));
    }

    @Test
    public void getProfileInfoWithoutAuth() {
        final String SECURED_URL = base.toString() + "/profile";

        ResponseEntity<String> response = template.getForEntity(SECURED_URL, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    /**
     * Build http headers with Auth header with JWT
     * Example call: template.exchange(base, HttpMethod.GET, new HttpEntity<>(headers), Page.class);
     *
     * @return
     */
    private HttpHeaders buildHeadersWithAuth() {
        String accessToken = generateToken("frodo");

        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTH_HEADER, JWT_PREFIX + accessToken);
        return headers;
    }
}
