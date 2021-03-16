package se.salemcreative.tweetapi.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.salemcreative.tweetapi.model.Tweet;
import se.salemcreative.tweetapi.model.User;
import se.salemcreative.tweetapi.service.TweetService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TweetControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    TweetService tweetService;

    /**
     * Requests without an access token should get code 401 = Unauthorized
     *
     * @throws Exception
     */
    @Test
    public void requestWithoutAccessTokenIsUnauthorized() throws Exception {
        Tweet wisdom = new Tweet(wizard, "All we can do, is to decide what do with the time that is given to us...");
        wisdom.setAuthor(wizard);
        List<Tweet> allTweets = new ArrayList<>();
        allTweets.add(wisdom);

        when(tweetService.findAll()).thenReturn(allTweets);

        mvc.perform(MockMvcRequestBuilders.get("/tweets/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void findAll() throws Exception {
        Tweet wisdom = new Tweet(wizard, "All we can do, is to decide what do with the time that is given to us...");
        Tweet wisdom2 = new Tweet(wizard, "All we can do, is to decide what do with the time that is given to us...");
        wisdom.setAuthor(wizard);
        List<Tweet> allTweets = new ArrayList<>();
        allTweets.add(wisdom);
        allTweets.add(wisdom2);

        when(tweetService.findAll()).thenReturn(allTweets);

        String accessToken = generateToken(hobbit.getUserName());
        mvc.perform(MockMvcRequestBuilders.get("/tweets/")
                .header(AUTH_HEADER, JWT_PREFIX + accessToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].message").value(wisdom.getMessage()));
    }

    @Test
    public void findByUserName() throws Exception {
        Tweet wisdom = new Tweet(wizard, "All we can do, is to decide what do with the time that is given to us...");
        wisdom.setAuthor(wizard);
        List<Tweet> userTweets = new ArrayList<>();
        userTweets.add(wisdom);

        when(tweetService.findByUserName(wizard.getUserName())).thenReturn(userTweets);

        String accessToken = generateToken(hobbit.getUserName());
        mvc.perform(MockMvcRequestBuilders.get("/tweets/users/gandalf")
                .header(AUTH_HEADER, JWT_PREFIX + accessToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].message").value(wisdom.getMessage()));
    }

    @Test
    public void findById() throws Exception {
        final Long id = 42L;
        Tweet wisdom = new Tweet(wizard, "All we can do, is to decide what do with the time that is given to us...");
        wisdom.setAuthor(wizard);

        when(tweetService.findById(id)).thenReturn(wisdom);

        String accessToken = generateToken(hobbit.getUserName());
        mvc.perform(MockMvcRequestBuilders.get("/tweets/" + id)
                .header(AUTH_HEADER, JWT_PREFIX + accessToken)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(wisdom.getMessage()));
    }

    @Test
    public void tweet() throws Exception {
        final String message = "All we can do, is to decide what do with the time that is given to us...";

        String accessToken = generateToken(hobbit.getUserName());
        mvc.perform(MockMvcRequestBuilders.post("/tweets/tweet")
                .header(AUTH_HEADER, JWT_PREFIX + accessToken)
                .content(message)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Mockito.verify(tweetService, Mockito.times(1))
                .tweet(Mockito.any(User.class), Mockito.any(String.class));
    }

    @Test
    public void reply() throws Exception {
        final Long id = 1L;
        final String message = "I Agree, and I have this to add to the subject...";

        Tweet wisdom = new Tweet(wizard, "All we can do, is to decide what do with the time that is given to us...");
        when(tweetService.findById(id)).thenReturn(wisdom);

        String accessToken = generateToken(hobbit.getUserName());
        mvc.perform(MockMvcRequestBuilders.post("/tweets/1/reply")
                .header(AUTH_HEADER, JWT_PREFIX + accessToken)
                .content(message)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Mockito.verify(tweetService, Mockito.times(1))
                .reply(Mockito.any(User.class), Mockito.any(String.class), Mockito.anyLong());
    }
}
