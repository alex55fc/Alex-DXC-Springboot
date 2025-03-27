package com.microcompany.accountsservice.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import com.microcompany.accountsservice.model.Account;
import com.microcompany.accountsservice.model.Customer;

@DataJpaTest()
@ComponentScan(basePackages = {"com.microcompany.accountsservice"})
@AutoConfigureTestEntityManager
@Sql(value = "classpath:testing.sql")
class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findAll() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Crear y persistir un Customer
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        entityManager.persist(customer);

        // Crear y persistir un Account asignando el Customer
        Account account = new Account(null, "Ahorro", dateFormat.parse("2023-01-15"), 1500, null);
        account.setOwner(customer);
        entityManager.persist(account);
        entityManager.flush();

        List<Account> accounts = accountRepository.findAll();

        assertNotNull(accounts);
        assertThat(accounts.size()).isGreaterThan(0);
    }

    @Test
    void save() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Crear y persistir un Customer
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        entityManager.persist(customer);

        // Crear un Account asignando el Customer
        Account account = new Account(null, "Ahorro", dateFormat.parse("2023-01-15"), 1500, null);
        account.setOwner(customer);

        accountRepository.save(account);

        assertThat(account.getId()).isGreaterThan(0);
        Account emAccount = entityManager.find(Account.class, account.getId());
        assertThat(emAccount.getOpeningDate().compareTo(account.getOpeningDate()));
    }

    @Test
    void findByType() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Crear y persistir dos Customers
        Customer customer1 = new Customer();
        customer1.setName("Customer 1");
        customer1.setEmail("customer1@example.com");
        entityManager.persist(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Customer 2");
        customer2.setEmail("customer2@example.com");
        entityManager.persist(customer2);

        // Crear y persistir dos Accounts asignando los Customers
        Account account1 = new Account(null, "Ahorro", dateFormat.parse("2023-01-15"), 1500, null);
        account1.setOwner(customer1);
        entityManager.persist(account1);

        Account account2 = new Account(null, "Corriente", dateFormat.parse("2023-01-15"), 2000, null);
        account2.setOwner(customer2);
        entityManager.persist(account2);

        entityManager.flush();

        // when
        List<Account> accounts = accountRepository.findByType("Ahorro");

        // then
        assertNotNull(accounts);
        assertFalse(accounts.isEmpty());
        assertEquals(4, accounts.size());
        assertEquals("Ahorro", accounts.get(0).getType());
    }

    @Test
    void deleteById() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Crear y persistir un Customer
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setEmail("test@example.com");
        entityManager.persist(customer);

        // Crear y persistir un Account asignando el Customer
        Account account = new Account(null, "Ahorro", dateFormat.parse("2023-01-15"), 1500, null);
        account.setOwner(customer);
        entityManager.persist(account);
        entityManager.flush();

        // when
        accountRepository.deleteById(account.getId());
        entityManager.flush();

        // then
        Account deletedAccount = entityManager.find(Account.class, account.getId());
        assertNull(deletedAccount, "Account should be null after deletion");
    }

    @Test
    void findById_NotFound() {
        // given
        Long nonExistentId = 999L;

        // when
        List<Account> accounts = accountRepository.findAllById(Collections.singletonList(nonExistentId));

        // then
        assertNotNull(accounts);
        assertTrue(accounts.isEmpty());
    }
}