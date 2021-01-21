package com.bnpparibas.itg.mylibraries.libraries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("tp-spring-8-test-NLO")
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Tests absence d'authentification .... ")
    public void testIfSecure() throws Exception{
        mvc.perform(get("/unsecured/toto"))
            .andExpect(status().isOk());
        mvc.perform(get("/librairies"))
            .andExpect(status().isUnauthorized());
        mvc.perform(get("/notexist"))
            .andExpect(status().isUnauthorized());

    }

    @Test
    @DisplayName("Tests présence d'authentification")
    @WithMockUser
    public void testIfSecure_2()throws Exception{
        mvc.perform(get("/libraries"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test du role user sur une ressource admin")
    @WithMockUser(authorities = "USER_ROLE")
    public void testUserRefused() throws Exception{
        mvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }
}
