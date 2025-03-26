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

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.not;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(value = "classpath:testing.sql")
public class AccountControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @Test
    void givenAccounts_whenProducts_thenStatus200() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date openingDate = sdf.parse("2024-02-16");
        
        Account account = new Account(1L, "Ahorro", openingDate, 1500, 1L);
        when(accountService.getAccount(1L, 1L)).thenReturn(account);
        
        mvc.perform(get("/api/v1/account/1/customer/1")
                .accept(MediaType.APPLICATION_JSON))                
            .andExpect(status().isOk());            
    }

    @Test
    void givenAccounts_whenGetProductsBadSearch_thenStatus404() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date openingDate = sdf.parse("2024-02-16");
        
        Account account = new Account(1L, "Ahorro", openingDate, 1500, 1L);
        when(accountService.getAccount(1L, 1L)).thenReturn(account);
        
        mvc.perform(get("/api/v2/account/1/customer/1")
                .accept(MediaType.APPLICATION_JSON))                
            .andExpect(status().isNotFound());            
    }

    @Test
    void getAccount_should_ReturnAnAccountWithBalance1500() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date openingDate = sdf.parse("2024-02-16");
        
        Account account = new Account(1L, "Ahorro", openingDate, 1500, 1L);
        when(accountService.getAccount(1L, 1L)).thenReturn(account);
        
        mvc.perform(get("/api/v1/account/1/customer/1")
                .accept(MediaType.APPLICATION_JSON))                
            .andExpect(status().isOk())                
            .andExpect(jsonPath("$.balance").value(1500));
    }

    @Test
    void getAccount_shouldNot_ReturnAnAccountWithBalance1500() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date openingDate = sdf.parse("2024-02-16");
        
        Account account = new Account(1L, "Ahorro", openingDate, 1600, 1L);
        when(accountService.getAccount(1L, 1L)).thenReturn(account);
        
        mvc.perform(get("/api/v1/account/1/customer/1")
                .accept(MediaType.APPLICATION_JSON))                
            .andExpect(status().isOk())                
            .andExpect(jsonPath("$.balance").value(not(1500)));
    }
}