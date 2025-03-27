package com.microcompany.accountsservice.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.model.Customer;
import com.microcompany.accountsservice.persistence.AccountRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AccountServiceTest {

    @InjectMocks
    AccountService as;

    @Mock
    AccountRepository arMock;

    @Mock
    EntityManagerFactory emf;

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Crear Customers de prueba
        Customer customer1 = new Customer(1L, "Customer 1", "customer1@example.com", null);
        Customer customer2 = new Customer(2L, "Customer 2", "customer2@example.com", null);

        // Crear Accounts de prueba con Customers
        List<Account> accountsFake = List.of(
                new Account(1L, "Fake type", dateFormat.parse("2023-01-15"), 400, customer1),
                new Account(2L, "Fake type2", dateFormat.parse("2023-01-16"), 500, customer2),
                new Account(3L, "Fake type3", dateFormat.parse("2023-01-17"), 300, customer1)
        );

        // Mockear el repositorio para devolver accountsFake cuando se busque por ownerId 1L
        Mockito.when(arMock.findByOwnerId(1L)).thenReturn(accountsFake);
        Mockito.when(arMock.findByOwnerId(222L)).thenReturn(null);

        // Mockear el método save para asignar un ID
        Mockito.when(arMock.save(Mockito.any(Account.class)))
                .thenAnswer(elem -> {
                    Account ac = (Account) elem.getArguments()[0];
                    ac.setId(111L);
                    return ac;
                });
    }

    @Test
    void givenAccountsWhenSearchByLongIdIsNotNull() {
        List<Account> accounts = as.getAccounts(1L);
        assertThat(accounts).isNotNull();
        assertThat(accounts.size()).isGreaterThan(0);
    }

    @Test
    void givenAccountsWhenSearchByLongIdNotExistThenReturnEmptyList() {
        List<Account> accounts = as.getAccounts(222L);
        assertThat(accounts).isNull();        
    }

    @Test
    void givenValidAccount_WhenCreate_ThenThenIsNotNull() {
        // Crear un Customer de prueba
        Customer customer = new Customer(5L, "Test Customer", "test@example.com", null);

        // Crear un Account con el Customer
        Account newAccount = new Account(null, "Fake type", null, 0, customer);

        // Llamar al método create
        as.create(newAccount, customer.getId());

        // Verificar que el ID fue asignado
        assertThat(newAccount.getId()).isGreaterThan(0L);
        assertThat(newAccount.getId()).isEqualTo(111L);
    }


}