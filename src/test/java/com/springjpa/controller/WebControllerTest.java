package com.springjpa.controller;

import com.springjpa.Application;
import com.springjpa.model.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.nio.charset.Charset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebController.class)
@ContextConfiguration(classes= Application.class)
@EnableWebMvc
@AutoConfigureMockMvc

public class WebControllerTest {
    @Autowired
    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    String testCorrectUsername="33";
    String testCorrectPassword="33";

    String testIncorrectPassword="55";
    String testNotfoundUsername="44";



    @Test
    public void shouldLoginSuccessfully() throws Exception {
        User testUser = new User();
        testUser.setUsername(testCorrectUsername);
        testUser.setPassword(testCorrectPassword);

        String methodUrl= "/login";

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(testUser);

        mockMvc.perform(post(methodUrl).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldLoginWithBadCredentials() throws Exception {
        User testUser = new User();
        testUser.setUsername(testCorrectUsername);
        testUser.setPassword(testIncorrectPassword);

        String methodUrl= "/login";

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(testUser);

        mockMvc.perform(post(methodUrl).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldLoginWithNotFound() throws Exception {
        User testUser = new User();
        testUser.setUsername(testNotfoundUsername);
        testUser.setPassword(testCorrectPassword);

        String methodUrl= "/login";

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(testUser);

        mockMvc.perform(post(methodUrl).contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isNotFound());
    }

}
