package com.scooterjee.app.expostion.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scooterjee.app.expostion.connection.dto.SessionDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerE2ETest {

    @Autowired
    private MockMvc mvc;

    private static final String UUID_HEADER = "uuid";

    @Test
    void should_be_ultimate_end_to_end() throws Exception {

        //create users
        mvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));

        mvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            "{\"email\": \"pabib@myges.fr\", \"firstname\":\"Paul Adil\", \"lastname\":\"Abib\", \"password\": \"0000\", \"phoneNumber\":\"0102030405\",\"address\": {\"city\":\"Paris\", \"street\":\"inconnus\",\"country\":\"France\",\"postalCode\":\"75011\"}}"))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json("{\"userId\":1}"));

        mvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            "{\"email\": \"adouillard@myges.fr\", \"firstname\":\"Amélie Vieille\", \"lastname\":\"Douillard Petite\", \"password\": \"0000\", \"phoneNumber\":\"0102030405\",\"address\": {\"city\":\"Paris\", \"street\":\"mayenne\",\"country\":\"France\",\"postalCode\":\"45000\"}}"))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json("{\"userId\":2}"));

        mvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[{\"email\":\"pabib@myges.fr\",\"lastname\":\"Abib\",\"firstname\":\"Paul Adil\",\"address\":\"null inconnus, 75011 France\",\"phoneNumber\":\"0102030405\",\"roles\":[]},{\"email\":\"adouillard@myges.fr\",\"lastname\":\"Douillard Petite\",\"firstname\":\"Amélie Vieille\",\"address\":\"null mayenne, 45000 France\",\"phoneNumber\":\"0102030405\",\"roles\":[]}]"));

        //create scooter model
        mvc.perform(MockMvcRequestBuilders.get("/models"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));

        mvc.perform(MockMvcRequestBuilders.post("/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"serie 1\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        mvc.perform(MockMvcRequestBuilders.get("/models"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"name\":\"serie 1\"}]"));

        //get both token
        String tokenPaul = mvc.perform(MockMvcRequestBuilders.post("/login")
                                           .contentType(MediaType.APPLICATION_JSON)
                                           .content("{\"email\": \"pabib@myges.fr\",\"password\": \"0000\"}"))
            .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        SessionDTO sessionPaul = mapper.readValue(tokenPaul, SessionDTO.class);
        assertThat(sessionPaul.token).isNotBlank();

        String tokenAme = mvc.perform(MockMvcRequestBuilders.post("/login")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content("{\"email\": \"adouillard@myges.fr\",\"password\": \"0000\"}"))
            .andReturn().getResponse().getContentAsString();

        SessionDTO sessionAme = mapper.readValue(tokenAme, SessionDTO.class);
        assertThat(sessionAme.token).isNotBlank();

        //create scooter
        mvc.perform(MockMvcRequestBuilders.get("/scooters"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));

        mvc.perform(MockMvcRequestBuilders.post("/scooters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"scooterModelID\" : 1,\"serialNumber\" : \"000251\"}")
                        .header(UUID_HEADER, sessionPaul.token))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        mvc.perform(MockMvcRequestBuilders.get("/scooters"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[{\"id\":1,\"scooterModelID\":1,\"serialNumber\":\"000251\"}]"));

        //create category
        mvc.perform(MockMvcRequestBuilders.get("/categories"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[]"));

        mvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"category\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        mvc.perform(MockMvcRequestBuilders.get("/categories"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[{\"id\":1,\"name\":\"category\"}]"));

        //create problemStatus
        mvc.perform(MockMvcRequestBuilders.get("/problemstatus"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[]"));

        mvc.perform(MockMvcRequestBuilders.post("/problemstatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"open\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        mvc.perform(MockMvcRequestBuilders.post("/problemstatus")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"waiting for review\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        mvc.perform(MockMvcRequestBuilders.post("/problemstatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\" : \"closed\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        mvc.perform(MockMvcRequestBuilders.get("/problemstatus"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[{\"id\":1,\"name\":\"open\"}, {\"id\":2,\"name\":\"waiting for review\"}, {\"id\":3,\"name\":\"closed\"}]"));

        //create problem
        mvc.perform(MockMvcRequestBuilders.get("/problems"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[]"));

        mvc.perform(MockMvcRequestBuilders.post("/problems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                     "    \"name\": \"Problème de freins\",\n" +
                                     "    \"description\": \"Quand je freine bah la batterie se coupe et je freine pas :,(\",\n" +
                                     "    \"scooterId\": 1,\n" +
                                     "    \"latitude\": 47.9029,\n" +
                                     "    \"longitude\": 1.9039,\n" +
                                     "    \"categoryId\": 1,\n" +
                                     "    \"problemStatusId\": 1\n" +
                                     "}")
                        .header(UUID_HEADER, sessionPaul.token))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        mvc.perform(MockMvcRequestBuilders.get("/problems"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[{\"id\":1,\"name\":\"Problème de freins\",\"description\":\"Quand je freine bah la batterie se coupe et je freine pas :,(\",\"scooter\":{\"id\":1,\"scooterModelID\":1,\"serialNumber\":\"000251\"},\"latitude\":47.9029,\"longitude\":1.9039,\"date\":\"2022-07-27\",\"owner\":{\"email\":\"pabib@myges.fr\",\"lastname\":\"Abib\",\"firstname\":\"Paul Adil\",\"address\":\"null inconnus, 75011 France\",\"phoneNumber\":\"0102030405\",\"roles\":[]},\"referent\":null,\"category\":{\"id\":1,\"name\":\"category\"},\"status\":{\"id\":1,\"name\":\"open\"}}]"));

        //assign ame to be resolver problem
        mvc.perform(MockMvcRequestBuilders.post("/users/adouillard@myges.fr/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"list\":[1]}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        //see all available pb for ame
        mvc.perform(MockMvcRequestBuilders.get("/users/adouillard@myges.fr/availableproblems"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"name\":\"Problème de freins\",\"description\":\"Quand je freine bah la batterie se coupe et je freine pas :,(\",\"scooter\":{\"id\":1,\"scooterModelID\":1,\"serialNumber\":\"000251\"},\"latitude\":47.9029,\"longitude\":1.9039,\"date\":\"2022-07-27\",\"owner\":{\"email\":\"pabib@myges.fr\",\"lastname\":\"Abib\",\"firstname\":\"Paul Adil\",\"address\":\"null inconnus, 75011 France\",\"phoneNumber\":\"0102030405\",\"roles\":[]},\"referent\":null,\"category\":{\"id\":1,\"name\":\"category\"},\"status\":{\"id\":1,\"name\":\"open\"}}]"));

        //paul can't see the problem
        mvc.perform(MockMvcRequestBuilders.get("/users/pabib@myges.fr/availableproblems"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));

        //assign ame on the given problem
        mvc.perform(MockMvcRequestBuilders.put("/problems/1")
                        .header(UUID_HEADER, sessionAme.token))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        //verify if ame is assigned to the problem
       mvc.perform(MockMvcRequestBuilders.get("/problems"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(
                "[{\"id\":1,\"name\":\"Problème de freins\",\"description\":\"Quand je freine bah la batterie se coupe et je freine pas :,(\",\"scooter\":{\"id\":1,\"scooterModelID\":1,\"serialNumber\":\"000251\"},\"latitude\":47.9029,\"longitude\":1.9039,\"date\":\"2022-07-27\",\"owner\":{\"email\":\"pabib@myges.fr\",\"lastname\":\"Abib\",\"firstname\":\"Paul Adil\",\"address\":\"null inconnus, 75011 France\",\"phoneNumber\":\"0102030405\",\"roles\":[]},\"referent\":{\"email\":\"adouillard@myges.fr\",\"lastname\":\"Douillard Petite\",\"firstname\":\"Amélie Vieille\",\"address\":\"null mayenne, 45000 France\",\"phoneNumber\":\"0102030405\",\"roles\":[]},\"category\":{\"id\":1,\"name\":\"category\"},\"status\":{\"id\":1,\"name\":\"open\"}}]"));

        //paul can't resolve the problem (can't close)
        mvc.perform(MockMvcRequestBuilders.put("/problems/1/status/2")
                        .header(UUID_HEADER, sessionPaul.token))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().json("{\"message\":\"Only the referent on the problem can close it\"}"));

        //ame resolves the problem
        mvc.perform(MockMvcRequestBuilders.put("/problems/1/status/2")
                        .header(UUID_HEADER, sessionAme.token))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));

        //ame should not see problem in available list anymore
        mvc.perform(MockMvcRequestBuilders.get("/users/adouillard@myges.fr/availableproblems"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));

        //paul should be able to recommend (or not) the referent
        mvc.perform(MockMvcRequestBuilders.post("/user/2/recommend")
                        .header(UUID_HEADER, sessionPaul.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"problemId\":1}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(""));


        //now we are able to see the recommendation
        mvc.perform(MockMvcRequestBuilders.get("/referents"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[{\"id\":1,\"lastName\":\"Abib\",\"firstname\":\"Paul Adil\",\"rating\":0.0},{\"id\":2,\"lastName\":\"Douillard Petite\",\"firstname\":\"Amélie Vieille\",\"rating\":1.0}]"));
    }

}