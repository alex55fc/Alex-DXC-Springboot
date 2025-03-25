package com.microcompany.accountsservice.controller;

import com.microcompany.accountsservice.exception.GlobalException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.persistence.AccountRepository;
import com.microcompany.accountsservice.services.AccountService;
import com.microcompany.accountsservice.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Date;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest_WebMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accServiceMock;

    @MockBean
    private AccountRepository accRepositoryMock;


    @BeforeEach
    public void setUp(){

        List<Account> accountsFake = List.of(
                new Account(1L, "Ahorro", new Date("2024-02-16"), 1500, 2L),
                new Account(2L, "Ahorro", new Date("2024-02-16"), 1500, 2L),
                new Account(3L, "Ahorro", new Date("2024-02-16"), 1500, 2L)
        );

        Mockito.when(accServiceMock.getAccounts(1L)).thenReturn(accountsFake);
        Mockito.when(accServiceMock.getAccounts(100L)).thenThrow(new GlobalException("No hay cuentas"));

        Mockito.when(accRepositoryMock.save(Mockito.any(Account.class)))
                .thenAnswer(elem -> {
                    Account acc = (Account) elem.getArguments()[0];
                    acc.setId(101L);
                    return acc;
        });
    }

    @Test
    public void givenProducts_whenGetAccountsBadSearch_theStatus404() throws Exception {
        mvc.perform(get("/accounts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$[0].type", is("Fake acc 1")));
    }

    @Test
    void givenProducts_whenValidCreateProduct_thenIsCreatedAndHaveId() throws Exception {
        Account newAcc = new Account(1L, "Ahorro", new Date("2024-02-16"), 1500, 2L);

        mvc.perform(post("/accounts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.asJsonString(newAcc))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.id", greaterThan(0L)));
    } 

}
