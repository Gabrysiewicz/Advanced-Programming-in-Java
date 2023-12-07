package com.example.Java4.controller;

import com.example.Java4.entity.List;
import com.example.Java4.entity.LoginResponseDTO;
import com.example.Java4.entity.User;
import com.example.Java4.repository.ListRepository;
import com.example.Java4.repository.UserRepository;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.config.name=application-tests")
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

        List list = List.builder().isFavorite(true).name("abcd").build();
        List saved = repository.save(list);
        User user1 = userRepository.findById(testUser.getId()).orElse(null);

        user1.addList(list);
        userRepository.save(user1);


        mvc.perform(get("/list/id/" + saved.getId())
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value("abcd"))
                .andExpect(jsonPath("$.isFavorite").value(true));
    }

    @Test
    void getListByIdWhenListIsNotUsers() throws Exception {

        List list = List.builder().isFavorite(true).name("abcd").build();
        List saved = repository.save(list);
        User user1 = userRepository.findById(testUser.getId()).orElse(null);

        user1.addList(list);
        userRepository.save(user1);


        mvc.perform(get("/list/id/" + saved.getId()+1)
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isForbidden());
    }



    @Test
    void addList() throws Exception {


        List list = List.builder().name("listname").isFavorite(true).listItems(new HashSet<>()).users(new HashSet<User>(Arrays.asList(testUser))).build();
        mvc.perform(MockMvcRequestBuilders.post("/list/")
                        .header("Authorization", "Bearer " + token)

                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(list)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("listname"))
                .andExpect(jsonPath("$.isFavorite").value(true))
                .andDo(print())
                .andReturn();


    }



    @Test
    void updateList() throws Exception {
        List list = List.builder().isFavorite(true).name("basic").build();
        List saved = repository.save(list);
        User user1 = userRepository.findById(testUser.getId()).orElse(null);

        user1.addList(list);
        userRepository.save(user1);

        saved.setName("changed");

        mvc.perform(put("/list/")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("changed"))
                .andExpect(jsonPath("$.isFavorite").value("true"));

    }

    @Test
    void getLists() throws Exception {
        List list = List.builder().id(1).isFavorite(true).name("test").build();
        List list2 = List.builder().id(2).isFavorite(false).name("xyz").users(new HashSet<>()).build();
        LoginResponseDTO login = authenticationService.loginUser("admin", "password");


        repository.save(list);
        repository.save(list2);


        mvc.perform(get("/list/s")
                        .header("Authorization", "Bearer " + login.getJwt())
                ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test"))
                .andExpect(jsonPath("$[0].isFavorite").value(true))
                .andExpect(jsonPath("$[1].name").value("xyz"))
                .andExpect(jsonPath("$[1].isFavorite").value(false));

    }

    @Test
    void getListsAsNotAdmin() throws Exception {
        List list = List.builder().id(1).isFavorite(true).name("test").build();
        List list2 = List.builder().id(2).isFavorite(false).name("xyz").users(new HashSet<>()).build();


        repository.save(list);
        repository.save(list2);


        mvc.perform(get("/list/s")
                .header("Authorization", "Bearer " + token)
        ).andDo(print()).andExpect(status().isForbidden());

    }


    @Test
    void deleteList() throws Exception {
        List list = List.builder().isFavorite(true).name("to be deleted").build();
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