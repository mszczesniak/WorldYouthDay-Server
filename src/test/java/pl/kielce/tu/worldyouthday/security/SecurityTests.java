package pl.kielce.tu.worldyouthday.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.security.token.json.request.AuthenticationRequest;
import pl.kielce.tu.worldyouthday.security.token.json.response.AuthenticationResponse;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@ActiveProfiles("test")
public class SecurityTests {

    @Value("${local.server.port}")
    private int port;

    private ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private RestTemplate template = new TestRestTemplate();


    @Test
    public void userEndpointProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/user", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void loginError() throws Exception {


        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("admin");
        authenticationRequest.setPassword("wrongPassword");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthenticationRequest> entity = new HttpEntity<>(authenticationRequest, requestHeaders);

        ResponseEntity<String> response = template.postForEntity(new URI("http://localhost:" + port + "/auth"), entity, String.class);
        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);

    }

    @Test
    public void loginSucceeds() throws Exception {


        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("admin");
        authenticationRequest.setPassword("password");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AuthenticationRequest> entity = new HttpEntity<>(authenticationRequest, requestHeaders);

        ResponseEntity<String> response = template.postForEntity(new URI("http://localhost:" + port + "/auth"), entity, String.class);
        AuthenticationResponse authenticationResponse = OBJECT_MAPPER.readValue(response.getBody(), AuthenticationResponse.class);
        assertNotNull(authenticationResponse.getToken());
        UserResource loggedUser = authenticationResponse.getLoggedUser();
        assertNotNull(loggedUser);
        assertEquals(loggedUser.getLogin(), "admin");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

    }
}
