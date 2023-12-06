package com.example.Java4.controller;

import com.example.Java4.Java4Application;
import com.example.Java4.entity.List;
import com.example.Java4.entity.LoginResponseDTO;
import com.example.Java4.entity.User;
import com.example.Java4.repository.ListRepository;
import com.example.Java4.repository.UserRepository;
import com.example.Java4.service.AuthenticationService;
import com.example.Java4.service.ListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ListControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ListRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    private String token;
    private  User testUser;


    @BeforeEach
    void setup() {
        register();
        login();
    }

    private void register() {
        authenticationService.registerUser("test@test.com", "userTest", "password");

    }

    private void login() {
        LoginResponseDTO login = authenticationService.loginUser("userTest", "password");
        token = login.getJwt();
        testUser = login.getUser();

    }

    @Test
    void getListById() throws Exception {

        List list = List.builder().isFavorite(true).hash("abcd").build();
        List saved = repository.save(list);
        User user1 = userRepository.findById(testUser.getId()).orElse(null);

        user1.addList(list);
        userRepository.save(user1);


        mvc.perform(get("/list/id/" + saved.getId())
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.hash").value("abcd"))
                .andExpect(jsonPath("$.isFavorite").value(true));
    }


    @Test
    void addList() throws Exception {


        List list = List.builder().hash("listhash").isFavorite(true).listItems(new HashSet<>()).users(new HashSet<User>(Arrays.asList(testUser))).build();
        mvc.perform(MockMvcRequestBuilders.post("/list/")
                        .header("Authorization", "Bearer " + token)

                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hash").value("listhash"))
                .andExpect(jsonPath("$.isFavorite").value(true))
                .andDo(print())
                .andReturn();


    }



    @Test
    void updateList() {
    }

    @Test
    void getLists() throws Exception {
        List list = List.builder().id(1).isFavorite(true).hash("test").build();
        List list2 = List.builder().id(2).isFavorite(false).hash("xyz").users(new HashSet<>()).build();
        LoginResponseDTO login = authenticationService.loginUser("admin", "password");


        repository.save(list);
        repository.save(list2);


        mvc.perform(get("/list/s")
                        .header("Authorization", "Bearer " + login.getJwt())
                ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].hash").value("test"))
                .andExpect(jsonPath("$[0].isFavorite").value(true))
                .andExpect(jsonPath("$[1].hash").value("xyz"))
                .andExpect(jsonPath("$[1].isFavorite").value(false));

    }


    @Test
    void deleteList() throws Exception {
        List list = List.builder().isFavorite(true).hash("to be deleted").build();
        List saved = repository.save(list);
        User user1 = userRepository.findById(testUser.getId()).orElse(null);

        user1.addList(list);
        userRepository.save(user1);

        mvc.perform(delete("/list/" + saved.getId())
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }
}