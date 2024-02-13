package se.salemcreative.tweetapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.salemcreative.tweetapi.security.JwtTokenUtil;
import se.salemcreative.tweetapi.security.JwtUserDetailsService;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Test
    public void getHello() throws Exception {
        String accessToken = generateToken("frodo");

        mvc.perform(MockMvcRequestBuilders.get("/")
                        .header(AUTH_HEADER, JWT_PREFIX + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from the Creative Tweet API!")));
    }

    @Test
    public void getHelloWithoutAuth() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from the Creative Tweet API!")));
    }

    @Test
    public void getProfileInfo() throws Exception {
        String accessToken = generateToken("frodo");

        mvc.perform(MockMvcRequestBuilders.get("/profile")
                        .header(AUTH_HEADER, JWT_PREFIX + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("applicationName")))
                .andExpect(content().string(containsString("applicationVersion")))
                .andExpect(content().string(containsString("defaultProfile")))
                .andExpect(content().string(containsString("activeProfile")));
    }

    @Test
    public void getProfileInfoWithoutAuth() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/profile")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
