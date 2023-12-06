package com.example.Java4.controller;

import com.example.Java4.entity.ListItem;
import com.example.Java4.entity.LoginResponseDTO;
import com.example.Java4.entity.User;
import com.example.Java4.repository.ListItemRepository;
import com.example.Java4.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ListItemControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ListItemRepository repository;


    @Autowired
    private AuthenticationService authenticationService;

    private String token;
    private User testUser;


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
    void addListItem() throws Exception {
        ListItem item = ListItem.builder()
                .name("item1")
                .description("desc")
                .isDone(true)
                .build();

        mvc.perform(post("/item/")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("item1"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.isDone").value(true));
    }

    @Test
    void getListItemsAsUser() throws Exception {


        mvc.perform(get("/item/s")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getListItemsAsAdmin() throws Exception {
        LoginResponseDTO login = authenticationService.loginUser("admin", "password");

        ListItem item = ListItem.builder()
                .name("do testu")
                .description("desc")
                .isDone(true)
                .build();
        repository.save(item);

        mvc.perform(get("/item/s")
                        .header("Authorization", "Bearer " + login.getJwt()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("do testu"))
                .andExpect(jsonPath("$[0].description").value("desc"))
                .andExpect(jsonPath("$[0].isDone").value(true));

    }

    @Test
    void getItemById() {
    }
}