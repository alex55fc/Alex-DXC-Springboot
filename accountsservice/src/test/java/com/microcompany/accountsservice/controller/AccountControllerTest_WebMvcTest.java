package com.microcompany.accountsservice.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.microcompany.accountsservice.exception.GlobalException;
import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.model.Customer;
import com.microcompany.accountsservice.persistence.AccountRepository;
import com.microcompany.accountsservice.services.AccountService;
import com.microcompany.accountsservice.util.JsonUtil;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest_WebMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accServiceMock;

    @MockBean
    private AccountRepository accRepositoryMock;

    private List<Account> accountsFake;

    @BeforeEach
    public void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date openingDate = sdf.parse("2024-02-16");

        // Crear un objeto Customer para usar en las cuentas
        Customer customer = new Customer(2L, "Test Customer", "test@example.com", null);

        // Crear la lista de cuentas con el objeto Customer
        accountsFake = List.of(
                new Account(1L, "Ahorro", openingDate, 1500, customer),
                new Account(2L, "Ahorro", openingDate, 1500, customer),
                new Account(3L, "Ahorro", openingDate, 1500, customer)
        );

        // Configurar el mock del servicio para devolver las cuentas según el ID del propietario
        given(accServiceMock.getAccounts(2L)).willReturn(accountsFake);
        given(accServiceMock.getAccounts(100L)).willThrow(new GlobalException("No hay cuentas"));

        // Configurar el mock del repositorio para simular el guardado de una cuenta
        Mockito.when(accRepositoryMock.save(Mockito.any(Account.class)))
                .thenAnswer(elem -> {
                    Account acc = (Account) elem.getArguments()[0];
                    acc.setId(101L);
                    return acc;
                });
    }


    @Test
    public void givenAccounts_whenGetAccountsWithInvalidOwnerId_thenStatus404() throws Exception {
        mvc.perform(get("/account?ownerId=100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidAccount_whenCreateAccount_isCreated() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date openingDate = sdf.parse("2024-02-16");

        // Crear un objeto Customer para la nueva cuenta
        Customer newCustomer = new Customer(2L, "New Customer", "new@example.com", null);

        // Crear una nueva cuenta con el objeto Customer
        Account newAcc = new Account(null, "Nueva Cuenta", openingDate, 0, newCustomer);

        mvc.perform(post("/api/v1//account/customer/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.asJsonString(newAcc)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());                
    }

    @Test
    void givenInvalidAccount_whenCreateAccount_isNotCreated() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date openingDate = sdf.parse("2024-02-16");

        // Crear un objeto Customer para la nueva cuenta
        Customer newCustomer = new Customer(2L, "New Customer", "new@example.com", null);

        // Crear una nueva cuenta con datos inválidos (por ejemplo, tipo nulo)
        Account invalidAcc = new Account(null, null, openingDate, 0, newCustomer);

        mvc.perform(post("/api/v1/account/customer/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.asJsonString(invalidAcc)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()); // Espera un 400 en lugar de 201
    }
}