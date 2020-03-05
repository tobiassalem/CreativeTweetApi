package se.salemcreative.twitapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.salemcreative.twitapi.model.User;
import se.salemcreative.twitapi.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    /**
     * Requests without an access token should get code 401 = Unauthorized
     *
     * @throws Exception
     */
    @Test
    public void requestWithoutAccessTokenIsUnauthorized() throws Exception {
        final User hobbit = new User("frodo");
        List<User> allUsers = new ArrayList<>();
        allUsers.add(hobbit);

        when(userService.findAll()).thenReturn(allUsers);

        mvc.perform(MockMvcRequestBuilders.get("/users/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void findAll() throws Exception {
        List<User> allUsers = new ArrayList<>();
        allUsers.add(hobbit);

        when(userService.findAll()).thenReturn(allUsers);

        String accessToken = generateToken("frodo");
        mvc.perform(MockMvcRequestBuilders.get("/users/")
                .header(AUTH_HEADER, JWT_PREFIX + accessToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName").value(hobbit.getUserName()));
    }

    @Test
    public void findFollowers() throws Exception {
        hobbit.followUser(wizard);
        Set<User> followers = new HashSet<>();
        followers.add(hobbit);

        when(userService.findFollowers(wizard.getUserName())).thenReturn(followers);

        String accessToken = generateToken("frodo");
        mvc.perform(MockMvcRequestBuilders.get("/users/followers/gandalf")
                .header(AUTH_HEADER, JWT_PREFIX + accessToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userName").value(hobbit.getUserName()));
    }
}
