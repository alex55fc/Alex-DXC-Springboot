package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.net.ServerSocket;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(value = "classpath:testing.sql")
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @Test
    void getAccount_shouldReturnAnAccount() throws Exception {
        Account account = new Account(1L, "Ahorro", new Date("2024-02-16"), 1500, 2L);
        when(accountService.getAccount(1L, 2L)).thenReturn(account);

        mvc.perform(get("/account/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.type").value("Ahorro"))
                .andExpect((ResultMatcher) jsonPath("$.balance").value("1500"));
    }

    @Test
    public void givenAccounts_whenProducts_thenStatus200() throws Exception {
        mvc.perform(get("/accounts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0]name", is("Account Test 1")));
    }

    @Test
    public void givenAccounts_whenGetProductsBadSearch_thenStatus404() throws Exception {
        mvc.perform(get("/accounts?nombrewith=a").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.status", is(404)));
    }
}
