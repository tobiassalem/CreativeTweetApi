package se.salemcreative.twitapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.salemcreative.twitapi.model.Tweet;
import se.salemcreative.twitapi.model.User;
import se.salemcreative.twitapi.service.TweetService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TweetControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	TweetService tweetService;

	@Test
	public void getAllTweets() throws Exception {
		User wizard = new User("Gandalf");
		Tweet wisdom = new Tweet(wizard, "All we can do, is to decide what do with the time that is given to us...");
		wisdom.setAuthor(wizard);
		List<Tweet> allTweets = new ArrayList<>();
		allTweets.add(wisdom);

		when(tweetService.findAll()).thenReturn(allTweets);

		mvc.perform(MockMvcRequestBuilders.get("/tweets/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].message").value(wisdom.getMessage()));
	}


}
