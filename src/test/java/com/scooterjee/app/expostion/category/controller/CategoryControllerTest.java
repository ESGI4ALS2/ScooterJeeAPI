package com.scooterjee.app.expostion.category.controller;

import com.scooterjee.app.domain.categories.Categories;
import com.scooterjee.app.infrastructure.service.CategoriesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriesService categoriesService;


    @AfterEach
    void tearDown() {
        Mockito.reset(categoriesService);
    }

    @Nested
    class CreateCategory {

        @Test
        void should_return_status_200_when_creating_post_request_with_valid_json() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"name\"}"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(""));
        }
        @Test
        void should_return_status_400_when_creating_post_request_with_wrong_json() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"nae\":\"name\"}"))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string(""));
        }

        @Test
        void should_return_status_409_when_creating_post_request_with_right_json() throws Exception {
            Categories categories = new Categories(1L, "cat1");
            //BDDMockito.willThrow(new CertificatAlreadyPresentException()).given(certificatService).add(certificat);

            mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"cat1\"}"))
                    .andExpect(MockMvcResultMatchers.status().is(409))
                    .andExpect(MockMvcResultMatchers.content().string("{\"message\":\"\"}"));
        }

    }

}