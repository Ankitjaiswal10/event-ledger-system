package com.eventledger.account.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthShouldReturnUp()
            throws Exception {

        mockMvc.perform(
                        (RequestBuilder) get("/health"))
                .andExpect(
                        status().isOk())
                .andExpect(
                        jsonPath("$.status")
                                .value("UP"));
    }

    @Test
    void shouldReturn404ForMissingAccount()
            throws Exception {

        mockMvc.perform(
                        (RequestBuilder) get("/accounts/unknown"))
                .andExpect(
                        status().isNotFound());
    }
}
